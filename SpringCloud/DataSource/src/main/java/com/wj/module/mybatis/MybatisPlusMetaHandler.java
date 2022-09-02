package com.wj.module.mybatis;

/**
 * @PackageName: com.wj.module.mybatis
 * @ClassName: MybatisPlusMetaHandler
 * @Description: MybatisPlusMetaHandler
 * @Author: Winjay
 * @Date: 2022-05-08 15:17:39
 */

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * MybatisPlus对默认字段赋值
 * @autho winjay
 * @Date 2021-07-29 14:39:34
 */
@Configuration
public class MybatisPlusMetaHandler implements MetaObjectHandler {

    /**
     * 插入操作时自动对注解的字段赋值
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        // 优化: 有没有这个属性,有的话才自动填充
        boolean createTime = metaObject.hasSetter("createTime");
        boolean updateTime = metaObject.hasSetter("updateTime");
        boolean delFlag = metaObject.hasSetter("delFlag");
        boolean createDate = metaObject.hasSetter("createDate");
        boolean updateDate = metaObject.hasSetter("updateDate");

        // 优化: 根据属性setter参数的类型赋值
        if(createTime){
            String createTimeType = metaObject.getSetterType("createTime").getSimpleName();
            insertValueByType(createTimeType,"createTime",date,metaObject);
        }
        if(updateTime){
            String updateTimeType = metaObject.getSetterType("updateTime").getSimpleName();
            insertValueByType(updateTimeType,"updateTime",date,metaObject);
        }
        if(createDate){
            String createDateType = metaObject.getSetterType("createDate").getSimpleName();
            insertValueByType(createDateType,"createDate",date,metaObject);
        }
        if(updateDate){
            String updateDateType = metaObject.getSetterType("updateDate").getSimpleName();
            insertValueByType(updateDateType,"updateDate",date,metaObject);
        }
        if(delFlag){
            String delFlagType = metaObject.getSetterType("delFlag").getSimpleName();
            insertValueByType(delFlagType,"delFlag","0",metaObject);
        }

//        if(null != currentUser){ //登录页面保存没有用户信息
//            this.strictInsertFill(metaObject,"createId",String.class,currentUser.getId());
//            this.strictInsertFill(metaObject,"updateId",String.class,currentUser.getId());
//            this.strictInsertFill(metaObject,"createUser",String.class,currentUser.getUserName());
//            this.strictInsertFill(metaObject,"updateUser",String.class,currentUser.getUserName());
//            this.strictInsertFill(metaObject,"createBy",String.class,currentUser.getUserName());
//            this.strictInsertFill(metaObject,"updateBy",String.class,currentUser.getUserName());
//        }
    }

    /**
     * 修改操作时自动对注解的字段赋值
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

//        UserEntity currentUser = null;
//        try {
//            currentUser  = UserUtils.getCurrentUser();
//        }catch (Exception e){
//            currentUser = null;
//        }
        boolean updateDate = metaObject.hasSetter("updateDate");
        boolean updateTime = metaObject.hasSetter("updateTime");

        // 优化:
        if (updateTime) {
            String updateTimeType = metaObject.getSetterType("updateTime").getSimpleName();
            updateValueByType(updateTimeType,"updateTime",date,metaObject);
        }
        if(updateDate){
            String updateDateType = metaObject.getSetterType("updateDate").getSimpleName();
            updateValueByType(updateDateType,"updateDate",date,metaObject);
        }
//        if(null != currentUser) { //登录页面保存没有用户信息
//            this.strictUpdateFill(metaObject,"updateId",String.class,currentUser.getId());
//            this.strictUpdateFill(metaObject,"updateUser",String.class,currentUser.getUserName());
//            this.strictUpdateFill(metaObject,"updateBy",String.class,currentUser.getUserName());
//        }
    }

    /**
     * 根据类型自动填充属性(新增)
     * @param type
     * @param name
     * @param val
     * @param metaObject
     */
    private void insertValueByType(String type,String name,String val,MetaObject metaObject){
        switch (type){
            case "String":
                this.strictInsertFill(metaObject,name,String.class,val);
                break;
            case "Integer":
                this.strictInsertFill(metaObject,name,Integer.class,0);
                break;
            case "Date":
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date parse = new Date();
                try {
                    parse = df.parse(df.format(parse));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                this.strictInsertFill(metaObject,name,Date.class,parse);
                break;
        }
    }

    /**
     * 根据类型自动填充属性（更新）
     * @param type
     * @param name
     * @param val
     * @param metaObject
     */
    private void updateValueByType(String type,String name,String val,MetaObject metaObject){
        switch (type){
            case "String":
                this.strictUpdateFill(metaObject,name,String.class,val);
                break;
            case "Integer":
                this.strictUpdateFill(metaObject,name,Integer.class,0);
                break;
            case "Date":
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date parse = new Date();
                try {
                    parse = df.parse(df.format(parse));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                this.strictUpdateFill(metaObject,name,Date.class,parse);
                break;
        }
    }

    /**
     * 检验日期长度是否为19 并且格式为yyyy-MM-dd HH:mm:ss
     * @param time
     * @return
     */
    public boolean validTime(String time) {
        boolean flag = true;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            df.parse(time);
            if(time.length()!=19){
                flag = false;
            }
        }catch (Exception e){
            flag = false;
        }

        return flag;
    }

    /**
     * 获取对象属性的类型
     * @param o
     * @param name
     * @return
     */
    private String getAttributeType(Object o,String name) {
        //获取类对象
        Class clazz = o.getClass();
        //获取对象属性的类型
        Field field = null;
        String type = null;
        try {
            field = clazz.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        if(null != field){
            field.setAccessible(true);
            type = field.getType().getSimpleName();
        }
        return type;
    }
}
