package org.example.domain.ronda;

import co.com.sofka.domain.generic.Entity;
import org.example.domain.juego.Jugador;
import org.example.domain.juego.values.Dinero;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.ronda.values.Apuesta;
import org.example.domain.ronda.values.EtapaId;

import java.util.Map;

public class Etapa extends Entity<EtapaId> {

    private final Map<JugadorId, Jugador> participantesEtapa;
    private Dinero apuestaMaxima;
    private Map<JugadorId, Apuesta> turnos;


    private Etapa(EtapaId entityId, Map<JugadorId, Jugador> participantesEtapa) {
        super(entityId);
        this.participantesEtapa = participantesEtapa;
    }

}
