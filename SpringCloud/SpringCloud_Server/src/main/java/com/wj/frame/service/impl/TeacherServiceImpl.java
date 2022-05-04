package com.wj.frame.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wj.frame.entity.student.Student;
import com.wj.frame.entity.teacher.Teacher;
import com.wj.frame.mapper.TeacherMapper;
import com.wj.frame.service.StudentService;
import com.wj.frame.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
@DS("slave")
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService{

    @Autowired
    private StudentService studentService;

    @Override
    public boolean setObj(int i) {
        Teacher teacher = new Teacher();
        teacher.setId(i);
        teacher.setName("李飒飒");
        baseMapper.updateById(teacher);
        this.setobj2();
        Student student = new Student();
        student.setId(1);
        student.setName("张三思si");
        studentService.setObj1(student);
        return true;
    }

    private boolean setobj2(){
        Teacher teacher = new Teacher();
        teacher.setId(1);
        teacher.setName("李飒飒das");
        baseMapper.updateById(teacher);
        return true;
    }
}




