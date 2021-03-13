package org.example.domain.juego.events;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.juego.values.JugadorId;

import java.util.Set;

public class JuegoIniciado extends DomainEvent {

    public JuegoIniciado(){
        super("nomemientas.JuegoIniciado");

    }

}
