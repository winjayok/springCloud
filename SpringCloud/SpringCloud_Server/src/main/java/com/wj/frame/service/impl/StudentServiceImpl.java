package com.wj.frame.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wj.frame.entity.student.Student;
import com.wj.frame.mapper.StudentMapper;
import com.wj.frame.service.StudentService;
import com.wj.frame.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @PackageName: com.wj.frame.service.impl
 * @ClassName: StudentServiceImpl
 * @Description: StudentServiceImpl
 * @Author: Winjay
 * @Date: 2022-04-30 19:57:35
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
@DS("master")
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Autowired
    private TeacherService teacherService;

    @Override
    public boolean setObj(Student student) {
        baseMapper.insert(student);
        //teacherService.setObj(1);
        return true;
    }

    @Override
    public boolean setObj1(Student student) {
        baseMapper.updateById(student);
        return false;
    }

    @Override
    public Student getOne(Integer id) {
        return this.baseMapper.selectById(id);
    }
}

