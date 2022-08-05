package com.example.stunting.data.model.fun;

import com.google.gson.annotations.SerializedName;

public class ResponsePerLevel {

    @SerializedName("1")
    private DataScorePerLevel level1;

    @SerializedName("2")
    private DataScorePerLevel level2;

    @SerializedName("3")
    private DataScorePerLevel level3;

    @SerializedName("4")
    private DataScorePerLevel level4;

    @SerializedName("5")
    private DataScorePerLevel level5;

    @SerializedName("6")
    private DataScorePerLevel level6;

    @SerializedName("7")
    private DataScorePerLevel level7;

    @SerializedName("8")
    private DataScorePerLevel level8;

    @SerializedName("9")
    private DataScorePerLevel level9;

    @SerializedName("10")
    private DataScorePerLevel level10;

    public ResponsePerLevel(DataScorePerLevel level1, DataScorePerLevel level2, DataScorePerLevel level3, DataScorePerLevel level4, DataScorePerLevel level5, DataScorePerLevel level6, DataScorePerLevel level7, DataScorePerLevel level8, DataScorePerLevel level9, DataScorePerLevel level10) {
        this.level1 = level1;
        this.level2 = level2;
        this.level3 = level3;
        this.level4 = level4;
        this.level5 = level5;
        this.level6 = level6;
        this.level7 = level7;
        this.level8 = level8;
        this.level9 = level9;
        this.level10 = level10;
    }

    public DataScorePerLevel getLevel1() {
        return level1;
    }

    public void setLevel1(DataScorePerLevel level1) {
        this.level1 = level1;
    }

    public DataScorePerLevel getLevel2() {
        return level2;
    }

    public void setLevel2(DataScorePerLevel level2) {
        this.level2 = level2;
    }

    public DataScorePerLevel getLevel3() {
        return level3;
    }

    public void setLevel3(DataScorePerLevel level3) {
        this.level3 = level3;
    }

    public DataScorePerLevel getLevel4() {
        return level4;
    }

    public void setLevel4(DataScorePerLevel level4) {
        this.level4 = level4;
    }

    public DataScorePerLevel getLevel5() {
        return level5;
    }

    public void setLevel5(DataScorePerLevel level5) {
        this.level5 = level5;
    }

    public DataScorePerLevel getLevel6() {
        return level6;
    }

    public void setLevel6(DataScorePerLevel level6) {
        this.level6 = level6;
    }

    public DataScorePerLevel getLevel7() {
        return level7;
    }

    public void setLevel7(DataScorePerLevel level7) {
        this.level7 = level7;
    }

    public DataScorePerLevel getLevel8() {
        return level8;
    }

    public void setLevel8(DataScorePerLevel level8) {
        this.level8 = level8;
    }

    public DataScorePerLevel getLevel9() {
        return level9;
    }

    public void setLevel9(DataScorePerLevel level9) {
        this.level9 = level9;
    }

    public DataScorePerLevel getLevel10() {
        return level10;
    }

    public void setLevel10(DataScorePerLevel level10) {
        this.level10 = level10;
    }
}
