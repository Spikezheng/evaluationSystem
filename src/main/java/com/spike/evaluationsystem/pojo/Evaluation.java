package com.spike.evaluationsystem.pojo;

public class Evaluation {
    private float staff_corporate_identity;
    private float staff_comunication_ability;
    private float staff_responsibility;
    private float staff_enthusiasm;
    private float staff_task_completion_time;
    private float staff_feedback_time;
    private float staff_task_response_tim;

    public void setStaff_corporate_identity(float staff_corporate_identity){
        this.staff_corporate_identity = staff_corporate_identity;
    }

    public void setStaff_comunication_ability(float staff_comunication_ability){
        this.staff_comunication_ability = staff_comunication_ability;
    }

    public void setStaff_responsibility(float staff_responsibility){
        this.staff_responsibility = staff_responsibility;
    }

    public void setStaff_enthusiasm(float staff_enthusiasm){
        this.staff_enthusiasm = staff_enthusiasm;
    }

    public void setStaff_task_completion_time(float staff_task_completion_time){
        this.staff_task_completion_time = staff_task_completion_time;
    }

    public void setStaff_feedback_time(float staff_feedback_time) {
        this.staff_feedback_time = staff_feedback_time;
    }

    public void setStaff_task_response_tim(float staff_task_response_tim){
        this.staff_task_response_tim = staff_task_response_tim;
    }

    public float getStaff_corporate_identity(){
        return this.staff_corporate_identity;
    }

    public float getStaff_comunication_ability(){
        return this.staff_comunication_ability;
    }

    public float getStaff_responsibility(){
        return this.staff_responsibility;
    }

    public float getStaff_enthusiasm(){
        return this.staff_enthusiasm;
    }

    public float getStaff_task_completion_time(){
        return this.staff_task_completion_time;
    }

    public float getStaff_feedback_time() {
        return staff_feedback_time;
    }

    public float getStaff_task_response_tim(){
        return this.staff_task_response_tim;
    }

    @Override
    public String toString(){
        return "{ staff_corporate_identity: " + this.getStaff_corporate_identity() + "," +
                " staff_comunication_ability: " + this.getStaff_comunication_ability() + "," +
                " staff_responsibility: " + this.getStaff_responsibility() + "," +
                " staff_enthusiasm: " + this.getStaff_enthusiasm() + "," +
                " staff_task_completion_time: " + this.getStaff_task_completion_time() + "," +
                " staff_feedback_time: " + this.getStaff_feedback_time() + "," +
                " staff_task_response_tim: " + this.getStaff_task_response_tim() + "}";
    }
}
