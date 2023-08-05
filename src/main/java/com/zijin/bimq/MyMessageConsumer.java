package com.zijin.bimq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
@Slf4j
@Component

public class MyMessageConsumer {
    @RabbitListener(queues = {"code_queue"},ackMode = "MANUAL")
    @SneakyThrows
    public void receiveMessage(String message,Channel channel,@Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag){
        log.info("receiveMessage message= {}",message);
        channel.basicAck(deliveryTag,false);
    }

 }




