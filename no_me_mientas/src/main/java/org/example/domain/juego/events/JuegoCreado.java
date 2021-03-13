package org.example.domain.juego.events;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.juego.values.JuegoId;

public class JuegoCreado extends DomainEvent {

    private final JuegoId juegoId;

    public JuegoCreado(JuegoId juegoId) {

        super("nomemientas.JuegoCreado");
        this.juegoId = juegoId;
    }

    public JuegoId getJuegoId() {
        return juegoId;
    }
}
