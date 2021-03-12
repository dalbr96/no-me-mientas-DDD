package org.example.domain.juego.command;

import co.com.sofka.domain.generic.Command;
import org.example.domain.juego.values.JuegoId;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.juego.values.Name;

public class AddJugador implements Command {
    private final JuegoId juegoId;
    private final JugadorId jugadorId;
    private final Name nombre;


    public AddJugador(JuegoId juegoId, JugadorId jugadorId, Name nombre) {
        this.juegoId = juegoId;
        this.jugadorId = jugadorId;
        this.nombre = nombre;
    }

    public JuegoId getJuegoId() {
        return juegoId;
    }

    public JugadorId getJugadorId() {
        return jugadorId;
    }

    public Name getNombre() {
        return nombre;
    }
}
