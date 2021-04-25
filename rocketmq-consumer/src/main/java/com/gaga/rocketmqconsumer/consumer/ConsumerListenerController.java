package com.gaga.rocketmqconsumer.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * rocketmq 消费者监听
 * @Author fuGaga
 * @Date 2021/4/7 16:56
 * @Version 1.0
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "${rocketmq.consumer.group}",
        topic = "${rocketmq.consumer.topic}",
        consumeMode = ConsumeMode.ORDERLY) //consumeMode设置消费模式为顺序消费
public class ConsumerListenerController implements RocketMQListener<String> {

    @Override
    public void onMessage(String s) {
        log.info("消费消息----内容为:{}", s);
    }
}
