package org.example.domain.ronda.events;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.ronda.values.Apuesta;

public class ApuestaAsignada extends DomainEvent {
    private final JugadorId jugadorId;
    private final  Apuesta apuesta;

    public ApuestaAsignada(JugadorId jugadorId, Apuesta apuesta) {
        super("nomemientas.ApuestaAsignada");
        this.jugadorId = jugadorId;
        this.apuesta = apuesta;
    }

    public JugadorId getJugadorId() {
        return jugadorId;
    }

    public Apuesta getApuesta() {
        return apuesta;
    }
}
