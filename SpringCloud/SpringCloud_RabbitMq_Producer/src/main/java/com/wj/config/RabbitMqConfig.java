package com.wj.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Package: com.wj.consumer.config
 * @ClassName: BeanConfig
 * @Auther: Winjay
 * @Date: 2022/9/5 14:57
 * @ProjectName: SpringCloud
 * @Des:
 */
@Configuration
public class RabbitMqConfig {

    /**
     * @description:发送消息json序列化
     * @author: Winjay
     * @date: 2022/9/5 14:58
     * @param
     * @return: org.springframework.amqp.support.converter.MessageConverter
     **/
    @Bean
    public MessageConverter getMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
