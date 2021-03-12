package org.example.domain.ronda;

import co.com.sofka.domain.generic.AggregateEvent;
import org.example.domain.juego.Jugador;
import org.example.domain.juego.values.Dinero;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.ronda.values.Dado;
import org.example.domain.ronda.values.Puntaje;
import org.example.domain.ronda.values.RondaId;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Ronda extends AggregateEvent<RondaId> {

    protected Map<JugadorId, Jugador> jugadoresRonda;
    protected Map<JugadorId, Puntaje> puntajes;
    protected Dinero capitalAcumulado;
    protected Set<Etapa> etapas;
    protected List<Dado> dados;


    public Ronda(RondaId entityId) {
        super(entityId);
    }

}
