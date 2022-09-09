package com.wj.config;

import com.wj.mqEnum.ExchangeEnum;
import com.wj.mqEnum.QueueEnum;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Package: com.wj.config
 * @ClassName: TopicExchangeConfig
 * @Auther: Winjay
 * @Date: 2022/9/5 15:36
 * @ProjectName: SpringCloud
 * @Des:主题交换机
 */
@Configuration
public class TopicExchangeConfig {

    //绑定键
    public final static String TOPIC_SMS = "topic.sms";
    public final static String TOPIC_ALL = "topic.#";

    /**
     * @description:创建主题交换机
     * @author: Winjay 
     * @date: 2022/9/5 15:42 
     * @return: org.springframework.amqp.core.TopicExchange
     **/
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(ExchangeEnum.TOPIC_EXCHANGE.getExchange(),true,false);
    }

    /**
     * @description:创建sms队列
     * @author: Winjay 
     * @date: 2022/9/5 15:40 
     * @return: org.springframework.amqp.core.Queue
     **/
    @Bean
    public Queue firstQueue() {
        return new Queue(QueueEnum.TOPIC_QUEUE_SMS.getQueue(),true);
    }

    /**
     * @description:创建mail队列
     * @author: Winjay 
     * @date: 2022/9/5 15:53 
     * @return: org.springframework.amqp.core.Queue
     **/
    @Bean
    public Queue secondQueue() {
        return new Queue(QueueEnum.TOPIC_QUEUE_MAIL.getQueue(),true);
    }

    @Bean
    public Binding bindingExchangeFirstQueue(){
        return BindingBuilder.bind(firstQueue()).to(topicExchange()).with(TOPIC_SMS);
    }

    //将secondQueue和topicExchange绑定,而且绑定的键值为用上通配路由键规则topic.#
    // 这样只要是消息携带的路由键是以topic.开头,都会分发到该队列
    //* - 表示任意的一个单词
    //# - 表示0个或多个单词
    @Bean
    public Binding bindingExchangeSecondQueue(){
        return BindingBuilder.bind(secondQueue()).to(topicExchange()).with(TOPIC_ALL);
    }
   
}
