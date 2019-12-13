package sap.commerce.org.unitservice.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import sap.commerce.org.unitservice.dto.NotificationDTO;

@Component
public class QueueProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${unitservice.rabbitmq.exchange}")
    private String exchange;

    @Value("${unitservice.rabbitmq.send-queue}")
    private String sendQueue;

    @Value("${unitservice.rabbitmq.routing-key}")
    private String routingkey;

    @Autowired
    public QueueProducer(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void produce(final NotificationDTO notification) throws Exception {
        System.out.println("Sending notification...");
        rabbitTemplate.convertAndSend(exchange, routingkey, notification);
        System.out.println("Notification stored in queue successfully");
    }
}
