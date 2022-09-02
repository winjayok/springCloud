package com.wj.consumer.receive;

import com.wj.consumer.vo.MessageVo;
import com.google.gson.Gson;
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
@RabbitListener(queues = {"direct_queue"})
public class receiveMessage {

    /**
     * 消费者单纯的使用，其实可以不用添加这个配置，直接建后面的监听就好，
     * 使用注解来让监听器监听对应的队列即可。配置上了的话，其实消费者也是生成者的身份，也能推送该消息。
     */
    @RabbitHandler
    public void receiveMessage(Map<String,Object> map){
        for (String k:map.keySet()) {
            System.out.println(map.get(k));
        }
    }
}
