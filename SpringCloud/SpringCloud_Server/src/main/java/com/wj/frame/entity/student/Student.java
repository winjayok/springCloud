package com.wj.frame.entity.student;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * @PackageName: com.wj.frame.entity.student
 * @ClassName: Student
 * @Description: Student
 * @Author: Winjay
 * @Date: 2022-04-30 19:55:44
 */
@TableName("student")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;

    private String name;

    private String classNo;

    private String number;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String delFlag;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "DemoEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", classNo='" + classNo + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
