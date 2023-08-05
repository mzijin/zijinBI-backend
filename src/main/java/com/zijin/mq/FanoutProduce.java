package com.zijin.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


import java.util.Scanner;

public class FanoutProduce {

  private static final String EXCHANGE_NAME = "fanout-exchange";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    try (Connection connection = factory.newConnection();
         Channel channel = connection.createChannel()) {
//        创建交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        Scanner scanner=new Scanner(System.in);
        while (scanner.hasNext()) {
            String message=scanner.nextLine();
            channel.basicPublish(EXCHANGE_NAME,"",null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
        }

    }
  }
}