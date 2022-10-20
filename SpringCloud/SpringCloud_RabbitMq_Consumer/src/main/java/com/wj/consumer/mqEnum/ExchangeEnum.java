package com.wj.consumer.mqEnum;

/**
 * @Package: com.wj.mqEnum
 * @ClassName: ExchangeEnum
 * @Auther: Winjay
 * @Date: 2022/9/5 11:49
 * @ProjectName: SpringCloud
 * @Des:
 */
public enum ExchangeEnum {

    DIRECT_EXCHANGE("DIRECT_EXCHANGE"),

    TOPIC_EXCHANGE("TOPIC_EXCHANGE"),

    DEAD_EXCHANGE("DEAD_EXCHANGE"),

    FANOUT_EXCHANGE("FANOUT_EXCHANGE");

    private final String exchange;

    ExchangeEnum(String exchange){
        this.exchange = exchange;
    }

    public String getExchange() {
        return exchange;
    }

}
