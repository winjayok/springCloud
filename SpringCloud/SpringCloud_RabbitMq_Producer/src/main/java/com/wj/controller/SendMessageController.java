package com.wj.controller;

import com.wj.mqEnum.ExchangeEnum;
import com.wj.mqEnum.RouteKeyEnum;
import com.wj.vo.MessageVo;
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
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/sendMessage")
    public String sendMessage(){
        try {
            String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            MessageVo messageVo = new MessageVo();
            messageVo.setBody("这是一个死性消息队列");
            messageVo.setValue(createTime);
            rabbitTemplate.convertAndSend(ExchangeEnum.DIRECT_EXCHANGE.getExchange(), RouteKeyEnum.DIRECT_ROUTING_KEY.getRouteKey(),messageVo);
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
