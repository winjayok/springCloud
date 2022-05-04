package com.wj.frame.web.controller;

import com.wj.feignApi.StudentFeignApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PackageName: com.wj.frame.web.controller
 * @ClassName: TestApi
 * @Description: TestApi
 * @Author: Winjay
 * @Date: 2022-05-01 19:12:43
 */
@RestController
public class TestApi {

    @Autowired
    private StudentFeignApi studentFeignApi;

    @RequestMapping("/test/getUserId")
    public String GetId(){
        return studentFeignApi.getUserId();
    }

}
