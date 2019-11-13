package com.spike.evaluationsystem.pojo;

public class SelfEvaluation extends Evaluation {
    private int selfEvaluationId;

    public void setSelfEvaluationId(int selfEvaluationId){
        this.selfEvaluationId = selfEvaluationId;
    }

    public int getSelfEvaluationId(){
        return this.selfEvaluationId;
    }
}
