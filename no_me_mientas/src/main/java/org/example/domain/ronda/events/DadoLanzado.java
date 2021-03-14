package org.example.domain.ronda.events;

import co.com.sofka.domain.generic.DomainEvent;

public class DadoLanzado extends DomainEvent {

    public DadoLanzado() {
        super("nomemientas.LanzarDado");
    }
}
