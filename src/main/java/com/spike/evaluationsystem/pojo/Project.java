package com.spike.evaluationsystem.pojo;

import java.util.ArrayList;

public class Project {
    private String name;
    private float score;
    private String time;
    private int projectId;
    private int leaderId;
    private ArrayList<Employee> employees;
    private String department;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public void setLeaderId(int leaderId) {
        this.leaderId = leaderId;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    public String getName() {
        return name;
    }

    public float getScore() {
        return score;
    }

    public String getTime() {
        return time;
    }

    public int getLeaderId() {
        return leaderId;
    }

    public int getProjectId() {
        return projectId;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public String toString(){
        return "{ name: " + name + ", score: "  + score + " }";
    }
}
