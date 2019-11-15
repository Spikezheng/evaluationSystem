package com.spike.evaluationsystem.dao;

import com.spike.evaluationsystem.pojo.Weight;

import java.sql.*;

public class ScoreDAO {
    private String host = "jdbc:mysql://127.0.0.1:3306/evaluationsystem?characterEncoding=UTF-8";
    private String admin = "root";
    private String password = "root";

    public ScoreDAO(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.host, this.admin, this.password);
    }

    //更新自我分数
    public void updateSelfScore(float selfScore, int id){
        String sql = "update score set selfScore = ? where id = ?";
        try(Connection connection = this.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setFloat(1, selfScore);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return;
    }

    //更新领导分数
    public void updateBossScore(float bossScore, int id){
        String sql = "update score set bossScore = ? where id = ?";
        try(Connection connection = this.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setFloat(1, bossScore);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return;
    }

    //更新出席分数
    public void updateAttendanceScore(float attendanceScore, int id){
        String sql = "update score set attendanceScore = ? where id = ?";
        try(Connection connection = this.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setFloat(1, attendanceScore);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return;
    }

    //更新项目分数
    public void updateProjectScore(float projectScore, int id){
        String sql = "update score set projectScore = ? where id = ?";
        try(Connection connection = this.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setFloat(1, projectScore);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return;
    }

    //获得总分
    public float getTotal(int id){
        float score = 0f;

        String sql = "select * from score where id = ?";
        try(Connection connection = this.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setFloat(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                score += resultSet.getFloat(1);
                score += resultSet.getFloat(2);
                score += resultSet.getFloat(3);
                score += resultSet.getFloat(4);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return score;
    }
}
