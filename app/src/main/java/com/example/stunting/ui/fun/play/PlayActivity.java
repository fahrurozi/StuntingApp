package com.example.stunting.ui.fun.play;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.CircularArray;

import com.example.stunting.R;
import com.example.stunting.data.model.fun.DataFun;
import com.example.stunting.data.model.fun.ResponseFun;
import com.example.stunting.data.model.fun.ResponseLevelAvailable;
import com.example.stunting.data.model.fun.ResponseSubmitFun;
import com.example.stunting.data.model.review.ResponseAddReview;
import com.example.stunting.data.network.ApiEndpoint;
import com.example.stunting.data.network.ApiService;
import com.example.stunting.ui.fun.FunActivity;
import com.example.stunting.ui.stunting_tribute.detail.AddReviewActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dmax.dialog.SpotsDialog;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener{

    Button ansA, ansB, ansC, ansD;
    Button btnSubmit;
    WebView wvQuestion;
    TextView tvQuestionNumber, tvLevel;

    Integer level;

    JSONObject listAnswersObj = null;
    JSONArray daftarJawabanArray = null;

    private ApiEndpoint endpoint = ApiService.getRetrofitInstance();

    Integer score = 0;

    private SpotsDialog spotsDialog;

    Integer pickAnswer = 0;
    ArrayList<Integer> listPickAnswer = new ArrayList<>();
    ArrayList<Integer> listQuestionId = new ArrayList<>();
    ArrayList<Integer> listCorrectAnswer = new ArrayList<>();
    Integer currentQuestionIndex = 0;
    Integer correctAnswer = 0;
    Integer questionId = 0;
    Integer totalQuestion = 0;

    List<DataFun> dataFunList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fun_question);

        ansA = findViewById(R.id.ansA);
        ansB = findViewById(R.id.ansB);
        ansC = findViewById(R.id.ansC);
        ansD = findViewById(R.id.ansD);
        btnSubmit = findViewById(R.id.btnSubmit);
        wvQuestion = findViewById(R.id.wvQuestion);
        tvQuestionNumber = findViewById(R.id.tvQuestionNumber);
        tvLevel = findViewById(R.id.tvTitle);


        spotsDialog = new SpotsDialog(this, "Mohon Tunggu...");

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        level = getIntent().getIntExtra("level", 0);

        getQuestionPerLevel(level);

        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(PlayActivity.this, FunActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onClick(View v) {
        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);

        Button clickedButton = (Button) v;
        if(clickedButton.getId()==R.id.btnSubmit){
            listPickAnswer.add(pickAnswer);
            listQuestionId.add(questionId);
            currentQuestionIndex++;
            loadDataToUI(dataFunList);

        }else{
            //choices button clicked
            clickedButton.setBackgroundColor(Color.LTGRAY);
            if(clickedButton.getId()==ansA.getId()){
                pickAnswer = 0;
            }else if (clickedButton.getId()==ansB.getId()){
                pickAnswer = 1;
            }else if (clickedButton.getId()==ansC.getId()){
                pickAnswer = 2;
            } else if (clickedButton.getId()==ansD.getId()){
                pickAnswer = 3;
            }


        }
    }


    public void getQuestionPerLevel(Integer level){
        try {
            spotsDialog.show();
            JSONStringer json = new JSONStringer();
            json.object();
            json.key("filter_type").value("by_levels");
            json.key("levels");
            json.array();
            json.value(level);
            json.endArray();
            json.key("get_type").value("qas");
            json.endObject();

            Call<ResponseFun> questionCall = endpoint.getQuestionPerLevel( json.toString());
            questionCall.enqueue(new Callback<ResponseFun>() {

                @Override
                public void onResponse(Call<ResponseFun> call, Response<ResponseFun> response) {
                    spotsDialog.dismiss();
                    if (response.isSuccessful() && response.body().getQas() != null) {
                        if (response.body().getQas().size() == 0){
                            Toast.makeText(getApplicationContext(), "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                        }else{
                            totalQuestion = response.body().getQas().size();
                            Log.d("HAI", "total Question: "+totalQuestion);
                            dataFunList = response.body().getQas();
                            loadDataToUI(response.body().getQas());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseFun> call, Throwable t) {
                    if (Objects.equals(t.getMessage(), "closed")) {
                    } else {
                        Toast.makeText(getApplicationContext(), "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public void loadDataToUI(List<DataFun> dataFun){
        if(currentQuestionIndex == totalQuestion ){
            finishQuiz();
            return;
        }

        wvQuestion.clearCache(true);
        WebSettings webSettings = wvQuestion.getSettings();
        webSettings.setJavaScriptEnabled(true);
        correctAnswer = dataFun.get(currentQuestionIndex).getCorrectAnswer();
        questionId = dataFun.get(currentQuestionIndex).getId();
        wvQuestion.loadUrl(ApiService.BASE_URL + "static/" + dataFun.get(currentQuestionIndex).getQuestionFile());

        listCorrectAnswer.add(correctAnswer);
        tvQuestionNumber.setText("Soal " + (currentQuestionIndex + 1) + " dari " + totalQuestion);
        tvLevel.setText("Level " + level);
        try {
            listAnswersObj = new JSONObject(dataFun.get(currentQuestionIndex).getAnswersContent());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            daftarJawabanArray = listAnswersObj.getJSONArray("choices");
            ansA.setText((String) daftarJawabanArray.get(0));
            ansB.setText((String) daftarJawabanArray.get(1));
            ansC.setText((String) daftarJawabanArray.get(2));
            ansD.setText((String) daftarJawabanArray.get(3));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void postAnswer(Integer questionId, Integer answer) throws JSONException{
        RequestBody body;
        JSONStringer json = new JSONStringer();
        json.object();
        json.key("qa_id").value(questionId);
        json.key("submitted_answer").value(answer);
        json.endObject();

        body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json.toString());
        Call<ResponseSubmitFun> submitAnswerCall = endpoint.postSubmitAnswer(body);

        submitAnswerCall.enqueue(new Callback<ResponseSubmitFun>() {

            @Override
            public void onResponse(Call<ResponseSubmitFun> call, Response<ResponseSubmitFun> response) {
                try {
                    if (response.isSuccessful() && response.body().getAnswer() != null) {
                        Log.d("HAI", "onResponse: "+"OK");
                    } else {
                        Toast.makeText(getApplicationContext(), "Gagal Mengirim Data", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Gagal Mengirim Data!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseSubmitFun> call, Throwable t) {
                Toast.makeText(PlayActivity.this, "Gagal mengirim data"+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("GAGAL", "onFailure: "+t.getMessage());
            }
        });
    }

    void finishQuiz(){
        Log.d("HAI", "Pick Answer: "+listPickAnswer);
        Log.d("HAI", "Correct Answer: "+listCorrectAnswer);
        Log.d("HAI", "Question Id: "+listQuestionId);
        for(int i = 0; i < listPickAnswer.size(); i++){
            try {
                postAnswer(listQuestionId.get(i), listPickAnswer.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < listPickAnswer.size(); i++){
            if(listPickAnswer.get(i) == listCorrectAnswer.get(i)){
                score += 1;
            }
        }


        new AlertDialog.Builder(this)
                .setTitle("RESULT")
                .setMessage("Score is "+ score+" out of "+ totalQuestion)
                .setPositiveButton("Selesai",(dialogInterface, i) -> closeQuiz() )
                .setCancelable(false)
                .show();
    }

    public void closeQuiz(){
        Intent intent = new Intent(this, FunActivity.class);
        startActivity(intent);
        finish();
    }


}
