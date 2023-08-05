package com.zijin.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class TopicConsumer {

  private static final String EXCHANGE_NAME = "topic_exchange";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
//    创建队列
      String queueName1 ="frontend_queue";
      channel.queueDeclare(queueName1, true, false, false, null);
//    绑定队列
      channel.queueBind(queueName1, EXCHANGE_NAME, "#.前端.#");
      String queueName2 ="backend_queue";
      channel.queueDeclare(queueName2, true, false, false, null);
//    绑定队列
      channel.queueBind(queueName2, EXCHANGE_NAME, "#.后端.#");
      String queueName3 ="product_queue";
      channel.queueDeclare(queueName3, true, false, false, null);
//    绑定队列
      channel.queueBind(queueName3, EXCHANGE_NAME, "#.产品.#");
      System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
      DeliverCallback xiaoadeliverCallback = (consumerTag, delivery) -> {
          String message = new String(delivery.getBody(), "UTF-8");
          System.out.println(" [xiaoa] Received '" +
                  delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
      };
      DeliverCallback xiaobdeliverCallback = (consumerTag, delivery) -> {
          String message = new String(delivery.getBody(), "UTF-8");
          System.out.println(" [xiaob] Received '" +
                  delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
      };
    DeliverCallback xiaocdeliverCallback = (consumerTag, delivery) -> {
        String message = new String(delivery.getBody(), "UTF-8");
        System.out.println(" [xiaoc] Received '" +
            delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
    };
    channel.basicConsume(queueName1, true, xiaoadeliverCallback, consumerTag -> { });
      channel.basicConsume(queueName2, true, xiaobdeliverCallback, consumerTag -> { });
      channel.basicConsume(queueName3, true, xiaocdeliverCallback, consumerTag -> { });
  }
}