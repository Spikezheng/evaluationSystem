package com.spike.evaluationsystem.controller;

import com.spike.evaluationsystem.dao.EmployeeDAO;
import com.spike.evaluationsystem.pojo.Employee;
import com.spike.evaluationsystem.pojo.SelfEvaluation;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {
    @PostMapping("/login")
    public String login(@RequestBody Employee employee){
        Employee retrieveEmployee = new EmployeeDAO().retrieve(employee);

        if(retrieveEmployee == null){
            return "未注册, 登陆失败!";
        }
        System.out.println(retrieveEmployee.getId());
        return "登陆成功!";
    }

}
