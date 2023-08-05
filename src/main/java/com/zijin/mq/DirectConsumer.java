package com.zijin.mq;

import com.rabbitmq.client.*;

public class DirectConsumer {

  private static final String EXCHANGE_NAME = "direct_exchange";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.exchangeDeclare(EXCHANGE_NAME, "direct");
      String queueName1 ="xiaoyu_queue";
      channel.queueDeclare(queueName1, true, false, false, null);
//    绑定队列
      channel.queueBind(queueName1, EXCHANGE_NAME, "xiaoyu");
      String queueName2 ="xiaochen_queue";
      channel.queueDeclare(queueName2, true, false, false, null);
//    绑定队列
      channel.queueBind(queueName2, EXCHANGE_NAME, "xiaochen");
    DeliverCallback xiaoyudeliverCallback = (consumerTag, delivery) -> {
        String message = new String(delivery.getBody(), "UTF-8");
        System.out.println(" [x] Received '" +
            delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
    };
      DeliverCallback xiaochendeliverCallback = (consumerTag, delivery) -> {
          String message = new String(delivery.getBody(), "UTF-8");
          System.out.println(" [x] Received '" +
                  delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
      };
    channel.basicConsume(queueName1, true, xiaoyudeliverCallback, consumerTag -> { });
      channel.basicConsume(queueName2, true, xiaochendeliverCallback, consumerTag -> { });
  }
}