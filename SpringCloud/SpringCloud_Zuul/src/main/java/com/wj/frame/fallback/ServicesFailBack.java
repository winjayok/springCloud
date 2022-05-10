package com.wj.frame.fallback;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 请求失败反馈
 *
 * @Description: 网关熔断处理
 * @Package com.wj.frame.fallback
 * @author winjay
 * @Date: 2022-04-30 19:59:45
 */
@Component
public class ServicesFailBack implements FallbackProvider {

    private Logger log = LoggerFactory.getLogger(ServicesFailBack.class);

    /**
     * 推荐 - 为指定的服务定义特性化的fallback逻辑
     * 推荐 - 提供一个处理所有服务的fallback逻辑
     * 好处 - 某个服务发生超时，那么指定的fallback逻辑执行。如果有新服务上线，未提供fallback逻辑还有个通用的
     * *代表全部
     **/
    @Override
    public String getRoute() {
        // 可以单个服务id，也用* 或者 null 代表所有服务都过滤
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                //请求网关成功了
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.OK.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return HttpStatus.OK.getReasonPhrase();
            }

            @Override
            public void close() {
               log.info("close");
            }

            @Override
            public InputStream getBody() throws IOException {
                RequestContext ctx = RequestContext.getCurrentContext();
                Throwable throwable = ctx.getThrowable();
                if (throwable != null) {
                    log.error(throwable.getMessage(), throwable.getCause());
                }

                JSONObject jo = new JSONObject();
                jo.put("code", 500);
                jo.put("message",  "由于其他服务访问量过大，当前服务已降级处理！");
                return new ByteArrayInputStream(JSON.toJSONBytes(jo));
            }

            @Override
            public HttpHeaders getHeaders() {
                //设置http头
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
                return httpHeaders;
            }
        };
    }

}
