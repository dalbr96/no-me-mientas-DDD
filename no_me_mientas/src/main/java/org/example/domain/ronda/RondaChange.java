package org.example.domain.ronda;

import co.com.sofka.domain.generic.EventChange;
import org.example.domain.juego.values.Dinero;
import org.example.domain.ronda.events.*;
import org.example.domain.ronda.values.Dado;
import org.example.domain.ronda.values.EtapaId;
import org.example.domain.ronda.values.Puntaje;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class RondaChange extends EventChange {

    public RondaChange(Ronda ronda){

        apply((RondaCreada event) ->{
            ronda.jugadoresRonda = event.getJugadoresRonda();
            ronda.capitalJugadores = event.getCapitalJugadores();
            ronda.puntajes = new HashMap<>();
            ronda.jugadoresRonda.forEach((jugadorId) -> {
                ronda.puntajes.put(jugadorId, new Puntaje(0));
            });
            ronda.capitalAcumulado = new Dinero(0);
            ronda.dados = new ArrayList<>();
            ronda.etapas = new HashSet<>();
            ronda.juegoId = event.getJuegoId();
        });

        apply(dadoslanzados(ronda));

        apply(etapaCreada(ronda));

        apply(dadosDestapados(ronda));

        apply(dadosAsignadosAEtapa(ronda));

        apply(turnosAsignados(ronda));

        apply(apuestaDeJugadorAsignada(ronda));

        apply((JugadorEliminado event) -> {
            var jugadorId = event.getJugadorId();
            var jugadores = new ArrayList<>(ronda.jugadoresRonda());
            jugadores.remove(jugadorId);
            ronda.jugadoresRonda = jugadores;
        });

    }

    private Consumer<ApuestaAsignada> apuestaDeJugadorAsignada(Ronda ronda) {
        return (ApuestaAsignada event) -> {
            var jugadorId = event.getJugadorId();
            var apuesta = event.getApuesta();

            ronda.etapas.stream().filter(Etapa::esActual).findFirst().get().asignarApuesta(jugadorId, apuesta);
        };
    }

    private Consumer<TurnosAsignados> turnosAsignados(Ronda ronda) {
        return (TurnosAsignados event) -> {
            var ordenApuestas = ronda.etapas.iterator().next().orden();
            Collections.shuffle(ordenApuestas);
            ronda.etapas.stream().filter(Etapa::esActual).findFirst().get().asignarOrden(ordenApuestas);
        };
    }

    private Consumer<DadosAsignadosAEtapa> dadosAsignadosAEtapa(Ronda ronda) {
        return (DadosAsignadosAEtapa event) -> {
            var dados = ronda.dados().stream().filter(dado -> dado.value().estaDestapado()).collect(Collectors.toList());
            ronda.etapas.stream().filter(Etapa::esActual).findAny().get().agregarDados(dados);
        };
    }

    private Consumer<DadosDestapados> dadosDestapados(Ronda ronda) {
        return (DadosDestapados event) -> {
            if (isEtapa(ronda, 1)) {
                for (int i = 0; i < 3; i++) {
                    ronda.dados.set(i, ronda.dados.get(i).destaparCara());
                }
            }
            if (isEtapa(ronda, 2)) {
                for (int i = 0; i < 5; i++) {
                    ronda.dados.set(i, ronda.dados.get(i).destaparCara());
                }
            }
            if (isEtapa(ronda, 3)) {
                for (int i = 0; i <= 5; i++) {
                    ronda.dados.set(i, ronda.dados.get(i).destaparCara());
                }
            }
        };
    }

    private boolean isEtapa(Ronda ronda, int numeroEtapa) {
        return ronda.etapas.size() == numeroEtapa;
    }

    private Consumer<EtapaCreada> etapaCreada(Ronda ronda) {
        return (EtapaCreada event) -> {

            var apuestaMaxima = event.getApuestaMaxima();
            var jugadoresEtapa = event.getJugadoresEtapa();

            ronda.etapas.forEach(Etapa::cambiarActual);
            ronda.etapas.add(new Etapa(new EtapaId(), new Dinero(apuestaMaxima), jugadoresEtapa));
        };
    }

    private Consumer<DadoLanzado> dadoslanzados(Ronda ronda) {
        return (DadoLanzado event) -> {
            for (int i = 0; i < 6; i++) {
                ronda.dados.add(new Dado());
            }
        };
    }
}
