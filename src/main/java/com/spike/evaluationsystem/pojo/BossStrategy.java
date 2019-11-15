package com.spike.evaluationsystem.pojo;

public class BossStrategy implements Strategy {
    private BossEvaluation bossEvaluation;

    public BossStrategy(BossEvaluation bossEvaluation){
        this.bossEvaluation = bossEvaluation;
    }


    @Override
    public float calculate(Weight weight) {
        float score = bossEvaluation.getStaff_comunication_ability() + bossEvaluation.getStaff_corporate_identity() + bossEvaluation.getStaff_enthusiasm() +
                bossEvaluation.getStaff_feedback_time() + bossEvaluation.getStaff_responsibility() + bossEvaluation.getStaff_task_completion_time() +
                bossEvaluation.getStaff_task_response_tim();

        score = score * weight.getBossEvaluationWeight();
        score = (score*100) / 100;

        return score;
    }
}
