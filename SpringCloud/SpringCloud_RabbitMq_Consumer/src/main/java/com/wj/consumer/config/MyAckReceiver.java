package com.wj.consumer.config;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.wj.consumer.vo.MessageVo;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Map;

/**
 * @Package: com.wj.consumer.config
 * @ClassName: MyAckReceiver
 * @Auther: Winjay
 * @Date: 2022/10/9 16:39
 * @ProjectName: SpringCloud
 * @Des:
 */
@Component
public class MyAckReceiver implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            byte[] body = message.getBody();
            String s = new String(body);
            // ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(body));
            MessageVo messageVo = JSONObject.parseObject(s,MessageVo.class);
            String messageId = messageVo.getBody();
            String messageData = messageVo.getValue();
           // ois.close();
            System.out.println("  MyAckReceiver  messageId:"+messageId+"  messageData:"+messageData);
            System.out.println("消费的主题消息来自："+message.getMessageProperties().getConsumerQueue());
            channel.basicAck(deliveryTag, true); //第二个参数，手动确认可以被批处理，当该参数为 true 时，则可以一次性确认 delivery_tag 小于等于传入值的所有消息
//			channel.basicReject(deliveryTag, true);//第二个参数，true会重新放回队列，所以需要自己根据业务逻辑判断什么时候使用拒绝
        } catch (Exception e) {
            channel.basicReject(deliveryTag, false);
            e.printStackTrace();
        }
    }

}