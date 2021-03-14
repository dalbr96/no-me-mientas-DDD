package org.example.domain.ronda.events;

import co.com.sofka.domain.generic.DomainEvent;

public class DadosLanzados extends DomainEvent {

    public DadosLanzados() {
        super("nomemientas.LanzarDado");
    }
}
