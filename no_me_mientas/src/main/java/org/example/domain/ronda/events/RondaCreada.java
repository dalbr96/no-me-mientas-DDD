package org.example.domain.ronda.events;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.ronda.values.CapitalesJugadoresRonda;
import org.example.domain.juego.values.JuegoId;
import org.example.domain.ronda.values.RondaId;

import java.util.*;

public class RondaCreada extends DomainEvent {

    private final RondaId rondaId;
    private final JuegoId juegoId;
    private final List<CapitalesJugadoresRonda> jugadoresRonda;



    public RondaCreada(RondaId rondaId, JuegoId juegoId, List<CapitalesJugadoresRonda> jugadoresRonda) {

        super("nomemientas.ronda.RondaCreada");
        this.rondaId = rondaId;
        this.juegoId = juegoId;
        this.jugadoresRonda = jugadoresRonda;
    }

    public RondaId getRondaId() {
        return rondaId;
    }

    public JuegoId getJuegoId() {
        return juegoId;
    }

    public List<CapitalesJugadoresRonda>  getJugadoresRonda() {
        return jugadoresRonda;
    }


}
