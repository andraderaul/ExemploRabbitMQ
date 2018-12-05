package br.ufs.dcomp.ExemploRabbitMQ;

import com.rabbitmq.client.*; // API que implementa o protocolo AMQP

import java.io.IOException;

public class Receptor {

  private final static String QUEUE_NAME = "minha-fila";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory(); // pode fazer quantas conexoes quiser
    factory.setUri("amqp://mtvezqic:oT0QoH-plEwlIWdYPd0RZR7Ku1ndXp5A@barnacle.rmq.cloudamqp.com/mtvezqic");
    Connection connection = factory.newConnection(); // ele pode criar canais dentro de uma conexão TCP, independentes de ida e volta
    Channel channel = connection.createChannel(); // abrindo a conexão

                      //(queue-name, durable, exclusive, auto-delete, params); 
    channel.queueDeclare(QUEUE_NAME, false,   false,     false,       null);
    
    System.out.println(" [*] Esperando recebimento de mensagens...");
    
    /* metodo baseado em eventos*/
    Consumer consumer = new DefaultConsumer(channel) {
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)           throws IOException { // esse metodo é executado toda vez que uma mensagem chega pra uma fila

        String message = new String(body, "UTF-8");
        System.out.println(" [x] Mensagem recebida: '" + message + "'");

                        //(deliveryTag,               multiple);
        //channel.basicAck(envelope.getDeliveryTag(), false);
      }
    };
                      //(queue-name, autoAck, consumer);    
    channel.basicConsume(QUEUE_NAME, true,    consumer);
  }
}