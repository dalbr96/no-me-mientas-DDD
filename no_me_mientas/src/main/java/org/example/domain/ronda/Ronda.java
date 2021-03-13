package org.example.domain.ronda;

import co.com.sofka.domain.generic.AggregateEvent;
import org.example.domain.juego.Jugador;
import org.example.domain.juego.values.Dinero;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.ronda.events.RondaCreada;
import org.example.domain.ronda.values.Dado;
import org.example.domain.ronda.values.Puntaje;
import org.example.domain.ronda.values.RondaId;

import java.util.*;

public class Ronda extends AggregateEvent<RondaId> {

    protected Map<JugadorId, Jugador> jugadoresRonda;
    protected Map<JugadorId, Puntaje> puntajes;
    protected Dinero capitalAcumulado;
    protected Set<Etapa> etapas;
    protected List<Dado> dados;


    private Ronda(RondaId entityId) {
        super(entityId);
        subscribe(new RondaChange(this));
    }

    public Ronda(RondaId entityId, Map<JugadorId, Jugador> jugadoresRonda){

        super(entityId);

        HashMap<JugadorId, Puntaje> puntajes = new HashMap<>();

        jugadoresRonda.forEach((jugadorId, jugador) ->{
            puntajes.put(jugadorId, new Puntaje());
        });

        appendChange(new RondaCreada(jugadoresRonda, puntajes)).apply();
    }

    public void iniciarEtapa(){

    }

}
