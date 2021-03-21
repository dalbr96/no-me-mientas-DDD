package org.example.infra.bus;

import co.com.sofka.business.generic.BusinessException;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.generic.Incremental;
import co.com.sofka.infraestructure.bus.EventBus;
import co.com.sofka.infraestructure.bus.notification.SuccessNotification;
import co.com.sofka.infraestructure.bus.serialize.SuccessNotificationSerializer;
import co.com.sofka.infraestructure.event.ErrorEvent;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class RabbitMQEventBus implements EventBus {
    private static final String EXCHANGE = "no_me_mientas";
    private static final String TOPIC_BUSINESS_ERROR = "org.example.no_me_mientas.business.error";
    private static final Logger LOGGER = Logger.getLogger(RabbitMQEventBus.class.getName());

    private final RabbitTemplate rabbitTemplate;
    private final RabbitAdmin rabbitAdmin;

    public RabbitMQEventBus(@Value("${spring.bus.uri}") String uri){

        this.rabbitTemplate = new RabbitTemplate(new CachingConnectionFactory(URI.create(uri)));
        this.rabbitAdmin = new RabbitAdmin(this.rabbitTemplate);

    }
    @Override
    public void publish(DomainEvent domainEvent) {
        try{

            var notification = SuccessNotification.wrapEvent(EXCHANGE, domainEvent);
            var notificacionSerializada = SuccessNotificationSerializer.instance().serialize(notification);

            rabbitAdmin.declareExchange(new TopicExchange(EXCHANGE));
            rabbitTemplate.convertAndSend(EXCHANGE, domainEvent.type, notificacionSerializada.getBytes(StandardCharsets.UTF_8));

            LOGGER.log(Level.INFO, "Event Published to {0}/{1}", new String[]{domainEvent.type, domainEvent.aggregateRootId()});

            var isIncremental = domainEvent instanceof Incremental;

            if(!isIncremental){
                Thread.sleep(1000);
            }

        }catch (InterruptedException e){
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void publishError(ErrorEvent errorEvent) {
        if(errorEvent.error instanceof BusinessException){
            publishErrorLog(errorEvent);
        }
        LOGGER.log(Level.SEVERE, errorEvent.error.getMessage(), errorEvent.error);
    }

    private void publishErrorLog(ErrorEvent errorEvent){
        LOGGER.log(Level.WARNING, "Error Event published to {0}/{1}",
                new String[]{TOPIC_BUSINESS_ERROR, errorEvent.identify});
    }
}
