package org.example.domain.juego;

import co.com.sofka.domain.generic.EventChange;
import org.example.domain.juego.events.JuegoCreado;
import org.example.domain.juego.events.JuegoFinalizadoGanador;
import org.example.domain.juego.events.JuegoIniciado;
import org.example.domain.juego.events.JugadorAnhadido;

public class JuegoChange extends EventChange {

    public JuegoChange(Juego juego){

        apply((JuegoCreado event) ->{
            juego.jugadores = event.getJugadores();
            juego.juegoIniciado = Boolean.FALSE;
            juego.hayGanador = Boolean.FALSE;
        });

        apply((JugadorAnhadido event) -> {
            juego.jugadores.put(event.getJugador().identity(), event.getJugador());
        });

        apply((JuegoIniciado event) ->{

            if (Boolean.TRUE.equals(juego.juegoIniciado)){

                throw new IllegalArgumentException("El juego ya está iniciado");
            }

            if(juego.jugadores().size() < 3){
                throw new IllegalArgumentException("El juego no puede iniciar con menos de 3 jugadores");
            }

            juego.juegoIniciado= Boolean.TRUE;
        });

        apply((JuegoFinalizadoGanador event) ->{
            if(Boolean.FALSE.equals(juego.juegoIniciado)){
                throw new IllegalArgumentException("El juego no puede finalizar si no ha iniciado");
            }

            if(Boolean.TRUE.equals(juego.hayGanador)){
                throw new IllegalArgumentException("El juego ya tiene ganador");
            }

            juego.hayGanador = Boolean.TRUE;

        });
    }

}
