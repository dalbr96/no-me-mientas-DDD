package org.example.domain.juego;

import co.com.sofka.domain.generic.EventChange;
import org.example.domain.juego.events.*;
import org.example.domain.juego.values.JugadorId;


import java.util.HashMap;

public class JuegoChange extends EventChange {

    public JuegoChange(Juego juego){

        apply((JuegoCreado event) ->{
            juego.jugadores = event.getJugadores();
            juego.juegoIniciado = Boolean.FALSE;
            juego.hayGanador = Boolean.FALSE;
        });

        apply((JugadorAnhadido event) -> {

            if(Boolean.TRUE.equals(juego.juegoIniciado)){
                throw new IllegalArgumentException("No puede agregar jugadores en un juego iniciado");
            }

            HashMap<JugadorId, Jugador> jugadoresNuevos = new HashMap<>();
            juego.jugadores.forEach((jugadorId, jugador)-> jugadoresNuevos.put(jugadorId, jugador));
            jugadoresNuevos.put(event.getJugadorId(), new Jugador(event.getJugadorId(), event.getNombre()));
            juego.jugadores = jugadoresNuevos;
        });

        apply((JugadorAnhadidoConCapital event) -> {

            if(Boolean.TRUE.equals(juego.juegoIniciado)){
                throw new IllegalArgumentException("No puede agregar jugadores en un juego iniciado");
            }

            HashMap<JugadorId, Jugador> jugadoresNuevos = new HashMap<>();
            juego.jugadores.forEach((jugadorId, jugador)-> jugadoresNuevos.put(jugadorId, jugador));
            jugadoresNuevos.put(event.getJugadorId(), new Jugador(event.getJugadorId(), event.getNombre()));
            juego.jugadores = jugadoresNuevos;
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
