package com.spike.evaluationsystem.pojo;

import java.util.ArrayList;

public class Employee{
    private String username;
    private String password;
    private int id;
    private int selfEvaluationId;
    private int bossEvaluationId;
    private float attendanceScore = 0;
    private float totalScore = 0;
    private ArrayList<Project> projects;

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setSelfEvaluationId(int selfEvaluationId){
        this.selfEvaluationId = selfEvaluationId;
    }

    public void setBossEvaluationId(int bossEvaluationId){
        this.bossEvaluationId = bossEvaluationId;
    }

    public void setAttendanceScore(float attendanceScore) {
        this.attendanceScore = attendanceScore;
    }

    public void setTotalScore(float totalScore) {
        this.totalScore = totalScore;
    }

    public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;
    }

    public float getAttendanceScore() {
        return attendanceScore;
    }

    public float getTotalScore() {
        return totalScore;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public int getId(){
        return this.id;
    }

    public int getSelfEvaluationId(){
        return this.selfEvaluationId;
    }

    public int getBossEvaluationId(){
        return this.bossEvaluationId;
    }

    public ArrayList<Project> getProjects() {
        return projects;
    }
}