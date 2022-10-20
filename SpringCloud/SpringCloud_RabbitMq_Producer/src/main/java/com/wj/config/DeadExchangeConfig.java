package com.wj.config;

import com.wj.mqEnum.ExchangeEnum;
import com.wj.mqEnum.QueueEnum;
import com.wj.mqEnum.RouteKeyEnum;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Package: com.wj.config
 * @ClassName: DeadExchangeConfig
 * @Auther: Winjay
 * @Date: 2022/10/9 17:08
 * @ProjectName: SpringCloud
 * @Des:死性队列交换机
 */
@Configuration
public class DeadExchangeConfig {

    /**
     * @description:创建死性交换机
     *  durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
     *  exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
     *  autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
     * @author: Winjay
     * @date: 2022/10/9 17:13
     * @return: org.springframework.amqp.core.DirectExchange
     **/
    @Bean
    public DirectExchange deadDirectExchange(){
        return new DirectExchange(ExchangeEnum.DEAD_EXCHANGE.getExchange(),true,false);
    }

    /**
     * 创建一个直连死性队列
     */
    @Bean
    public Queue directDeadQueue(){
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        //   return new Queue("TestDirectQueue",true,true,false);
        //一般设置一下队列的持久化就好,其余两个就是默认false
        return new Queue(QueueEnum.DEAD_DIRECT_QUEUE.getQueue(),true);
    }

    /**
     * 死性队列绑定  将队列和交换机绑定, 并设置用于匹配键：DEAD_DIRECT_ROUTING_KEY
     */
    @Bean
    Binding directDeadBind(){
        return BindingBuilder.bind(directDeadQueue()).to(deadDirectExchange()).with(RouteKeyEnum.DEAD_DIRECT_ROUTING_KEY.getRouteKey());
    }
}
