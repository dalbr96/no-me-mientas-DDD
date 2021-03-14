package org.example.domain.ronda;

import co.com.sofka.domain.generic.Entity;
import org.example.domain.juego.Jugador;
import org.example.domain.juego.values.Dinero;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.ronda.values.Apuesta;
import org.example.domain.ronda.values.EtapaId;

import java.util.HashMap;
import java.util.Map;

public class Etapa extends Entity<EtapaId> {

    private Dinero apuestaMaxima;
    private Map<JugadorId, Apuesta> turnos;
    private Boolean esActual;


    public Etapa(EtapaId entityId, Dinero apuestaMaxima){
        super(entityId);
        this.apuestaMaxima = apuestaMaxima;
        this.turnos = new HashMap<>();
        this.esActual = Boolean.TRUE;
    }

    public Dinero getApuestaMaxima() {
        return apuestaMaxima;
    }

    public Map<JugadorId, Apuesta> getTurnos() {
        return turnos;
    }

    public Boolean getEsActual() {
        return esActual;
    }


}
