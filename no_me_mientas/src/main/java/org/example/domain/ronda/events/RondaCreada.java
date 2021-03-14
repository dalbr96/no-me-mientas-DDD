package org.example.domain.ronda.events;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.juego.values.Dinero;
import org.example.domain.juego.values.JuegoId;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.ronda.values.RondaId;

import java.util.*;

public class RondaCreada extends DomainEvent {

    private final RondaId rondaId;
    private final JuegoId juegoId;
    private Map<JugadorId, Dinero> capitalJugadores;


    public RondaCreada( RondaId rondaId, JuegoId juegoId, Map<JugadorId, Dinero> capitalJugadores) {

        super("nomemientas.RondaCreada");
        this.rondaId = rondaId;
        this.juegoId = juegoId;
        this.capitalJugadores = capitalJugadores;
    }

    public RondaId getRondaId() {
        return rondaId;
    }

    public JuegoId getJuegoId() {
        return juegoId;
    }

    public Map<JugadorId, Dinero> getCapitalJugadores() {
        return capitalJugadores;
    }
}
