package com.spike.evaluationsystem.pojo;

public class Weight {
    private String department;
    private float attendanceWeight;
    private float projectWeight;
    private float selfEvaluationWeight;
    private float bossEvaluationWeight;

    public float getAttendanceWeight() {
        return attendanceWeight;
    }

    public float getBossEvaluationWeight() {
        return bossEvaluationWeight;
    }

    public float getProjectWeight() {
        return projectWeight;
    }

    public float getSelfEvaluationWeight() {
        return selfEvaluationWeight;
    }

    public String getDepartment() {
        return department;
    }

    public void setAttendanceWeight(float attendanceWeight) {
        this.attendanceWeight = attendanceWeight;
    }

    public void setBossEvaluationWeight(float bossEvaluationWeight) {
        this.bossEvaluationWeight = bossEvaluationWeight;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setProjectWeight(float projectWeight) {
        this.projectWeight = projectWeight;
    }

    public void setSelfEvaluationWeight(float selfEvaluationWeight) {
        this.selfEvaluationWeight = selfEvaluationWeight;
    }

}
