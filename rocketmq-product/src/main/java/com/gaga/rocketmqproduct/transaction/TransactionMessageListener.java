package com.gaga.rocketmqproduct.transaction;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * 事务消息监听
 * @Author fuGaga
 * @Date 2021/4/16 14:33
 * @Version 1.0
 */
@Slf4j
@Component
@RocketMQTransactionListener(txProducerGroup="transactionGroup")
public class TransactionMessageListener implements RocketMQLocalTransactionListener {

    //事务消息发送完毕会调用此方法执行本地事务
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        System.out.println("-------------开始执行本地事务---------");
        String transationId = (String) message.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);
        String messageId = message.getHeaders().getId().toString();
        log.info("消息id------->"+messageId);
        log.info("事务id------->"+transationId);
        System.out.println("-------------执行本地事务---------");

        return RocketMQLocalTransactionState.UNKNOWN;
    }

    //本地事务执行结果为未知，broker端回调此方法回查事务状态
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        System.out.println("-------------开始执行回查---------");
        String transationId = (String) message.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);
        String messageId = message.getHeaders().getId().toString();
        log.info("消息id------->"+messageId);
        log.info("事务id------->"+transationId);

        return RocketMQLocalTransactionState.UNKNOWN;
    }
}
