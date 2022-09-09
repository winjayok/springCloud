package com.wj.config;

import com.wj.mqEnum.ExchangeEnum;
import com.wj.mqEnum.QueueEnum;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Package: com.wj.config
 * @ClassName: FanoutExchangeConfig
 * @Auther: Winjay
 * @Date: 2022/9/5 17:43
 * @ProjectName: SpringCloud
 * @Des:扇形(广播)交换机
 */
@Configuration
public class FanoutExchangeConfig {

    /**
     * @description:扇形(广播)交换机
     * 创建三个队列 ：FANOUT_QUEUE_A  FANOUT_QUEUE_B  FANOUT_QUEUE_C
     * 将三个队列都绑定在交换机 fanoutExchange 上
     * 因为是扇型交换机, 路由键无需配置,配置也不起作用
     * @author: Winjay
     * @date: 2022/9/5 15:42
     * @return: org.springframework.amqp.core.TopicExchange
     **/
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(ExchangeEnum.FANOUT_EXCHANGE.getExchange(),true,false);
    }

    @Bean
    public Queue queueA() {
        return new Queue(QueueEnum.FANOUT_QUEUE_A.getQueue());
    }

    @Bean
    public Queue queueB() {
        return new Queue(QueueEnum.FANOUT_QUEUE_B.getQueue());
    }

    @Bean
    public Queue queueC() {
        return new Queue(QueueEnum.FANOUT_QUEUE_C.getQueue());
    }

    @Bean
    Binding bindingExchangeA() {
        return BindingBuilder.bind(queueA()).to(fanoutExchange());
    }

    @Bean
    Binding bindingExchangeB() {
        return BindingBuilder.bind(queueB()).to(fanoutExchange());
    }

    @Bean
    Binding bindingExchangeC() {
        return BindingBuilder.bind(queueC()).to(fanoutExchange());
    }

}
