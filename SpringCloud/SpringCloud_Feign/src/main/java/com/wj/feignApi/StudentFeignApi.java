package com.wj.feignApi;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @PackageName: com.wj.feign.feignApi
 * @InterfaceName: StudentFeignApi
 * @Description: StudentFeignApi接口服务类
 * @Author: Winjay
 * @Date: 2022-05-01 19:08:47
 */
@FeignClient(name = "service-server")
public interface StudentFeignApi {

    @RequestMapping("/student/getUserId")
    String getUserId();

    @RequestMapping("/student/setObj1")
    boolean setObj1();
}
