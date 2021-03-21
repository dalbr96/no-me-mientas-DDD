package org.example.domain.juego.events;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.juego.values.Dinero;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.juego.values.Name;

public class JugadorAgregado extends DomainEvent {

    private final JugadorId jugadorId;
    private final Name nombre;
    private Dinero capital;

    public JugadorAgregado(JugadorId jugadorId, Name name, Dinero capital){
        super("nomemientas.juego.JugadorAgregado");
        this.jugadorId = jugadorId;
        this.nombre = name;
        this.capital = capital;
    }


    public Name getNombre() {
        return nombre;
    }

    public Dinero getCapital() {
        return capital;
    }

    public JugadorId getJugadorId() {
        return jugadorId;
    }
}
