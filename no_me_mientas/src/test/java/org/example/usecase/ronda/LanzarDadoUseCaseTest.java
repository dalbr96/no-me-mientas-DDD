package org.example.usecase.ronda;


import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.juego.values.Dinero;
import org.example.domain.juego.values.JuegoId;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.ronda.Ronda;
import org.example.domain.ronda.command.LanzarDados;
import org.example.domain.ronda.events.RondaCreada;
import org.example.domain.ronda.values.Dado;
import org.example.domain.ronda.values.RondaId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LanzarDadoUseCaseTest {

    @Mock
    DomainEventRepository repository;
    @Test
    void lanzarDadosTest(){
        var rondaId = RondaId.of("xxx");
        var command = new LanzarDados(rondaId);
        var useCase = new LanzarDadoUseCase();

        when(repository.getEventsBy(rondaId.value())).thenReturn(domainEvents());

        useCase.addRepository(repository);

        var events = UseCaseHandler.getInstance().setIdentifyExecutor(rondaId.value())
                .syncExecutor(useCase, new RequestCommand<>(command)).orElseThrow().getDomainEvents();

        List<DomainEvent> eventList = new ArrayList<>(domainEvents());
        eventList.add(events.get(0));

        var rondaReconstruida = Ronda.from(rondaId, eventList);

        Assertions.assertEquals(6, rondaReconstruida.dados().size());
        for(Dado dado : rondaReconstruida.dados()){
            Assertions.assertTrue(dado.value().cara() > 0 && dado.value().cara() < 7);
        }

    }

    private List<DomainEvent> domainEvents() {

        var rondaId = RondaId.of("xxx");
        var juegoId = JuegoId.of("xxx-j");

        var jugadores = List.of(
                JugadorId.of("xxx-1"),
                JugadorId.of("xxx-2")
        );

        var capitales = Map.of(
                JugadorId.of("xxx-1"), new Dinero(400),
                JugadorId.of("xxx-2"), new Dinero(300)
        );

        return List.of(
                new RondaCreada(rondaId, juegoId, jugadores, capitales)
        );
    }

}