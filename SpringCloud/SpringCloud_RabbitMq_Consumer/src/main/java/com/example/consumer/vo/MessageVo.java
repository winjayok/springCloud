package com.example.consumer.vo;

import com.rabbitmq.client.MessageProperties;

/**
 * @PackageName: com.example.consumer.vo
 * @ClassName: MessageVo
 * @Description: MessageVo
 * @Author: Winjay
 * @Date: 2022-01-09 15:31:43
 */
public class MessageVo {

    private Object Body;

    private MessageProperties messageProperties;

    public void setBody(Object body) {
        Body = body;
    }

    public Object getBody() {
        return Body;
    }

    public void setMessageProperties(MessageProperties messageProperties) {
        this.messageProperties = messageProperties;
    }

    @Override
    public String toString() {
        return "MessageVo{" +
                "Body=" + Body +
                ", messageProperties=" + messageProperties +
                '}';
    }
}
