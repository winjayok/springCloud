package com.wj.consumer.receive;

import com.wj.consumer.vo.MessageVo;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @PackageName: com.example.consumer.receive
 * @ClassName: receiveMessage
 * @Description: receiveMessage
 * @Author: Winjay
 * @Date: 2022-01-03 16:49:29
 */
@Component
public class receiveMessage {

    /**
     * 消费者单纯的使用，其实可以不用添加这个配置，直接建后面的监听就好，
     * 使用注解来让监听器监听对应的队列即可。配置上了的话，其实消费者也是生成者的身份，也能推送该消息。
     */
    @RabbitListener(queues = {"direct_queue"})
    @RabbitHandler
    public void receiveDirect(MessageVo messageVo){
        System.out.println("come in Direct");
        System.out.println(messageVo.toString());
    }

    @RabbitListener(queues = {"TOPIC_QUEUE_SMS"})
    @RabbitHandler
    public void receiveTopic1(MessageVo messageVo){
        System.out.println("come in Topic sms");
        System.out.println(messageVo.toString());
    }

    @RabbitListener(queues = {"TOPIC_QUEUE_MAIL"})
    @RabbitHandler
    public void receiveTopic2(MessageVo messageVo){
        System.out.println("come in Topic mail");
        System.out.println(messageVo.toString());
    }

    @RabbitListener(queues = {"FANOUT_QUEUE_A"})
    @RabbitHandler
    public void receiveFanoutA(MessageVo messageVo){
        System.out.println("come in FANOUT_QUEUE_A");
        System.out.println(messageVo.toString());
    }

    @RabbitListener(queues = {"FANOUT_QUEUE_B"})
    @RabbitHandler
    public void receiveFanoutB(MessageVo messageVo){
        System.out.println("come in FANOUT_QUEUE_B");
        System.out.println(messageVo.toString());
    }

    @RabbitListener(queues = {"FANOUT_QUEUE_C"})
    @RabbitHandler
    public void receiveFanoutC(MessageVo messageVo){
        System.out.println("come in FANOUT_QUEUE_C");
        System.out.println(messageVo.toString());
    }
}
