package org.example.domain.juego.events;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.juego.values.Dinero;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.juego.values.Name;

public class JugadorAnhadidoConCapital extends DomainEvent {
    private final JugadorId jugadorId;
    private final Name nombre;
    private Dinero capital;

    public JugadorAnhadidoConCapital(JugadorId jugadorId, Name nombre, Dinero capital) {
        super("nomemientas.JugadorAñadidoConCapital");
        this.jugadorId = jugadorId;
        this.nombre = nombre;
        this.capital = capital;
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
