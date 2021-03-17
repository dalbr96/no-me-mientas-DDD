package org.example.domain.ronda.command;

import co.com.sofka.domain.generic.Command;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.ronda.values.RondaId;

public class EliminarJugador implements Command {

    private final JugadorId jugadorId;
    private final RondaId rondaId;


    public EliminarJugador(JugadorId jugadorId, RondaId rondaId) {
        this.jugadorId = jugadorId;
        this.rondaId = rondaId;
    }

    public JugadorId getJugadorId() {
        return jugadorId;
    }

    public RondaId getRondaId() {
        return rondaId;
    }
}
