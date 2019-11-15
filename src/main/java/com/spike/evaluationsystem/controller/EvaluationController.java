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
    public JSONObject uploadSelfEvaluaion(@RequestParam(value = "id", required = true) int id, @RequestBody SelfEvaluation selfEvaluation){
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

        JSONObject jsonObject = new JSONObject();
        Weight weight = new WeightDAO().retrieve(retrieveEmployee.getDepartment());
        float score = new SelfStrategy(selfEvaluation).calculate(weight);
        new ScoreDAO().updateSelfScore(score, id);
        jsonObject.put("score", score);
        return jsonObject;
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
        float score = 0f;
        score = retrieveEmployee.getAttendanceScore();
        jsonObject.put("attendanceScore", score);
        JSONArray jsonArray = new JSONArray();

        for(int i=0; i<retrieveEmployee.getProjects().size(); i++){
            JSONObject subJsonObject = new JSONObject();
            subJsonObject.put("name", retrieveEmployee.getProjects().get(i).getName());
            subJsonObject.put("score", retrieveEmployee.getProjects().get(i).getScore());
            score += retrieveEmployee.getProjects().get(i).getScore();
            jsonArray.add(subJsonObject);
        }

        new ScoreDAO().updateAttendanceScore(score, retrieveEmployee.getId());

        jsonObject.put("projects", jsonArray);
        jsonObject.put("totalScore", score);

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

        float score = 0f;

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
                score += retrieveEmployee.getProjects().get(i).getScore();
                subJsonArray.add(subJsonObject);
            }
            jsonObject.put("members", subJsonArray);
            jsonArray.add(jsonObject);
        }
        Weight weight = new WeightDAO().retrieve(retrieveEmployee.getDepartment());
        score = new ProjectStrategy(score).calculate(weight);
        new ScoreDAO().updateProjectScore(score, retrieveEmployee.getId());
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
        Employee employee = new EmployeeDAO().retrieve(memberEvaluation.getId());
        Weight weight = new WeightDAO().retrieve(employee.getDepartment());
        float score = new MemberStrategy(memberEvaluation).calculate(weight);
        jsonObject.put("score", score);
        return jsonObject;
    }

    //上传权重
    @PostMapping("/weight")
    @ResponseBody
    public void uploadWeight(@RequestBody Weight weight){
        WeightDAO weightDAO = new WeightDAO();
        Weight retrieveWeight = weightDAO.retrieve(weight.getDepartment());

        if( retrieveWeight == null){
            weightDAO.add(weight);
        }else{
            weightDAO.update(weight);
        }
    }

    //上传领导评价
    @PostMapping("/boss/evaluation")
    @ResponseBody
    public JSONObject uploadBossEvaluation(@RequestParam(value = "id", required = true) int id, @RequestBody BossEvaluation bossEvaluation){
        EmployeeDAO employeeDAO = new EmployeeDAO();
        Employee retrieveEmployee = employeeDAO.retrieve(id);

        //判断是否第一次上传
        if(retrieveEmployee.getBossEvaluationId() == 0){
            //第一次上传
            int bossEvaluationId = new BossEvaluationDAO().add(bossEvaluation);
            retrieveEmployee.setBossEvaluationId(bossEvaluationId);
            employeeDAO.update(retrieveEmployee);
        }else{
            //更新自我评价
            new BossEvaluationDAO().update(retrieveEmployee.getBossEvaluationId(), bossEvaluation);
        }

        JSONObject jsonObject = new JSONObject();
        Weight weight = new WeightDAO().retrieve(retrieveEmployee.getDepartment());
        float score = new BossStrategy(bossEvaluation).calculate(weight);
        new ScoreDAO().updateBossScore(score, id);
        jsonObject.put("score", score);
        return jsonObject;
    }

    //获取所有员工信息
    @GetMapping("/information/employees")
    @ResponseBody
    public JSONArray getEmployeeInformation(){
        ArrayList<Employee> employees = new EmployeeDAO().list();
        JSONArray jsonArray = new JSONArray();

        BossEvaluationDAO bossEvaluationDAO = new BossEvaluationDAO();
        for(int i=0; i<employees.size(); i++){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", employees.get(i).getId());
            jsonObject.put("name", employees.get(i).getUsername());
            jsonObject.put("department", employees.get(i).getDepartment());
            jsonObject.put("bossScore", new BossStrategy(bossEvaluationDAO.retrieve(employees.get(i).getBossEvaluationId())).calculate(new WeightDAO().retrieve(employees.get(i).getDepartment())));
            jsonObject.put("finalScore", employees.get(i).getTotalScore());
            jsonArray.add(jsonObject);
        }

        return jsonArray;
    }

    //获取所有项目信息
    @GetMapping("/information/projects")
    @ResponseBody
    public JSONArray getProjectsInformation(){
        ArrayList<Project> projects = new ProjectDAO().list();
        JSONArray jsonArray = new JSONArray();

        EmployeeDAO employeeDAO = new EmployeeDAO();
        for(int i=0; i<projects.size(); i++){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", projects.get(i).getProjectId());
            jsonObject.put("name", projects.get(i).getName());
            jsonObject.put("leader", employeeDAO.retrieve(projects.get(i).getProjectId()).getUsername());
            jsonObject.put("department", projects.get(i).getDepartment());
            jsonObject.put("beginDate", projects.get(i).getTime());
            jsonArray.add(jsonObject);
        }

        return jsonArray;
    }

    //获得权重
    @GetMapping("/weight/get")
    @ResponseBody
    public JSONObject getWeight(@RequestParam(value = "department", required = true) String department){
        JSONObject jsonObject = new JSONObject();

        Weight weight = new WeightDAO().retrieve(department);

        jsonObject.put("attendanceWeight", weight.getAttendanceWeight());
        jsonObject.put("projectWeight", weight.getProjectWeight());
        jsonObject.put("selfEvaluationWeight", weight.getSelfEvaluationWeight() );
        jsonObject.put("bossEvaluationWeight", weight.getBossEvaluationWeight());

        return jsonObject;
    }

    //获得总分
    @GetMapping("/totalScore")
    @ResponseBody
    public JSONObject getTotalScore(@RequestParam(value = "id", required = true) int id){
        JSONObject jsonObject = new JSONObject();

        float score = new ScoreDAO().getTotal(id);
        score = (score*100)/100;
        jsonObject.put("finalScore", score);

        return jsonObject;
    }


}
