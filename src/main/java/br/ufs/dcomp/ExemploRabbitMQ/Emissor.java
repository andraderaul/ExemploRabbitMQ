package br.ufs.dcomp.ExemploRabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Emissor {

  private final static String QUEUE_NAME = "minha-fila";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri("amqp://mtvezqic:oT0QoH-plEwlIWdYPd0RZR7Ku1ndXp5A@barnacle.rmq.cloudamqp.com/mtvezqic");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

                      //(queue-name, durable, exclusive, auto-delete, params); 
    channel.queueDeclare(QUEUE_NAME, false,   false,     false,       null); // isso é pra funcionar independemente de quem rodar primeiro, (emissor ou receptor)

    String message = "Olá!!!";
    
                    //  (exchange, routingKey, props, message-body             ); 
    channel.basicPublish("",       QUEUE_NAME, null,  message.getBytes("UTF-8")); // aqui n estamos passando exchange, mas estamos passando a chave de roteamento padrão
    System.out.println(" [x] Mensagem enviada: '" + message + "'");

    channel.close();
    connection.close();
  }
}