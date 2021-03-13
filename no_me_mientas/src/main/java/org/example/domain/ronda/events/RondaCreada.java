package org.example.domain.ronda.events;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.juego.Jugador;
import org.example.domain.juego.values.Dinero;
import org.example.domain.juego.values.JuegoId;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.ronda.Etapa;
import org.example.domain.ronda.values.Dado;
import org.example.domain.ronda.values.Puntaje;
import org.example.domain.ronda.values.RondaId;

import java.util.*;

public class RondaCreada extends DomainEvent {

    private final RondaId rondaId;
    private final JuegoId juegoId;


    public RondaCreada( RondaId rondaId, JuegoId juegoId) {

        super("nomemientas.RondaCreada");
        this.rondaId = rondaId;
        this.juegoId = juegoId;
    }

    public RondaId getRondaId() {
        return rondaId;
    }

    public JuegoId getJuegoId() {
        return juegoId;
    }
}
