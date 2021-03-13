package org.example.domain.ronda;

import co.com.sofka.domain.generic.EventChange;
import org.example.domain.juego.events.JuegoCreado;
import org.example.domain.ronda.events.RondaCreada;

public class RondaChange extends EventChange {

    public RondaChange(Ronda ronda){

        apply((RondaCreada event) ->{
            ronda.jugadoresRonda = event.getJugadoresRonda();
            ronda.puntajes = event.getPuntajes();
            ronda.capitalAcumulado = event.getCapitalAcumulado();
            ronda.dados = event.getDados();
            ronda.etapas = event.getEtapas();
        });
    }
}
