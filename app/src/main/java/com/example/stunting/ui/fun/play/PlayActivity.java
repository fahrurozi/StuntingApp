package com.example.stunting.ui.fun.play;

import android.app.AlertDialog;
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
import com.example.stunting.data.network.ApiEndpoint;
import com.example.stunting.data.network.ApiService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dmax.dialog.SpotsDialog;
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

//        Log.d("HAI", "onResponse: " + jsonObject);
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
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

            Toast.makeText(this, "submit", Toast.LENGTH_SHORT).show();
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


//            Toast.makeText(this, "jawaban", Toast.LENGTH_SHORT).show();
//            Log.d("HAI", "onClick: "+listAnswersObj);
//            Log.d("HAI", "onClick: "+daftarJawabanArray);
//            Log.d("HAI", "onClick: "+pickAnswer);
            Log.d("HAI", "Correct Answer: "+correctAnswer);

        }
    }

//    void loadNewQuestion(){
//
//        if(currentQuestionIndex == totalQuestion ){
//            finishQuiz();
//            return;
//        }
//
//        questionTextView.setText(QuestionAnswer.question[currentQuestionIndex]);
//        ansA.setText(QuestionAnswer.choices[currentQuestionIndex][0]);
//        ansB.setText(QuestionAnswer.choices[currentQuestionIndex][1]);
//        ansC.setText(QuestionAnswer.choices[currentQuestionIndex][2]);
//        ansD.setText(QuestionAnswer.choices[currentQuestionIndex][3]);
//
//    }

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
        wvQuestion.loadUrl(ApiService.BASE_URL + "static/" + dataFun.get(0).getQuestionFile());
        tvQuestionNumber.setText("Soal " + (currentQuestionIndex + 1) + " dari " + totalQuestion);
        tvLevel.setText("Level " + level);
        try {
            listAnswersObj = new JSONObject(dataFun.get(0).getAnswersContent());
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

    public void postAnswer(){

    }

    void finishQuiz(){

        new AlertDialog.Builder(this)
                .setTitle("HAI")
                .setMessage("Score is "+ score+" out of "+ totalQuestion)
                .setCancelable(false)
                .show();
    }


}
