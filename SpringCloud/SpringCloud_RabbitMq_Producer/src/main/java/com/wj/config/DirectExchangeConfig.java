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

import java.util.HashMap;
import java.util.Map;


/**
 * @PackageName: com.example.config
 * @ClassName: ExchangeConfig
 * @Description: 直连型交换机
 * @Author: Winjay
 * @Date: 2022-01-03 16:03:51
 */

@Configuration
public class DirectExchangeConfig {

    /**
     * 创建一个直连交换机
     */
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(ExchangeEnum.DIRECT_EXCHANGE.getExchange(),true,false);
    }

    /**
     * 创建一个直连队列
     * 绑定死性队列
     */
    @Bean
    public Queue directQueue(){
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        //   return new Queue("TestDirectQueue",true,true,false);
        //一般设置一下队列的持久化就好,其余两个就是默认false
        Map<String, Object> args = new HashMap<>();
        // x-dead-letter-exchange：这里声明当前业务队列绑定的死信交换机
        args.put("x-dead-letter-exchange", ExchangeEnum.DEAD_EXCHANGE.getExchange());
        // x-dead-letter-routing-key：这里声明当前业务队列的死信路由 key
        args.put("x-dead-letter-routing-key", RouteKeyEnum.DEAD_DIRECT_ROUTING_KEY.getRouteKey());
        return new Queue(QueueEnum.DIRECT_QUEUE.getQueue(),true,false,false,args);
    }

    /**
     * 绑定  将队列和交换机绑定, 并设置用于匹配键：DIRECT_ROUTING_KEY
     */
    @Bean
    Binding directBind(){
        return BindingBuilder.bind(directQueue()).to(directExchange()).with(RouteKeyEnum.DIRECT_ROUTING_KEY.getRouteKey());
    }


}
