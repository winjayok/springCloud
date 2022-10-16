package com.wj.activiti.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.activiti.spring.SpringAsyncExecutor;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

/**
 *
 * @PackageName: com.wj.config
 * @ClassName: ActivitiConfiguration
 * @Description: 配置activiti数据源及事务
 * @Author: Winjay
 * @Date: 2022-10-15 20:26:01
 */
@Configuration
public class ActivitiDatasourceConfig extends AbstractProcessEngineAutoConfiguration {

    /**
     *
     * @return
     */
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.dynamic.datasource.master")
    public DataSource masterDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration(PlatformTransactionManager transactionManager, SpringAsyncExecutor springAsyncExecutor) throws IOException {
        return baseSpringProcessEngineConfiguration(
                masterDataSource(),
                transactionManager,
                springAsyncExecutor);
    }
}
