package com.example.stunting.data.model.fun;

import com.google.gson.annotations.SerializedName;

public class DataFun {
    @SerializedName("id")
    private Integer id;

    @SerializedName("question_file")
    private String questionFile;

    @SerializedName("answers_file")
    private String answerFile;

    @SerializedName("level")
    private Integer level;

    @SerializedName("question_content")
    private String questionContent;

    @SerializedName("answers_content")
    private String answersContent;

    @SerializedName("correct_answer")
    private Integer correctAnswer;

    public DataFun(Integer id, String questionFile, String answerFile, Integer level, String questionContent, String answersContent, Integer correctAnswer) {
        this.id = id;
        this.questionFile = questionFile;
        this.answerFile = answerFile;
        this.level = level;
        this.questionContent = questionContent;
        this.answersContent = answersContent;
        this.correctAnswer = correctAnswer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestionFile() {
        return questionFile;
    }

    public void setQuestionFile(String questionFile) {
        this.questionFile = questionFile;
    }

    public String getAnswerFile() {
        return answerFile;
    }

    public void setAnswerFile(String answerFile) {
        this.answerFile = answerFile;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getAnswersContent() {
        return answersContent;
    }

    public void setAnswersContent(String answersContent) {
        this.answersContent = answersContent;
    }

    public Integer getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Integer correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
