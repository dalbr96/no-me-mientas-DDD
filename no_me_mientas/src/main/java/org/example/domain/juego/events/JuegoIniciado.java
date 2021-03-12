package org.example.domain.juego.events;

import co.com.sofka.domain.generic.DomainEvent;

public class JuegoIniciado extends DomainEvent {
    public JuegoIniciado(){

        super("nomemientas.JuegoIniciado");
    }
}
