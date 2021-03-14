package org.example.domain.ronda;

import co.com.sofka.domain.generic.AggregateEvent;
import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.juego.values.Dinero;
import org.example.domain.juego.values.JuegoId;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.ronda.events.*;
import org.example.domain.ronda.values.Dado;
import org.example.domain.ronda.values.Puntaje;
import org.example.domain.ronda.values.RondaId;

import java.util.*;

public class Ronda extends AggregateEvent<RondaId> {

    protected Set<JugadorId> jugadoresRonda;
    protected Map<JugadorId, Dinero> capitalJugadores;
    protected Map<JugadorId, Puntaje> puntajes;
    protected Dinero capitalAcumulado;
    protected Set<Etapa> etapas;
    protected List<Dado> dados;
    protected JuegoId juegoId;


    private Ronda(RondaId entityId) {
        super(entityId);
        subscribe(new RondaChange(this));
    }

    public Ronda(RondaId entityId, JuegoId juegoId, Set<JugadorId> jugadoresRonda, Map<JugadorId, Dinero> capitalJugadores){

        super(entityId);
        appendChange(new RondaCreada(entityId, juegoId, jugadoresRonda, capitalJugadores)).apply();
    }

    public static Ronda from(RondaId entityId, List<DomainEvent> events){
        var ronda = new Ronda(entityId);
        events.forEach(ronda::applyEvent);
        return ronda;
    }


    public void lanzarDados(){
        for(int i = 0; i < 6; i++){
            appendChange(new DadoLanzado()).apply();
        }
    }

    public void crearEtapa(){
        appendChange(new EtapaCreada(capitalJugadores)).apply();
    }

    public void destaparDados(){
        appendChange(new DadosDestapados()).apply();
    }

    public void asignarDadosAEtapa(){
        appendChange(new DadosAsignadosAEtapa()).apply();
    }

    public Set<JugadorId> jugadoresRonda() {
        return jugadoresRonda;
    }

    public Map<JugadorId, Puntaje> puntajes() {
        return puntajes;
    }

    public Dinero capitalAcumulado() {
        return capitalAcumulado;
    }

    public Set<Etapa> etapas() {
        return etapas;
    }

    public List<Dado> dados() {
        return dados;
    }
}
