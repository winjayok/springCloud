package com.wj.consumer.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @Package: com.wj.consumer.config
 * @ClassName: BeanConfig
 * @Auther: Winjay
 * @Date: 2022/9/5 14:57
 * @ProjectName: SpringCloud
 * @Des:
 */
@Configuration
public class BeanConfig {

    @Autowired
    RabbitTemplate rabbitTemplate;



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

    /**
     * 定制rabbitTemplate
     * RabbitConfig在创建完成后调用此方法
     */
    @PostConstruct
    public void initRabbitTemplate() {
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             发送到broke时回调，确认消息是否到达
             * @param correlationData 消息唯一关联数据
             * @param ack 是否成功还是失败
             * @param cause 失败原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("correlationData==" + correlationData);
                System.out.println("ack==" + ack);
                System.out.println("cause==" + cause);
            }
        });

        /**
         * 只有消息没有到达队列才回调
         */
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            /**
             * @param message 失败消息
             * @param replyCode 失败码
             * @param replyText 回复内容
             * @param exchange 发送的交换机
             * @param routingKey 交换机key
             */
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("fail message==" + message);
                System.out.println("replyCode" + replyCode);
                System.out.println("exchange==" + exchange);
                System.out.println("routingKey==" + routingKey);
            }
        });
    }
}
