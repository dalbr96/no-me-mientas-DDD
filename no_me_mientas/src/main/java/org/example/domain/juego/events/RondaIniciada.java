package org.example.domain.juego.events;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.juego.values.Dinero;
import org.example.domain.juego.values.DineroJugadores;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.ronda.values.RondaId;

import java.util.List;
import java.util.Map;

public class RondaIniciada extends DomainEvent {

    private List<DineroJugadores> jugadores;
    private RondaId rondaId;

    public RondaIniciada(RondaId rondaId, List<DineroJugadores> jugadores) {

        super("nomemientas.juego.RondaIniciada");
        this.rondaId = rondaId;
        this.jugadores = jugadores;

    }

    public List<DineroJugadores> getJugadores() {
        return jugadores;
    }

    public RondaId getRondaId() {
        return rondaId;
    }
}
