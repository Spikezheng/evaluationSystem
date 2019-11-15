package com.spike.evaluationsystem.pojo;

public class SelfStrategy implements Strategy {
    private SelfEvaluation selfEvaluation;

    public SelfStrategy(SelfEvaluation selfEvaluation){
        this.selfEvaluation = selfEvaluation;
    }

    @Override
    public float calculate(Weight weight) {
        float score = (selfEvaluation.getStaff_comunication_ability() + selfEvaluation.getStaff_corporate_identity() +
                selfEvaluation.getStaff_enthusiasm() + selfEvaluation.getStaff_feedback_time() + selfEvaluation.getStaff_responsibility() +
                selfEvaluation.getStaff_task_completion_time() + selfEvaluation.getStaff_task_response_tim()) * weight.getSelfEvaluationWeight();
        score = (score*100)/100;
        return score;
    }
}
