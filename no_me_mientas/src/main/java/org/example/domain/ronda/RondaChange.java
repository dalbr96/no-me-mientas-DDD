package org.example.domain.ronda;

import co.com.sofka.domain.generic.EventChange;
import org.example.domain.juego.Jugador;
import org.example.domain.juego.values.Dinero;
import org.example.domain.ronda.events.JugadorAgregadoARonda;
import org.example.domain.ronda.events.RondaCreada;
import org.example.domain.ronda.values.Puntaje;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class RondaChange extends EventChange {

    public RondaChange(Ronda ronda){

        apply((RondaCreada event) ->{
            ronda.jugadoresRonda = new HashMap<>();
            ronda.puntajes = new HashMap<>();
            ronda.capitalAcumulado = new Dinero(0);
            ronda.dados = new ArrayList<>();
            ronda.etapas = new HashSet<>();
            ronda.juegoId = event.getJuegoId();
        });

        apply((JugadorAgregadoARonda event) -> {
            ronda.jugadoresRonda.put(
                    event.getJugadorId(),
                    new Jugador(
                            event.getJugadorId(),
                            event.getNombre(),
                            event.getCapital())
            );

            ronda.puntajes.put(event.getJugadorId(),
                    new Puntaje(0));
        });
    }
}
