package com.spike.evaluationsystem.dao;

import com.spike.evaluationsystem.pojo.SelfEvaluation;

import java.sql.*;

public class SelfEvaluationDAO {
    private String host = "jdbc:mysql://127.0.0.1:3306/evaluationsystem?characterEncoding=UTF-8";
    private String admin = "root";
    private String password = "root";

    public SelfEvaluationDAO(){
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
    public SelfEvaluation retrieve(int selfEvaluationId){
        SelfEvaluation selfEvaluation = null;
        String sql = "select * from selfEvaluation where selfEvaluationId = ?";

        try(Connection connection = this.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,selfEvaluationId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                selfEvaluation.setSelfEvaluationId(selfEvaluationId);
                selfEvaluation.setStaff_corporate_identity(resultSet.getFloat(2));
                selfEvaluation.setStaff_comunication_ability(resultSet.getFloat(3));
                selfEvaluation.setStaff_responsibility(resultSet.getFloat(4));
                selfEvaluation.setStaff_enthusiasm(resultSet.getFloat(5));
                selfEvaluation.setStaff_task_completion_time(resultSet.getFloat(6));
                selfEvaluation.setStaff_feedback_time(resultSet.getFloat(7));
                selfEvaluation.setStaff_task_response_tim(resultSet.getFloat(8));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return selfEvaluation;
    }

    //插入
    public int add(SelfEvaluation selfEvaluation){
        int selfEvaluationId = 0;
        String sql = "insert into selfEvaluation values( null , ?, ?, ?, ?, ?, ?, ?)";

        try(Connection connection = this.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setFloat(1, selfEvaluation.getStaff_corporate_identity());
            preparedStatement.setFloat(2, selfEvaluation.getStaff_comunication_ability());
            preparedStatement.setFloat(3, selfEvaluation.getStaff_responsibility());
            preparedStatement.setFloat(4, selfEvaluation.getStaff_enthusiasm());
            preparedStatement.setFloat(5, selfEvaluation.getStaff_task_completion_time());
            preparedStatement.setFloat(6, selfEvaluation.getStaff_feedback_time());
            preparedStatement.setFloat(7, selfEvaluation.getStaff_task_response_tim());
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                selfEvaluationId = resultSet.getInt(1);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return selfEvaluationId;
    }

    //更新
    public void update(int selfEvaluationId, SelfEvaluation selfEvaluation){
        String sql = "update selfEvaluation set staff_corporate_identity = ?, staff_comunication_ability = ?, staff_responsibility = ?, " +
                "staff_enthusiasm = ?, staff_task_completion_time = ?, staff_feedback_time = ?, staff_task_response_tim = ? where selfEvaluationId = ?";

        try(Connection connection = this.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setFloat(1, selfEvaluation.getStaff_corporate_identity());
            preparedStatement.setFloat(2, selfEvaluation.getStaff_comunication_ability());
            preparedStatement.setFloat(3, selfEvaluation.getStaff_responsibility());
            preparedStatement.setFloat(4, selfEvaluation.getStaff_enthusiasm());
            preparedStatement.setFloat(5, selfEvaluation.getStaff_task_completion_time());
            preparedStatement.setFloat(6, selfEvaluation.getStaff_feedback_time());
            preparedStatement.setFloat(7, selfEvaluation.getStaff_task_response_tim());
            preparedStatement.setInt(8, selfEvaluationId);
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return;
    }


}
