package com.spike.evaluationsystem.dao;

import com.spike.evaluationsystem.pojo.Project;

import java.util.ArrayList;

public class ProjectDAOTest {

    public static void main(String [] args){
        ArrayList projectIds = new ArrayList();
        projectIds.add(1);

        ArrayList<Project> projects = new ProjectDAO().retrieve(projectIds);
        System.out.println(projects.get(0));
    }
}
