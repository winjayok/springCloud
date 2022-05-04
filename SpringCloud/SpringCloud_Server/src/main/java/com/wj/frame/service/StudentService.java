package com.wj.frame.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wj.frame.entity.student.Student;

/**
 * @PackageName: com.wj.frame.service
 * @InterfaceName: StudentService
 * @Description: StudentService接口服务类
 * @Author: Winjay
 * @Date: 2022-04-30 19:56:58
 */
public interface StudentService extends IService<Student> {

    boolean setObj(Student student);

    boolean setObj1(Student student);
}
