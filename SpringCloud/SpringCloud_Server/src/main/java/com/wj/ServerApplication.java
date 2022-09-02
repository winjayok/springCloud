package com.wj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @PackageName: com.wj.frame
 * @ClassName: ServerApplication
 * @Description: ServerApplication
 * @Author: Winjay
 * @Date: 2022-04-30 19:59:45
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients     //开启openfeign远程调用
/*默认扫描当前启动类的下的所有包路径，但需要扫描第三方模块的包，因此需要自己定义扫描路径，否则注解不生效*/
@ComponentScan(basePackages = {"com.wj.frame","com.wj.module.mybatis","com.wj.module.redis"})
@MapperScan(basePackages = {"com.wj.**.mapper"})
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}
