package com.spike.evaluationsystem.pojo;

public class MemberStrategy implements Strategy {
    private MemberEvaluation memberEvaluation;
    public MemberStrategy(MemberEvaluation memberEvaluation){
        this.memberEvaluation = memberEvaluation;
    }

    @Override
    public float calculate(Weight weight) {
        float score = (memberEvaluation.getStaff_comunication_ability()  + memberEvaluation.getStaff_enthusiasm() +
                memberEvaluation.getStaff_task_completion_time() ) * weight.getProjectWeight();
        return score;
    }
}
