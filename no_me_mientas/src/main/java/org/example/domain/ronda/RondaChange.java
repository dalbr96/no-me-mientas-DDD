package org.example.domain.ronda;

import co.com.sofka.domain.generic.EventChange;
import org.example.domain.juego.Jugador;
import org.example.domain.juego.values.Dinero;
import org.example.domain.ronda.events.DadosLanzados;
import org.example.domain.ronda.events.JugadorAgregadoARonda;
import org.example.domain.ronda.events.RondaCreada;
import org.example.domain.ronda.values.Dado;
import org.example.domain.ronda.values.Puntaje;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class RondaChange extends EventChange {

    public RondaChange(Ronda ronda){

        apply((RondaCreada event) ->{
            ronda.capitalJugadores = event.getCapitalJugadores();
            ronda.puntajes = new HashMap<>();

            ronda.capitalJugadores.forEach((jugadorId, capital) -> {
                ronda.puntajes.put(jugadorId, new Puntaje(0));
            });

            ronda.capitalAcumulado = new Dinero(0);
            ronda.dados = new ArrayList<>();
            ronda.etapas = new HashSet<>();
            ronda.juegoId = event.getJuegoId();
        });

        apply((DadosLanzados event) ->{
            for(int i = 0; i < 6; i++){
                ronda.dados.add(new Dado());
            }
        });
    }
}
