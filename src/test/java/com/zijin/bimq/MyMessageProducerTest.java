package com.zijin.bimq;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MyMessageProducerTest {
    @Resource
    private MyMessageProducer myMessageProducer;
    @Test
    void sendMessage(){
        myMessageProducer.sendMessage("code_exchange","my_routingKey","你好呀");
    }


}