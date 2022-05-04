package com.wj.frame.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

@Component
public class MyFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
//        //获取当前请求的上下文
//        RequestContext context = RequestContext.getCurrentContext();
//        //获取当前请求
//        HttpServletRequest request = context.getRequest();
//        System.out.println("该请求通过的地址为："+request.getRequestURI()+
//                        "\n该请求通过的方法为："+request.getMethod());
//        //获取headers的accesstoken的值
//        String accesstoken = request.getHeader("accesstoken");
//        //把token写入headers中
//        //简单校验headers中有无accesstoken
//        if(StringUtils.isEmpty(accesstoken)){
//            System.out.println("当前headers没有token");
//            context.setSendZuulResponse(false);
//            context.setResponseStatusCode(401);
//            return null;
//        }
        return null;
    }
}
