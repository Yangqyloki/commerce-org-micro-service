package sap.commerce.org.userservice.rabbitmq;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import sap.commerce.org.userservice.data.NotificationDTO;

@Component
public class QueueConsumer {
    private CountDownLatch latch = new CountDownLatch(1);

    private ObjectMapper mapper = new ObjectMapper();

    public void receiveMessage(final byte[] message) {
        try {
            latch.await(10000, TimeUnit.MILLISECONDS);
            System.out.println("Received (String) " + new String(message));
            final NotificationDTO notification = mapper.readValue(new String(message), NotificationDTO.class);
            System.out.println(mapper.writeValueAsString(notification.getMessage()));
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
