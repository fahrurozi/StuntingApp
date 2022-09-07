package com.stuntech.stunting.data.model.fun;

public class TestModel {
    private Integer level;

    private DataScorePerLevel dataScorePerLevel;

    public TestModel(Integer level, DataScorePerLevel dataScorePerLevel) {
        this.level = level;
        this.dataScorePerLevel = dataScorePerLevel;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public DataScorePerLevel getDataScorePerLevel() {
        return dataScorePerLevel;
    }

    public void setDataScorePerLevel(DataScorePerLevel dataScorePerLevel) {
        this.dataScorePerLevel = dataScorePerLevel;
    }
}
