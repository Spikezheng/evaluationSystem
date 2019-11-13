package com.spike.evaluationsystem.pojo;

public class MemberStrategy implements Strategy {
    private MemberEvaluation memberEvaluation;
    public MemberStrategy(MemberEvaluation memberEvaluation){
        this.memberEvaluation = memberEvaluation;
    }

    @Override
    public float calculate() {
        float score = (memberEvaluation.getStaff_comunication_ability() * 0.5f) + (memberEvaluation.getStaff_enthusiasm() * 0.8f) +
                (memberEvaluation.getStaff_task_completion_time() * 1.2f);
        return score;
    }
}
