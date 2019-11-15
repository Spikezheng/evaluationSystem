package com.spike.evaluationsystem.pojo;

public class ProjectStrategy implements Strategy {
    private float score;

    public ProjectStrategy(float score){
        this.score = score;
    }

    @Override
    public float calculate(Weight weight) {
        score = weight.getProjectWeight();
        score = (score*100) / 100;
        return score;
    }
}
