package com.spike.evaluationsystem.dao;

import com.spike.evaluationsystem.pojo.Employee;
import com.spike.evaluationsystem.pojo.Weight;

import java.sql.*;

public class WeightDAO {

    private String host = "jdbc:mysql://127.0.0.1:3306/evaluationsystem?characterEncoding=UTF-8";
    private String admin = "root";
    private String password = "root";

    public WeightDAO(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.host, this.admin, this.password);
    }

    // 插入
    public void add(Weight weight){
        String sql = "insert into weight values (?, ?, ?, ?, ?)";
        try(Connection connection = this.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setFloat(2, weight.getAttendanceWeight());
            preparedStatement.setFloat(3, weight.getProjectWeight());
            preparedStatement.setFloat(4, weight.getSelfEvaluationWeight());
            preparedStatement.setFloat(5, weight.getBossEvaluationWeight());
            preparedStatement.setString(1, weight.getDepartment());
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return;
    }

    //更新
    public void update(Weight weight){
        String sql = "update weight set attendanceWeight = ?, projectWeight = ?, selfEvaluationWeight = ?, bossEvaluationWeight = ? where department = ?";
        try(Connection connection = this.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setFloat(1, weight.getAttendanceWeight());
            preparedStatement.setFloat(2, weight.getProjectWeight());
            preparedStatement.setFloat(3, weight.getSelfEvaluationWeight());
            preparedStatement.setFloat(4, weight.getBossEvaluationWeight());
            preparedStatement.setString(5, weight.getDepartment());
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return;
    }

    //查找
    public Weight retrieve(String department) {
        Weight retrieveWeight = null;

        String sql = "select * from weight where department = ?";
        try(Connection connection = this.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, department);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                retrieveWeight = new Weight();
                retrieveWeight.setAttendanceWeight(resultSet.getFloat("attendanceWeight"));
                retrieveWeight.setProjectWeight(resultSet.getFloat("projectWeight"));
                retrieveWeight.setSelfEvaluationWeight(resultSet.getFloat("selfEvaluationWeight"));
                retrieveWeight.setBossEvaluationWeight(resultSet.getFloat("bossEvaluationWeight"));
                retrieveWeight.setDepartment(resultSet.getString("department"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return retrieveWeight;
    }
}
