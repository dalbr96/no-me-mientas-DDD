package org.example.domain.juego;

import co.com.sofka.domain.generic.EventChange;
import org.example.domain.juego.events.JuegoCreado;
import org.example.domain.juego.events.JuegoIniciado;
import org.example.domain.juego.events.JugadorAnhadido;

public class JuegoChange extends EventChange {

    public JuegoChange(Juego juego){

        apply((JuegoCreado event) ->{
            juego.jugadores = event.getJugadores();
            juego.juegoIniciado = Boolean.FALSE;
            juego.tieneGanador = Boolean.FALSE;
        });

        apply((JugadorAnhadido event) -> {
            juego.jugadores.put(event.getJugador().identity(), event.getJugador());
        });

        apply((JuegoIniciado event) ->{

            if (Boolean.TRUE.equals(juego.juegoIniciado)){

                throw new IllegalArgumentException("El juego ya está iniciado");
            }

            juego.juegoIniciado = Boolean.TRUE;
        });
    }

}
