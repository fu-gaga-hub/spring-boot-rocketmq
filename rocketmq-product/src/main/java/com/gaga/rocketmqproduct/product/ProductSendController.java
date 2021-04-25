package com.gaga.rocketmqproduct.product;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

/**
 * rocketmq生产者类
 *
 * @Author fuGaga
 * @Date 2021/4/7 14:58
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("productSendController")
public class ProductSendController {

    @Value("${rocketmq.producer.topic}")
    private String topic;

    @Autowired
    RocketMQTemplate rocketMQTemplate;

    /**
     * 发送异步消息
     *
     * @return void
     * @Author fuGaga
     * @Date 2021/4/7 17:12
     **/
    @RequestMapping("sendMessageByAsync")
    public void sendMessageByAsync() {
        for (int i = 0; i < 6; i++) {
            rocketMQTemplate.asyncSend(topic, "这是条异步消息" + i, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    log.info("send successful----------成功");
                }

                @Override
                public void onException(Throwable throwable) {
                    log.info("send fail; {}", throwable.getMessage());
                }
            });
        }

    }

    /**
     * 发送异步顺序消息
     *
     * @return void
     * @Author fuGaga
     * @Date 2021/4/7 17:18
     **/
    @RequestMapping("sendMessageByAsyncOrderly")
    public void sendMessageByAsyncOrderly() {
        for (int i = 0; i < 6; i++) {
//            rocketMQTemplate.asyncSendOrderly(topic, "这是条异步顺序消息"+i, "123", new SendCallback() {
//                @Override
//                public void onSuccess(SendResult sendResult) {
//                    log.info("send successful----------成功");
//                }
//
//                @Override
//                public void onException(Throwable throwable) {
//                    log.info("send fail; {}", throwable.getMessage());
//                }
//            });
            rocketMQTemplate.sendOneWayOrderly(topic, "这是条单向顺序消息"+i, "123");
        }
    }

    /**
     * 发送事务消息
     * @Author fuGaga
     * @Date 2021/4/16 14:06
     * @return void
     **/
    @RequestMapping("sendMessageTransaction")
    public void sendMessageTransaction(){
        Message<String> message = MessageBuilder.withPayload("这是一条事务消息").build();
        rocketMQTemplate.sendMessageInTransaction("transactionGroup",topic,message,"dsa");
    }
}
