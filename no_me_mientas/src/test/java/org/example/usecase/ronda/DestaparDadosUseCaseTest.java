package org.example.usecase.ronda;

import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.business.support.TriggeredEvent;
import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.juego.values.Dinero;
import org.example.domain.juego.values.DineroJugadores;
import org.example.domain.juego.values.JuegoId;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.ronda.Ronda;
import org.example.domain.ronda.events.DadosLanzados;
import org.example.domain.ronda.events.EtapaCreada;
import org.example.domain.ronda.events.RondaCreada;
import org.example.domain.ronda.values.Dado;
import org.example.domain.ronda.values.RondaId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DestaparDadosUseCaseTest {

    @Mock
    DomainEventRepository repository;

    @Test
    void destaparDadosUseCase(){

        var rondaId = RondaId.of("xxxx");

        var event = new EtapaCreada(400, List.of(JugadorId.of("xxx"), JugadorId.of("yyy")));
        event.setAggregateRootId(rondaId.value());

        var useCase = new DestaparDadosUseCase();

        when(repository.getEventsBy(event.aggregateRootId())).thenReturn(domainEventList());
        useCase.addRepository(repository);

        var events = UseCaseHandler.getInstance().setIdentifyExecutor(event.aggregateRootId())
                .syncExecutor(useCase, new TriggeredEvent<>(event)).orElseThrow().getDomainEvents();

        List<DomainEvent> eventos = new ArrayList<>(domainEventList());
        eventos.add(new EtapaCreada(400, List.of(JugadorId.of("xxx"), JugadorId.of("yyy"))));
        eventos.add(events.get(0));

        var ronda = Ronda.from(rondaId, eventos);

        Assertions.assertEquals(rondaId.value(), ronda.identity().value());
        Assertions.assertEquals(3, ronda.dados().stream().filter(dado -> dado.value().estaDestapado()).count());

    }

    @Test
    void destaparDadosUseCase_DosEtapas(){

        var rondaId = RondaId.of("xxxx");

        var event = new EtapaCreada(400, List.of(JugadorId.of("xxx"), JugadorId.of("yyy")));
        event.setAggregateRootId(rondaId.value());

        var useCase = new DestaparDadosUseCase();

        when(repository.getEventsBy(event.aggregateRootId())).thenReturn(domainEventList_DosEtapas());
        useCase.addRepository(repository);

        var events = UseCaseHandler.getInstance().setIdentifyExecutor(event.aggregateRootId())
                .syncExecutor(useCase, new TriggeredEvent<>(event)).orElseThrow().getDomainEvents();

        List<DomainEvent> eventos = new ArrayList<>(domainEventList_DosEtapas());
        eventos.add(new EtapaCreada(400, List.of(JugadorId.of("xxx"), JugadorId.of("yyy"))));
        eventos.add(events.get(0));

        var ronda = Ronda.from(rondaId, eventos);

        Assertions.assertEquals(rondaId.value(), ronda.identity().value());
        Assertions.assertEquals(5, ronda.dados().stream().filter(dado -> dado.value().estaDestapado()).count());

    }

    @Test
    void destaparDadosUseCase_TresEtapas(){

        var rondaId = RondaId.of("xxxx");

        var event = new EtapaCreada(400, List.of(JugadorId.of("xxx"), JugadorId.of("yyy")));
        event.setAggregateRootId(rondaId.value());

        var useCase = new DestaparDadosUseCase();

        when(repository.getEventsBy(event.aggregateRootId())).thenReturn(domainEventList_TresEtapas());
        useCase.addRepository(repository);

        var events = UseCaseHandler.getInstance().setIdentifyExecutor(event.aggregateRootId())
                .syncExecutor(useCase, new TriggeredEvent<>(event)).orElseThrow().getDomainEvents();

        List<DomainEvent> eventos = new ArrayList<>(domainEventList_TresEtapas());
        eventos.add(new EtapaCreada(400, List.of(JugadorId.of("xxx"), JugadorId.of("yyy"))));
        eventos.add(events.get(0));

        var ronda = Ronda.from(rondaId, eventos);

        Assertions.assertEquals(rondaId.value(), ronda.identity().value());
        Assertions.assertEquals(6, ronda.dados().stream().filter(dado -> dado.value().estaDestapado()).count());

    }

    private List<DomainEvent> domainEventList() {
        var rondaId = RondaId.of("xxx");
        var juegoId = JuegoId.of("xxx-j");

        var jugadores = List.of(
                new DineroJugadores(new Dinero(400), JugadorId.of("xxx-1")),
                new DineroJugadores(new Dinero(300), JugadorId.of("xxx-2"))
        );

        var dados = new ArrayList<Dado>();

        for(int i = 0; i < 6; i++){
            dados.add(new Dado());
        }
        return List.of(
                new RondaCreada(rondaId, juegoId, jugadores),
                new DadosLanzados(dados)
        );
    }
    private List<DomainEvent> domainEventList_DosEtapas() {

        var eventosDeDominio = new ArrayList<>(domainEventList());
        eventosDeDominio.add(new EtapaCreada(400, List.of(JugadorId.of("xxx-1"),JugadorId.of("xxx-2"))));

        return eventosDeDominio;
    }
    private List<DomainEvent> domainEventList_TresEtapas() {

        var eventosDeDominio = new ArrayList<>(domainEventList_DosEtapas());
        eventosDeDominio.add(new EtapaCreada(400, List.of(JugadorId.of("xxx-1"), JugadorId.of("xxx-2"))));

        return eventosDeDominio;
    }

    }
