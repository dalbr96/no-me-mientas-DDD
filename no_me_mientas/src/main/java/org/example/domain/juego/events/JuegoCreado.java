package org.example.domain.juego.events;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.juego.Jugador;
import org.example.domain.juego.values.JugadorId;

import java.util.Map;

public class JuegoCreado extends DomainEvent {

    private final Map<JugadorId, Jugador> jugadores;
    public JuegoCreado(Map<JugadorId, Jugador> jugadores) {
        super("nomemientas.JuegoCreado");
        this.jugadores = jugadores;
    }
}
