package com.wj.frame.server.controller;

import com.wj.frame.entity.student.Student;
import com.wj.frame.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @Autowired
    private StudentService studentService;

    @RequestMapping("/getUserId")
    public String GetId(){
        Student student = studentService.getById(1);
        return student.toString();
    }

    @RequestMapping("/setObj")
    public String setObj(){
        Student student = new Student();
        student.setId(1);
        student.setName("张三思");
        studentService.setObj(student);
        return "ok";
    }
}
