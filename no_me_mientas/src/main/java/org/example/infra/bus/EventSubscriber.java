package org.example.infra.bus;

public interface EventSubscriber {

    void subscribe(String evenType, String exchange);

}
