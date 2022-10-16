package com.wj.mqEnum;

/**
 * @Package: com.wj.mqEnum
 * @ClassName: RouteEnum
 * @Auther: Winjay
 * @Date: 2022/9/5 13:54
 * @ProjectName: SpringCloud
 * @Des:
 */
public enum RouteKeyEnum {

    DIRECT_ROUTING_KEY("DIRECT_ROUTING_KEY");

    private final String routeKey;


    RouteKeyEnum(String routeKey){
        this.routeKey = routeKey;
    }

    public String getRouteKey() {
        return routeKey;
    }
}
