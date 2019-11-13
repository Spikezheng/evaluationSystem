package com.spike.evaluationsystem.dao;

import com.spike.evaluationsystem.pojo.Employee;
import com.spike.evaluationsystem.pojo.Project;

import java.sql.*;
import java.util.ArrayList;

public class EmployeeDAO {
    private String host = "jdbc:mysql://127.0.0.1:3306/evaluationsystem?characterEncoding=UTF-8";
    private String admin = "root";
    private String password = "root";

    public EmployeeDAO(){
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
    public Employee retrieve(Employee employee) {
        Employee retrieveEmployee = null;

        String sql = "select * from employee where username = ? and password = ?";
        try(Connection connection = this.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, employee.getUsername());
            preparedStatement.setString(2, employee.getPassword());

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                retrieveEmployee = new Employee();
                retrieveEmployee.setId(resultSet.getInt("id"));
                retrieveEmployee.setSelfEvaluationId(resultSet.getInt("selfEvaluationId"));
                retrieveEmployee.setBossEvaluationId(resultSet.getInt("bossEvaluationId"));
                retrieveEmployee.setUsername(resultSet.getString("username"));
                retrieveEmployee.setPassword(resultSet.getString("password"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return retrieveEmployee;
    }

    public Employee retrieve(int id) {
        Employee retrieveEmployee = null;

        String sql = "select * from employee where id = ?";
        try(Connection connection = this.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                retrieveEmployee = new Employee();
                retrieveEmployee.setId(resultSet.getInt("id"));
                retrieveEmployee.setSelfEvaluationId(resultSet.getInt("selfEvaluationId"));
                retrieveEmployee.setBossEvaluationId(resultSet.getInt("bossEvaluationId"));
                retrieveEmployee.setUsername(resultSet.getString("username"));
                retrieveEmployee.setPassword(resultSet.getString("password"));
                retrieveEmployee.setAttendanceScore(resultSet.getFloat("attendanceScore"));
                retrieveEmployee.setTotalScore(resultSet.getFloat("totalScore"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return retrieveEmployee;
    }

    //获取项目id
    public ArrayList getProjectIds(Employee employee){
        ArrayList projectIds = null;

        String sql = "select * from employee_project where id = ?";
        try(Connection connection = this.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, employee.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            projectIds = new ArrayList();
            while(resultSet.next()){
                projectIds.add(resultSet.getInt("projectId"));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return projectIds;
    }

    //更新
    public void update(Employee employee){
        String sql = "update employee set selfEvaluationId = ?, bossEvaluationId = ? where id = ?";
        try(Connection connection = this.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, employee.getSelfEvaluationId());
            preparedStatement.setInt(2, employee.getBossEvaluationId());
            preparedStatement.setInt(3, employee.getId());
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return;
    }

    //获取成员
    public ArrayList<Employee> retrieve(ArrayList projectIds){
        ArrayList<Employee> employees = new ArrayList<>();

        for(int i=0; i<projectIds.size(); i++){
            Employee employee = retrieve((int)projectIds.get(i));
            employees.add(employee);
        }

        return  employees;
    }
}
