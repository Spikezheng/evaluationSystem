package com.spike.evaluationsystem.pojo;

public class MemberEvaluation {
    private int id;
    private int memberId;
    private float staff_comunication_ability;
    private float staff_enthusiasm;
    private float staff_task_completion_time;

    public void setId(int id) {
        this.id = id;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public void setStaff_comunication_ability(float staff_comunication_ability) {
        this.staff_comunication_ability = staff_comunication_ability;
    }

    public void setStaff_enthusiasm(float staff_enthusiasm) {
        this.staff_enthusiasm = staff_enthusiasm;
    }

    public void setStaff_task_completion_time(float staff_task_completion_time) {
        this.staff_task_completion_time = staff_task_completion_time;
    }

    public float getStaff_comunication_ability() {
        return staff_comunication_ability;
    }

    public float getStaff_enthusiasm() {
        return staff_enthusiasm;
    }

    public float getStaff_task_completion_time() {
        return staff_task_completion_time;
    }

    public int getId() {
        return id;
    }

    public int getMemberId() {
        return memberId;
    }
}
