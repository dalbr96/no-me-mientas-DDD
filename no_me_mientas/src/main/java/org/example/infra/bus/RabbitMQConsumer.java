package org.example.infra.bus;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.DeserializeEventException;
import co.com.sofka.infraestructure.bus.serialize.SuccessNotificationSerializer;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import java.util.concurrent.Flow;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RabbitMQConsumer implements Flow.Subscription{
    private static final Logger LOGGER = Logger.getLogger(RabbitMQConsumer.class.getName());
    private final Flow.Subscriber<DomainEvent> eventSubscriber;

    @Autowired
    public RabbitMQConsumer(EventListenerSubscriber eventSubscriber){
        this.eventSubscriber = eventSubscriber;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "juego.handles", durable = "true"),
            exchange = @Exchange(value = "no_me_mientas", type = "topic"),
            key = "nomemientan.juego.#"
    ))


    public void recievedMessageSlack(Message<String> message){
        localReplay(message);
    }

    private void localReplay(Message<String> payload) {
        try{
            String message = payload.getPayload();
            var notification = SuccessNotificationSerializer.instance().deserialize(message);
            var event = notification.deserializeEvent();
            LOGGER.log(Level.INFO, "Recibe message from {0} -- {1}",
                    new String[]{event.type, event.getClass().getName()});
            eventSubscriber.onSubscribe(this);
            eventSubscriber.onNext(event);
        }catch (DeserializeEventException e){
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }catch (Exception e){
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            eventSubscriber.onError(e);
        }
    }

    @Override
    public void request(long n) {

    }

    @Override
    public void cancel() {

    }
}
