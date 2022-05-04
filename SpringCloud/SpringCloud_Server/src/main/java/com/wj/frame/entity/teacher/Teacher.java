package com.wj.frame.entity.teacher;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * @PackageName: com.wj.frame.entity.teacher
 * @ClassName: teacher
 * @Description: teacher
 * @Author: Winjay
 * @Date: 2022-04-30 19:55:44
 */
@TableName("teacher")
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;

    private String name;

    private String classNo;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassNo() {
        return classNo;
    }

    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }


    @Override
    public String toString() {
        return "DemoEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", classNo='" + classNo + '\'' +
                '}';
    }
}
