package org.example.domain.ronda.command;

import co.com.sofka.domain.generic.Command;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.ronda.values.Apuesta;
import org.example.domain.ronda.values.RondaId;

public class AsignarApuesta implements Command {

    private final RondaId rondaId;
    private final JugadorId jugadorId;
    private final Apuesta apuesta;

    public AsignarApuesta(RondaId rondaId, JugadorId jugadorId, Apuesta apuesta){
        this.rondaId = rondaId;
        this.jugadorId = jugadorId;
        this.apuesta = apuesta;
    }

    public RondaId getRondaId() {
        return rondaId;
    }

    public JugadorId getJugadorId() {
        return jugadorId;
    }

    public Apuesta getApuesta() {
        return apuesta;
    }
}
