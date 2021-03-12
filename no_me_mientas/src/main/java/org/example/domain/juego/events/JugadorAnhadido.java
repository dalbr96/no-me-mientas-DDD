package org.example.domain.juego.events;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.juego.Jugador;

public class JugadorAnhadido extends DomainEvent {

    private final Jugador jugador;

    public JugadorAnhadido(Jugador jugador){
        super("nomemientas.JugadorAñadido");
        this.jugador = jugador;
    }

    public Jugador getJugador() {
        return jugador;
    }
}
