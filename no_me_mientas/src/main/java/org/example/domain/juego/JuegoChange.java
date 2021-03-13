package org.example.domain.juego;

import co.com.sofka.business.generic.BusinessException;
import co.com.sofka.domain.generic.EventChange;
import org.example.domain.juego.events.*;
import org.example.domain.juego.values.JugadorId;


import java.util.HashMap;

public class JuegoChange extends EventChange {

    public JuegoChange(Juego juego){

        apply((JuegoCreado event) ->{
            juego.jugadores = new HashMap<>();
            juego.juegoIniciado = Boolean.FALSE;
            juego.hayGanador = Boolean.FALSE;
        });

        apply((JugadorAgregado event) -> {

            if(Boolean.TRUE.equals(juego.juegoIniciado)){
                throw new IllegalArgumentException("No puede agregar jugadores en un juego iniciado");
            }

            if(juego.jugadores.size() > 23){
                throw new BusinessException(juego.identity().value(), "El máximo numero de jugadores ha sido alcanzado");
            }

            juego.jugadores.put(event.getJugadorId(),
                    new Jugador(
                            event.getJugadorId(),
                            event.getNombre(),
                            event.getCapital()));
        });

        apply((JuegoIniciado event) ->{

            juego.juegoIniciado= Boolean.TRUE;
        });

        apply((JuegoFinalizadoGanador event) ->{

            juego.hayGanador = Boolean.TRUE;
        });
    }

}
