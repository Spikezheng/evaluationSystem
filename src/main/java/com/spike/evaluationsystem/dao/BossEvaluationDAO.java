package com.spike.evaluationsystem.dao;

import com.spike.evaluationsystem.pojo.BossEvaluation;

import java.sql.*;

public class BossEvaluationDAO {
    private String host = "jdbc:mysql://127.0.0.1:3306/evaluationsystem?characterEncoding=UTF-8";
    private String admin = "root";
    private String password = "root";

    public BossEvaluationDAO(){
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
    public BossEvaluation retrieve(int bossEvaluationId){
        BossEvaluation bossEvaluation = null;
        String sql = "select * from bossEvaluation where bossEvaluationId = ?";

        try(Connection connection = this.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,bossEvaluationId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                bossEvaluation = new BossEvaluation();
                bossEvaluation.setBossEvaluationId(bossEvaluationId);
                bossEvaluation.setStaff_corporate_identity(resultSet.getFloat(2));
                bossEvaluation.setStaff_comunication_ability(resultSet.getFloat(3));
                bossEvaluation.setStaff_responsibility(resultSet.getFloat(4));
                bossEvaluation.setStaff_enthusiasm(resultSet.getFloat(5));
                bossEvaluation.setStaff_task_completion_time(resultSet.getFloat(6));
                bossEvaluation.setStaff_feedback_time(resultSet.getFloat(7));
                bossEvaluation.setStaff_task_response_tim(resultSet.getFloat(8));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return bossEvaluation;
    }

    //插入
    public int add(BossEvaluation bossEvaluation){
        int bossEvaluationId = 0;
        String sql = "insert into bossEvaluation values( null , ?, ?, ?, ?, ?, ?, ?)";

        try(Connection connection = this.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setFloat(1, bossEvaluation.getStaff_corporate_identity());
            preparedStatement.setFloat(2, bossEvaluation.getStaff_comunication_ability());
            preparedStatement.setFloat(3, bossEvaluation.getStaff_responsibility());
            preparedStatement.setFloat(4, bossEvaluation.getStaff_enthusiasm());
            preparedStatement.setFloat(5, bossEvaluation.getStaff_task_completion_time());
            preparedStatement.setFloat(6, bossEvaluation.getStaff_feedback_time());
            preparedStatement.setFloat(7, bossEvaluation.getStaff_task_response_tim());
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                bossEvaluationId = resultSet.getInt(1);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return bossEvaluationId;
    }

    //更新
    public void update(int bossEvaluationId, BossEvaluation bossEvaluation){
        String sql = "update bossEvaluation set staff_corporate_identity = ?, staff_comunication_ability = ?, staff_responsibility = ?, " +
                "staff_enthusiasm = ?, staff_task_completion_time = ?, staff_feedback_time = ?, staff_task_response_tim = ? where bossEvaluationId = ?";

        try(Connection connection = this.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setFloat(1, bossEvaluation.getStaff_corporate_identity());
            preparedStatement.setFloat(2, bossEvaluation.getStaff_comunication_ability());
            preparedStatement.setFloat(3, bossEvaluation.getStaff_responsibility());
            preparedStatement.setFloat(4, bossEvaluation.getStaff_enthusiasm());
            preparedStatement.setFloat(5, bossEvaluation.getStaff_task_completion_time());
            preparedStatement.setFloat(6, bossEvaluation.getStaff_feedback_time());
            preparedStatement.setFloat(7, bossEvaluation.getStaff_task_response_tim());
            preparedStatement.setInt(8, bossEvaluationId);
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return;
    }

    @Override
    public String toString(){
        return super.toString();
    }

}
