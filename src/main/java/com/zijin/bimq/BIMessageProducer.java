package com.zijin.bimq;

import com.zijin.constant.BIConstant;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class BIMessageProducer {
    @Resource
  private RabbitTemplate rabbitTemplate;
  public void sendMessage(String message){
      rabbitTemplate.convertAndSend(BIConstant.BI_EXCHANGE_NANE,BIConstant.BI_ROUTINGKEY,message);
  }
}
