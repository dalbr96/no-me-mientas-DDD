package org.example.domain.ronda;

import co.com.sofka.domain.generic.AggregateEvent;
import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.juego.values.Dinero;
import org.example.domain.juego.values.DineroJugadores;
import org.example.domain.juego.values.JuegoId;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.ronda.events.*;
import org.example.domain.ronda.values.Apuesta;
import org.example.domain.ronda.values.Dado;
import org.example.domain.ronda.values.Puntaje;
import org.example.domain.ronda.values.RondaId;

import java.util.*;

public class Ronda extends AggregateEvent<RondaId> {

    protected List<DineroJugadores> jugadores;
    protected Map<JugadorId, Puntaje> puntajes;
    protected Dinero capitalAcumulado;
    protected Set<Etapa> etapas;
    protected List<Dado> dados;
    protected JuegoId juegoId;


    private Ronda(RondaId entityId) {
        super(entityId);
        subscribe(new RondaChange(this));
    }

    public Ronda(RondaId entityId, JuegoId juegoId, List<DineroJugadores> jugadoresRonda){

        super(entityId);
        appendChange(new RondaCreada(entityId, juegoId, jugadoresRonda)).apply();
    }

    public static Ronda from(RondaId entityId, List<DomainEvent> events){
        var ronda = new Ronda(entityId);
        events.forEach(ronda::applyEvent);
        return ronda;
    }

    public void lanzarDados(){

        List<Dado> dados = new ArrayList<>();

        for(int i = 0; i < 6; i++){
            dados.add(new Dado());
        }
        appendChange(new DadosLanzados(dados)).apply();
    }

    public void crearEtapa(){

        Integer apuestaMaxima = this.jugadores.stream()
                .max(Comparator.comparing(dinero -> dinero.value().capital().value()))
                .get().value().capital().value();

        List<JugadorId> jugadoresId = new ArrayList<>();
        for(DineroJugadores jugador: jugadores){
            if(jugador.value().jugando().equals(Boolean.TRUE)){
                jugadoresId.add(jugador.value().jugadorId());
            }
        }

        appendChange(new EtapaCreada(apuestaMaxima, jugadoresId)).apply();
    }

    public void asignarApuesta(JugadorId jugadorId, Apuesta apuesta){
        appendChange(new ApuestaAsignada(jugadorId, apuesta)).apply();
    }

    public void destaparDados(){
        appendChange(new DadosDestapados()).apply();
    }

    public void asignarDadosAEtapa(){
        appendChange(new DadosAsignadosAEtapa()).apply();
    }

    public void asignarTurnos(){
        appendChange(new TurnosAsignados()).apply();
    }

    public void eliminarJugador(JugadorId jugadorId){
        appendChange(new JugadorEliminado(jugadorId)).apply();
    }

    public List<DineroJugadores> jugadores() {
        return jugadores;
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
