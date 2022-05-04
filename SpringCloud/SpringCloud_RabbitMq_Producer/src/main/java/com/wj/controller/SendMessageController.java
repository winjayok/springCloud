package com.wj.controller;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @PackageName: com.example.controller
 * @ClassName: SendMessageController
 * @Description: SendMessageController
 * @Author: Winjay
 * @Date: 2022-01-03 16:14:39
 */

@RestController
public class SendMessageController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    private static final String DIRECT_ROUTING_KEY = "direct_routing_key";

    private static final String DIRECT_EXCHANGE = "direct_exchange";

    @RequestMapping("/sendMessage")
    public String sendMessage(){
        try {
            String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            Map<String,Object> map=new HashMap<>();
            map.put("messageId","1");
            map.put("messageData","hello world");
            map.put("createTime",createTime);
            Message message = new Message("你好啊".getBytes(), new MessageProperties());
            rabbitTemplate.convertAndSend(DIRECT_EXCHANGE,DIRECT_ROUTING_KEY,"message");
        }catch (Exception e){
            return "消息发送失败："+e.getMessage();
        }
        return "消息发送成功";
    }

    @RequestMapping("/sendMessage1")
    public String sendMessag1e(){
        return "消息发送成功";
    }
}
