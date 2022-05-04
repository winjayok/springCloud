package com.wj.frame.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wj.frame.entity.teacher.Teacher;

/**
 *
 */
public interface TeacherService extends IService<Teacher> {

    boolean setObj(int i);
}
