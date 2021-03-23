package org.example.domain.ronda.events;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.juego.values.Dinero;
import org.example.domain.juego.values.DineroJugadores;
import org.example.domain.juego.values.JuegoId;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.ronda.values.RondaId;

import java.util.*;

public class RondaCreada extends DomainEvent {

    private final RondaId rondaId;
    private final JuegoId juegoId;
    private final List<DineroJugadores> jugadoresRonda;



    public RondaCreada(RondaId rondaId, JuegoId juegoId, List<DineroJugadores> jugadoresRonda) {

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

    public List<DineroJugadores>  getJugadoresRonda() {
        return jugadoresRonda;
    }


}
