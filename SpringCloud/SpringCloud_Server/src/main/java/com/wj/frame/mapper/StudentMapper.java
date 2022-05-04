package com.wj.frame.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wj.frame.entity.student.Student;
import org.apache.ibatis.annotations.Mapper;

/**
 * @PackageName: com.wj.frame.mapper
 * @InterfaceName: StudentMapper
 * @Description: StudentMapper接口服务类
 * @Author: Winjay
 * @Date: 2022-04-30 19:58:11
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {


}

