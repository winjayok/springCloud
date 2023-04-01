package com.wj.frame.server.controller;

import com.wj.feignApi.StudentFeignApi;
import com.wj.frame.entity.student.Student;
import com.wj.frame.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PackageName: com.wj.frame.server.controller
 * @ClassName: UserContoller
 * @Description: UserContoller
 * @Author: Winjay
 * @Date: 2022-04-30 19:59:45
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentFeignApi studentFeignApi;
    @RequestMapping("/getUserId")
    public String GetId() throws InterruptedException {
        Student student = studentService.getById(1);
        return student.toString();
    }

    @RequestMapping("/test")
    public String test() throws InterruptedException {
        return studentFeignApi.getUserId();
    }


    @RequestMapping("/setObj")
    public String setObj(){
        Student student = new Student();
        student.setId(1);
        student.setClassNo("66");
        student.setName("张思");
        studentService.setObj(student);
        return "ok";
    }

    @RequestMapping("/setObj1")
    public String setObj1(){
        Student student = new Student();
        student.setId(1);
        student.setClassNo("66");
        student.setName("张dsa思");
        studentService.setObj1(student);
        return "ok";
    }

    @RequestMapping(value = "/getParam",method = RequestMethod.POST)
    public String getParam(String username,String password,String captcha, boolean isRememberMe){
        System.out.println(username);
        System.out.println(password);
        return username + password;
    }

    @PostMapping("/getParam1")
    public String getParam1(String username,String password,String captcha, boolean isRememberMe){
        return password;
    }
}
