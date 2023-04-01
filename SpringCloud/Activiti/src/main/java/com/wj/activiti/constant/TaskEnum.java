package com.wj.activiti.constant;

/**
 * @Package: com.wj.activiti.constant
 * @ClassName: TaskEnum
 * @Auther: Winjay
 * @Date: 2022/10/22 0:20
 * @ProjectName: SpringCloud
 * @Des:任务枚举类
 */
public enum TaskEnum {

    APPLICANT("申请人","applicant"),

    PASS("通过","1"),

    REJECT("驳回","0");

    private String name;
    private String value;

    TaskEnum(String name,String value){
        this.name = name;
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
