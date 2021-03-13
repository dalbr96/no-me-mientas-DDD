package org.example.domain.juego.command;

import co.com.sofka.domain.generic.Command;
import org.example.domain.juego.values.Dinero;
import org.example.domain.juego.values.JuegoId;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.juego.values.Name;

public class AddJugadorConCapital implements Command {
    private final JuegoId juegoId;
    private final JugadorId jugadorId;
    private final Name nombre;
    private final Dinero capital;


    public AddJugadorConCapital(JuegoId juegoId, JugadorId jugadorId, Name nombre, Dinero capital) {
        this.juegoId = juegoId;
        this.jugadorId = jugadorId;
        this.nombre = nombre;
        this.capital = capital;
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

    public Dinero getCapital() {
        return capital;
    }
}
