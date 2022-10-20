package com.wj.consumer.receive;

import com.rabbitmq.client.Channel;
import com.wj.consumer.mqEnum.RouteKeyEnum;
import com.wj.consumer.vo.MessageVo;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

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
    @RabbitListener(queues = {"DIRECT_QUEUE"})
    @RabbitHandler
    public void receiveDirect(MessageVo messageVo, Channel channel, Message message) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            //System.out.println("消息队列消费失败，即将进入死性队列中~");
           // channel.basicReject(deliveryTag,false);
            System.out.println("come in Direct");
            System.out.println(messageVo.toString());
            System.out.println("消息队列消费成功");
            channel.basicAck(deliveryTag,false);
        }catch (Exception e){
            System.err.println("消息重回队列");
            channel.basicReject(deliveryTag,false);
        }
    }

    @RabbitListener(queues = {"TOPIC_QUEUE_SMS"})
    @RabbitHandler
    public void receiveTopic1(MessageVo messageVo, Channel channel, Message message) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            System.out.println("come in Topic sms");
            System.out.println(messageVo.toString());
            channel.basicAck(deliveryTag,false);
        }catch (Exception e){
            System.err.println("sms消息重回队列");
            channel.basicReject(deliveryTag,true);
        }
    }

    @RabbitListener(queues = {"TOPIC_QUEUE_MAIL"})
    @RabbitHandler
    public void receiveTopic2(MessageVo messageVo, Channel channel, Message message) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            System.out.println("come in Topic mail");
            System.out.println(messageVo.toString());
            channel.basicAck(deliveryTag,false);
        }catch (Exception e){
            System.err.println("mail消息重回队列");
            channel.basicReject(deliveryTag,true);
        }
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
