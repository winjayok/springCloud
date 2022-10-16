package com.wj.activiti.config;

import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.context.annotation.Configuration;

/**
 * @PackageName: com.wj.config
 * @ClassName: ActivitiConfiguration
 * @Description: 配置activiti字体，防止中文乱码
 * @Author: Winjay
 * @Date: 2022-10-15 20:26:01
 */
@Configuration
public class ActivitiConfiguration implements ProcessEngineConfigurationConfigurer {


    @Override
    public void configure(SpringProcessEngineConfiguration processEngineConfiguration) {
        processEngineConfiguration.setActivityFontName("宋体");
        processEngineConfiguration.setLabelFontName("宋体");
        processEngineConfiguration.setAnnotationFontName("宋体");
    }
}

