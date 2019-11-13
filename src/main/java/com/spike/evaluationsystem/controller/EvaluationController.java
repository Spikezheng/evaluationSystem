package com.spike.evaluationsystem.controller;

import com.spike.evaluationsystem.dao.*;
import com.spike.evaluationsystem.pojo.*;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class EvaluationController {

    // 上传自我评价
    @PostMapping("/evaluation/self")
    public String uploadSelfEvaluaion(@RequestParam(value = "id", required = true) int id, @RequestBody SelfEvaluation selfEvaluation){
        EmployeeDAO employeeDAO = new EmployeeDAO();
        Employee retrieveEmployee = employeeDAO.retrieve(id);

        //判断是否第一次上传
        if(retrieveEmployee.getSelfEvaluationId() == 0){
            //第一次上传
            int selfEvaluationId = new SelfEvaluationDAO().add(selfEvaluation);
            retrieveEmployee.setSelfEvaluationId(selfEvaluationId);
            employeeDAO.update(retrieveEmployee);
        }else{
            //更新自我评价
            new SelfEvaluationDAO().update(retrieveEmployee.getSelfEvaluationId(), selfEvaluation);
        }
        return "hello";
    }

    //获取领导评价
    @ResponseBody
    @PostMapping("/evaluation/boss")
    public JSONObject getBossEvaluation(@RequestBody Employee employee){
        Employee retrieveEmployee = new EmployeeDAO().retrieve(employee.getId());
        BossEvaluation bossEvaluation = new BossEvaluationDAO().retrieve(retrieveEmployee.getBossEvaluationId());
        return JSONObject.fromObject(bossEvaluation.toString());
    }

    //获取季度评分
    @ResponseBody
    @PostMapping("/score/quarter")
    public JSONObject getQuarterScore(@RequestBody Employee employee){
        Employee retrieveEmployee = new EmployeeDAO().retrieve(employee.getId());
        ArrayList projectIds = new EmployeeDAO().getProjectIds(retrieveEmployee);
        ArrayList<Project> projects = new ProjectDAO().retrieve(projectIds);
        retrieveEmployee.setProjects(projects);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("attendanceScore", retrieveEmployee.getAttendanceScore());
        JSONArray jsonArray = new JSONArray();

        for(int i=0; i<retrieveEmployee.getProjects().size(); i++){
            JSONObject subJsonObject = new JSONObject();
            subJsonObject.put("name", retrieveEmployee.getProjects().get(i).getName());
            subJsonObject.put("score", retrieveEmployee.getProjects().get(i).getScore());
            jsonArray.add(subJsonObject);
        }

        jsonObject.put("projects", jsonArray);
        jsonObject.put("totalScore", retrieveEmployee.getTotalScore());

        return jsonObject;
    }

    //获取项目
    @PostMapping("/information/project")
    @ResponseBody
    public JSONArray getProjectInformation(@RequestBody Employee employee){
        EmployeeDAO employeeDAO = new EmployeeDAO();
        Employee retrieveEmployee = employeeDAO.retrieve(employee.getId());
        ArrayList projectIds = employeeDAO.getProjectIds(retrieveEmployee);

        ProjectDAO projectDAO = new ProjectDAO();
        ArrayList<Project> projects = projectDAO.retrieve(projectIds);
        retrieveEmployee.setProjects(projects);

        for(int i=0; i<retrieveEmployee.getProjects().size(); i++){
            ArrayList employeeIds = projectDAO.getEmployeeIds(retrieveEmployee.getProjects().get(i).getProjectId());
            ArrayList<Employee> employees = employeeDAO.retrieve(employeeIds);
            retrieveEmployee.getProjects().get(i).setEmployees(employees);
        }

        JSONArray jsonArray = new JSONArray();

        int projectsNum = retrieveEmployee.getProjects().size();
        for(int i=0; i<projectsNum; i++){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("projecName", retrieveEmployee.getProjects().get(i).getName());
            jsonObject.put("leader", employeeDAO.retrieve(retrieveEmployee.getProjects().get(i).getLeaderId()).getUsername());
            jsonObject.put("beginData", retrieveEmployee.getProjects().get(i).getTime());


            JSONArray subJsonArray = new JSONArray();
            int employeesNum = retrieveEmployee.getProjects().get(i).getEmployees().size();
            for(int j=0; j<employeesNum; j++){
                JSONObject subJsonObject = new JSONObject();
                Employee subEmployee = retrieveEmployee.getProjects().get(i).getEmployees().get(j);
                subJsonObject.put("id", subEmployee.getId());
                subJsonObject.put("name", subEmployee.getUsername());
                subJsonObject.put("score", retrieveEmployee.getProjects().get(i).getScore());
                subJsonArray.add(subJsonObject);
            }
            jsonObject.put("members", subJsonArray);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @PostMapping("/evaluation/member")
    @ResponseBody
    public JSONObject uploadMemberEvaluation(@RequestBody MemberEvaluation memberEvaluation){
        MemberEvaluationDAO memberEvaluationDAO = new MemberEvaluationDAO();

        if(memberEvaluationDAO.find(memberEvaluation)){
            //存在记录
            memberEvaluationDAO.update(memberEvaluation);
        }else{
            //不存在记录
            memberEvaluationDAO.add(memberEvaluation);
        }
        JSONObject jsonObject = new JSONObject();
        float score = new MemberStrategy(memberEvaluation).calculate();
        score = (score*100)/100;
        jsonObject.put("score", score);
        return jsonObject;
    }
}
