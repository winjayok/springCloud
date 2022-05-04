package com.wj.module.mybatis;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @PackageName: com.wj.module.mybatis
 * @ClassName: MybatisPlusConfig
 * @Description: MybatisPlusConfig 分页插件配置
 * @Author: Winjay
 * @Date: 2022-04-30 22:38:27
 */
@Configuration
public class MybatisPlusConfig {
    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
