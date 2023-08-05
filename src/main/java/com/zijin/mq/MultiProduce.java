package com.zijin.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.util.Scanner;

public class MultiProduce {

  private static final String TASK_QUEUE_NAME = "task_queue";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    try (Connection connection = factory.newConnection();
         Channel channel = connection.createChannel()) {
         channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
         //创建一个新的 Scanner 对象，用于从控制台读取用户的输入。
        Scanner scanner=new Scanner(System.in);
//        循环检查是否有下一行输入。该循环会持续运行，直到用户输入结束（通过按下Ctrl + D，或者在控制台中输入EOF）。
        while (scanner.hasNext()){
//            从控制台读取下一行用户输入，并将其存储在名为 message 的字符串变量中。
            String message = scanner.nextLine();
            channel.basicPublish("", TASK_QUEUE_NAME,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
        }


    }
  }

}