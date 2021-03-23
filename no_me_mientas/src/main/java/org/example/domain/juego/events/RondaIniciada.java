package org.example.domain.juego.events;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.ronda.values.CapitalesJugadoresRonda;
import org.example.domain.ronda.values.RondaId;

import java.util.List;

public class RondaIniciada extends DomainEvent {

    private List<CapitalesJugadoresRonda> jugadores;
    private RondaId rondaId;

    public RondaIniciada(RondaId rondaId, List<CapitalesJugadoresRonda> jugadores) {

        super("nomemientas.juego.RondaIniciada");
        this.rondaId = rondaId;
        this.jugadores = jugadores;

    }

    public List<CapitalesJugadoresRonda> getJugadores() {
        return jugadores;
    }

    public RondaId getRondaId() {
        return rondaId;
    }
}
