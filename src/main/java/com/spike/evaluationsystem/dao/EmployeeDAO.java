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
                retrieveEmployee.setDepartment(resultSet.getString("department"));
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

    //列举
    public ArrayList<Employee> list(){
        ArrayList<Employee> employees = new ArrayList<>();

        String sql = "select * from employee";
        try(Connection connection = this.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Employee employee = new Employee();
                employee.setId(resultSet.getInt("id"));
                employee.setSelfEvaluationId(resultSet.getInt("selfEvaluationId"));
                employee.setBossEvaluationId(resultSet.getInt("bossEvaluationId"));
                employee.setUsername(resultSet.getString("username"));
                employee.setPassword(resultSet.getString("password"));
                employee.setAttendanceScore(resultSet.getFloat("attendanceScore"));
                employee.setTotalScore(resultSet.getFloat("totalScore"));
                employee.setDepartment(resultSet.getString("department"));
                employees.add(employee);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return employees;
    }
}
