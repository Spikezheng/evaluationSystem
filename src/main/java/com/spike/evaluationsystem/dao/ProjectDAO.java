package com.spike.evaluationsystem.dao;

import com.spike.evaluationsystem.pojo.Employee;
import com.spike.evaluationsystem.pojo.Project;

import java.sql.*;
import java.util.ArrayList;

public class ProjectDAO {

    private String host = "jdbc:mysql://127.0.0.1:3306/evaluationsystem?characterEncoding=UTF-8";
    private String admin = "root";
    private String password = "root";

    public ProjectDAO(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.host, this.admin, this.password);
    }

    //查找
    public ArrayList<Project> retrieve(ArrayList projectIds){
        ArrayList<Project> projects = new ArrayList<>();

        String sql = "select * from project where projectId = ?";

        try(Connection connection = this.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for(int i=0; i<projectIds.size(); i++){
                preparedStatement.setInt(1, (int)projectIds.get(i));
                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){
                    Project project = new Project();
                    project.setName(resultSet.getString("name"));
                    project.setScore(resultSet.getFloat("score"));
                    project.setTime(resultSet.getString("time"));
                    project.setProjectId(resultSet.getInt("projectId"));
                    project.setLeaderId(resultSet.getInt("leaderId"));
                    projects.add(project);
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

            return projects;
    }


    //获取成员id
    public ArrayList getEmployeeIds(int projectId){
        ArrayList employeeIds = new ArrayList<>();

        String sql ="select * from employee_project where projectId = ?";
        try(Connection connection = this.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, projectId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                employeeIds.add(resultSet.getInt("id"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return  employeeIds;
    }
}
