package com.zijin.bimq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class MqInitMain {
    public static void main(String[] args) {

//    创建测试所需要的交换机和队列，只需要执行一次
    try {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
//        创建交换机
        String EXCHANGE_NAME="code_exchange";

        channel.exchangeDeclare(EXCHANGE_NAME,"direct");
//        创建队列
        String QUEUE_NAME="code_queue";
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"my_routingKey");
    } catch (Exception e) {


    }
    }
}
