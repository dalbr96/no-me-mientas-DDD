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

    private Dinero apuestaMaxima;
    private Map<JugadorId, Apuesta> turnos;
    private Boolean esActual;
    private List<Dado> dadosDestapados;


    public Etapa(EtapaId entityId, Dinero apuestaMaxima){
        super(entityId);
        this.apuestaMaxima = apuestaMaxima;
        this.turnos = new HashMap<>();
        this.esActual = Boolean.TRUE;
        this.dadosDestapados = new ArrayList<>();
    }

    public void agregarDados(List<Dado> dados){
        this.dadosDestapados = dados;
    }

    public void cambiarActual(){
        this.esActual = Boolean.FALSE;
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
