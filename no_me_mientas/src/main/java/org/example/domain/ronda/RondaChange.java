package org.example.domain.ronda;

import co.com.sofka.domain.generic.EventChange;
import org.example.domain.juego.values.Dinero;
import org.example.domain.ronda.events.*;
import org.example.domain.ronda.values.Dado;
import org.example.domain.ronda.values.EtapaId;
import org.example.domain.ronda.values.Puntaje;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collector;
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

        apply((DadoLanzado event) ->{
            for(int i = 0; i < 6; i++){
                ronda.dados.add(new Dado());
            }
        });

        apply((EtapaCreada event) ->{

            var capitales = event.getCapitales();

            Integer apuestaMaxima = capitales.values().stream()
                    .max(Comparator.comparing(Dinero::value)).get().value();

            //TODO: Cambiar esActual a cada etapa!!

            ronda.etapas.add(new Etapa(new EtapaId(), new Dinero(apuestaMaxima)));
        });

        apply((DadosDestapados event) -> {
            if(ronda.etapas.size() == 1){
                for(int i = 0; i < 3; i++){
                    ronda.dados().set(i, ronda.dados().get(i).destaparCara()) ;
                }
            }
            if(ronda.etapas.size() == 2){
                for(int i = 0; i < 5; i++){
                    ronda.dados().set(i, ronda.dados().get(i).destaparCara()) ;
                }
            }
            if(ronda.etapas.size() == 3){
                for(int i = 0; i <= 5; i++){
                    ronda.dados().set(i, ronda.dados().get(i).destaparCara()) ;
                }
            }
        });

        apply((DadosAsignadosAEtapa event) -> {
            var dados = ronda.dados().stream().filter(dado -> dado.value().estaDestapado()).collect(Collectors.toList());
            ronda.etapas.iterator().next().agregarDados(dados);
        });
    }
}
