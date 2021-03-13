package org.example.domain.juego.events;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.juego.Jugador;
import org.example.domain.juego.values.JugadorId;

import java.util.Map;

public class RondaCreada extends DomainEvent {

    private final Map<JugadorId, Jugador> jugadores;

    public RondaCreada(Map<JugadorId, Jugador> jugadores) {
        super("nomemientas.RondaCreada");
        this.jugadores = jugadores;
    }

    public Map<JugadorId, Jugador> getJugadores() {
        return jugadores;
    }
}
