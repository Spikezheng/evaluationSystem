package com.spike.evaluationsystem.dao;

import com.spike.evaluationsystem.pojo.MemberEvaluation;

import java.sql.*;

public class MemberEvaluationDAO {
    private String host = "jdbc:mysql://127.0.0.1:3306/evaluationsystem?characterEncoding=UTF-8";
    private String admin = "root";
    private String password = "root";

    public MemberEvaluationDAO(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.host, this.admin, this.password);
    }

    //查找是否存在
    public boolean find(MemberEvaluation memberEvaluation){
        if(retrieve(memberEvaluation) == null)
            return false;
        else
            return true;
    }

    //查找
    public MemberEvaluation retrieve(MemberEvaluation memberEvaluation){
        MemberEvaluation retrieveMemberEvaluation = null;

        String sql = "select * from memberEvaluation where id = ? and memberId = ?";
        try(Connection connection = this.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, memberEvaluation.getId());
            preparedStatement.setInt(2, memberEvaluation.getMemberId());

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                retrieveMemberEvaluation = new MemberEvaluation();
                retrieveMemberEvaluation.setId(resultSet.getInt("id"));
                retrieveMemberEvaluation.setMemberId(resultSet.getInt("memberId"));
                retrieveMemberEvaluation.setStaff_comunication_ability(resultSet.getFloat("staff_comunication_ability"));
                retrieveMemberEvaluation.setStaff_enthusiasm(resultSet.getFloat("staff_enthusiasm"));
                retrieveMemberEvaluation.setStaff_task_completion_time(resultSet.getFloat("staff_task_completion_time"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

            return retrieveMemberEvaluation;
    }

    //增加
    public void add(MemberEvaluation memberEvaluation){

        String sql = "insert into memberEvaluation values(?, ?, ?, ?, ?)";
        try(Connection connection = this.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, memberEvaluation.getId());
            preparedStatement.setInt(2, memberEvaluation.getMemberId());
            preparedStatement.setFloat(3, memberEvaluation.getStaff_comunication_ability());
            preparedStatement.setFloat(4, memberEvaluation.getStaff_enthusiasm());
            preparedStatement.setFloat(5, memberEvaluation.getStaff_task_completion_time());
            preparedStatement.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return;
    }

    //更新
    public void update(MemberEvaluation memberEvaluation){
        String sql = "update memberEvaluation set staff_comunication_ability = ?, staff_enthusiasm = ?, staff_task_completion_time = ?" +
                "where id = ? and memberId = ?";
        try(Connection connection = this.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setFloat(1, memberEvaluation.getStaff_comunication_ability());
            preparedStatement.setFloat(2, memberEvaluation.getStaff_enthusiasm());
            preparedStatement.setFloat(3, memberEvaluation.getStaff_task_completion_time());
            preparedStatement.setInt(4, memberEvaluation.getId());
            preparedStatement.setInt(5, memberEvaluation.getMemberId());
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }

            return;
    }
}
