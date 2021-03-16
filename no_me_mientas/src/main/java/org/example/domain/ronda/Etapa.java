package org.example.domain.ronda;

import co.com.sofka.domain.generic.Entity;
import org.example.domain.juego.values.Dinero;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.ronda.values.Apuesta;
import org.example.domain.ronda.values.Dado;
import org.example.domain.ronda.values.EtapaId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Etapa extends Entity<EtapaId> {

    private Map<JugadorId, Apuesta> turnos;
    private List<Dado> dadosDestapados;
    private final Dinero apuestaMaxima;
    private List<JugadorId> ordenTurnos;
    private Boolean esActual;


    public Etapa(EtapaId entityId, Dinero apuestaMaxima, List<JugadorId> jugadoresEtapa){
        super(entityId);
        this.apuestaMaxima = apuestaMaxima;
        this.turnos = new HashMap<>();
        this.esActual = Boolean.TRUE;
        this.dadosDestapados = new ArrayList<>();
        this.ordenTurnos = new ArrayList<>(jugadoresEtapa);
    }

    public void agregarDados(List<Dado> dados){
        this.dadosDestapados = dados;
    }

    public void cambiarActual(){
        this.esActual = Boolean.FALSE;
    }

    public void asignarOrden(List<JugadorId> ordenTurnos){this.ordenTurnos = ordenTurnos;}

    public void asignarApuesta(JugadorId jugadorId, Apuesta apuesta){
        turnos.put(jugadorId, apuesta);
    }

    public List<JugadorId> orden() {
        return ordenTurnos;
    }

    public Dinero apuestaMaxima() {
        return apuestaMaxima;
    }

    public Map<JugadorId, Apuesta> turnos() {
        return turnos;
    }

    public Boolean esActual() {
        return esActual;
    }

    public List<Dado> dadosDestapados() {
        return dadosDestapados;
    }
}
