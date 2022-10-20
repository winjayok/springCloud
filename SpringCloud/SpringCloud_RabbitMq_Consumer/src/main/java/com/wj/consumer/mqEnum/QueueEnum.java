package com.wj.consumer.mqEnum;

/**
 * @Package: com.wj.mqEnum
 * @ClassName: QueueEnum
 * @Auther: Winjay
 * @Date: 2022/9/5 13:35
 * @ProjectName: SpringCloud
 * @Des:
 */
public enum QueueEnum {

    DIRECT_QUEUE("DIRECT_QUEUE"),
    DEAD_DIRECT_QUEUE("DEAD_DIRECT_QUEUE"),
    TOPIC_QUEUE_SMS("TOPIC_QUEUE_SMS"),
    TOPIC_QUEUE_MAIL("TOPIC_QUEUE_MAIL"),
    FANOUT_QUEUE_A("FANOUT_QUEUE_A"),
    FANOUT_QUEUE_B("FANOUT_QUEUE_B"),
    FANOUT_QUEUE_C("FANOUT_QUEUE_C");
    
    private final String queue;

    QueueEnum (String queue){
        this.queue = queue;
    }

    public String getQueue() {
        return queue;
    }
}
