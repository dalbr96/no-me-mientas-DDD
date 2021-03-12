package org.example.domain.juego.events;

import co.com.sofka.domain.generic.DomainEvent;

public class JuegoFinalizadoGanador extends DomainEvent {

    public JuegoFinalizadoGanador() {

        super("nomemientas.JuegoFinalizadoGanador");
    }
}
