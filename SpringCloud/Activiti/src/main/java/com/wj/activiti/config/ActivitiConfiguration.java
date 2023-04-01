package com.wj.activiti.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.spring.SpringAsyncExecutor;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @PackageName: com.wj.config
 * @ClassName: ActivitiConfiguration
 * @Description: activiti配置类
 * @Author: Winjay
 * @Date: 2022-10-15 20:26:01
 */
@Configuration
public class ActivitiConfiguration extends AbstractProcessEngineAutoConfiguration implements ProcessEngineConfigurationConfigurer {

    private final String DB_DRIVER_DM = "dm.jdbc.driver.DmDriver";

    /**
     * @description:船舰druid数据源
     * @author: Winjay
     * @date: 2022/10/18 9:10
     * @return: javax.sql.DataSource
     **/
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.dynamic.datasource.master")
    //@ConfigurationProperties(prefix = "spring.datasource.druid")
    public DataSource masterDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * @description:配置数据源及事务以及数据库类型
     * @author: Winjay
     * @date: 2022/10/18 9:10
     * @param transactionManager
     * @param springAsyncExecutor
     * @return: org.activiti.spring.SpringProcessEngineConfiguration
     **/
    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration(PlatformTransactionManager transactionManager, SpringAsyncExecutor springAsyncExecutor) throws IOException {
        SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
        DruidDataSource dataSource = (DruidDataSource) masterDataSource();
        if(DB_DRIVER_DM.equals(dataSource.getDriverClassName())){ //判断是否为达梦数据库驱动
            configuration.setDatabaseType("dm"); //必须配置达梦类型，他会加载对应驱动并检查和创建表
            configuration.setDatabaseSchema(dataSource.getUsername()); //达梦，oracle需要设置数据库模式\
            //否使用activiti历史表
//        configuration.setDbHistoryUsed(true);
//        // history-level属性
//        configuration.setHistoryLevel(HistoryLevel.FULL);
//        //是否使用activiti身份信息表
//        configuration.setDbIdentityUsed(false);
//        //mysql8.x以上连接后要加&nullCatalogMeansCurrent=true
//        //create-drop,drop-create,create,false,true
//        configuration.setDatabaseSchemaUpdate("create-drop");
        }
        // 外部配置文件注入
        configuration = baseSpringProcessEngineConfiguration(dataSource,transactionManager,springAsyncExecutor);
        return configuration;
    }

    @Override
    public void configure(SpringProcessEngineConfiguration configuration) {
        configuration.setActivityFontName("宋体");
        configuration.setLabelFontName("宋体");
        configuration.setAnnotationFontName("宋体");
    }
}

