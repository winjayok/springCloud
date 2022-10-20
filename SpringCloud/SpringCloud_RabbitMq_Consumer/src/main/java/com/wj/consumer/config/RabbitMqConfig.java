package com.wj.consumer.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
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

    /**
     * @description:设置消息确认机制（手动）
     * @author: Winjay
     * @date: 2022/10/9 14:37
     * @param connectionFactory
     * @return: org.springframework.amqp.rabbit.core.RabbitTemplate
     **/
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);

        //重新设置发送消息json序列化
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());

        //ConfirmCallback为发送Exchange（交换器）时回调，成功或者失败都会触发；
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("======================================================");
                System.out.println("ConfirmCallback:     "+"相关数据："+correlationData);
                System.out.println("ConfirmCallback:     "+"确认情况："+ack); //true成功，false失败
                System.out.println("ConfirmCallback:     "+"原因："+cause);
                System.out.println("======================================================");
            }
        });

        //ReturnCallback为路由不到队列时触发，成功则不触发
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("======================================================");
                System.out.println("ReturnCallback:     "+"消息："+message);
                System.out.println("ReturnCallback:     "+"回应码："+replyCode);
                System.out.println("ReturnCallback:     "+"回应信息："+replyText);
                System.out.println("ReturnCallback:     "+"交换机："+exchange);
                System.out.println("ReturnCallback:     "+"路由键："+routingKey);
                System.out.println("======================================================");
            }
        });
        return rabbitTemplate;
    }
}
