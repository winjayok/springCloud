package com.wj.vo;

import java.io.Serializable;

/**
 * @PackageName: com.example.consumer.vo
 * @ClassName: MessageBody
 * @Description: MessageBody
 * @Author: Winjay
 * @Date: 2022-01-09 15:31:43
 */
public class MessageBody implements Serializable {

    private static final long serialVersionUID = 1L;

    private String messageId;

    private String messageData;

    private String createTime;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageData() {
        return messageData;
    }

    public void setMessageData(String messageData) {
        this.messageData = messageData;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
