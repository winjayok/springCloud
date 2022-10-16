package com.wj.baseUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @PackageName: com.wj.baseUtils
 * @ClassName: Result
 * @Description: Result
 * @Author: Winjay
 * @Date: 2022-10-16 19:43:26
 */
public class Result extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public Result() {
        put("code", "0");
    }

    /**
     * 自定义反馈结果
     * @param code
     * @param msg
     */
    public Result(String code, String msg) {
        put("code", code);
        put("msg", msg);
    }

    /**
     * 反馈错误结果
     * @return
     */
    public static Result error() {
        return new Result(Constant.RESULT.CODE_NO.getValue(), Constant.RESULT.MSG_NO.getValue());
    }

    /**
     * 反馈错误结果消息
     * @param msg
     * @return
     */
    public static Result error(String msg) {
        return error(Constant.RESULT.CODE_NO.getValue(), msg);
    }

    /**
     * 自定义反馈错误结果
     * @param code
     * @param msg
     * @return
     */
    public static Result error(String code, String msg) {
        Result r = new Result();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    /**
     * 反馈成功消息
     * @param msg
     * @return
     */
    public static Result ok(String msg) {
        Result r = new Result();
        r.put("msg", msg);
        return r;
    }

    /**
     * 自定义反馈成功消息
     * @param map
     * @return
     */
    public static Result ok(Map<String, Object> map) {
        Result r = new Result();
        r.putAll(map);
        return r;
    }

    /**
     * 反馈成功消息
     * @return
     */
    public static Result ok() {
        return new Result(Constant.RESULT.CODE_YES.getValue(), Constant.RESULT.MSG_YES.getValue());
    }

    @Override
    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    static class Constant{
        /**
         * 返回状态值
         */
        public enum RESULT {
            /**
             * 成功
             */
            CODE_YES("0"),
            /**
             * 失败
             */
            CODE_NO("-1"),
            /**
             * 成功msg
             */
            MSG_YES("操作成功"),
            /**
             * 失败msg
             */
            MSG_NO("操作失败");
            private String value;

            private RESULT(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }
        }
    }
}