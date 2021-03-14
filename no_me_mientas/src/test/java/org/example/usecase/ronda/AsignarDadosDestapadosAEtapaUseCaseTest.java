package org.example.usecase.ronda;

import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.business.support.TriggeredEvent;
import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.juego.values.Dinero;
import org.example.domain.juego.values.JuegoId;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.ronda.Ronda;
import org.example.domain.ronda.events.DadoLanzado;
import org.example.domain.ronda.events.DadosDestapados;
import org.example.domain.ronda.events.EtapaCreada;
import org.example.domain.ronda.events.RondaCreada;
import org.example.domain.ronda.values.RondaId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AsignarDadosDestapadosAEtapaUseCaseTest {

    @Mock
    DomainEventRepository repository;

    @Test
    void asignarDadosAEtapaTest(){

        var rondaId = RondaId.of("xxxx");
        var evento = new DadosDestapados();
        var useCase = new AsignarDadosDestapadosAEtapaUseCase();
        evento.setAggregateRootId(rondaId.value());

        when(repository.getEventsBy(rondaId.value())).thenReturn(eventList());
        useCase.addRepository(repository);

        var events = UseCaseHandler.getInstance().setIdentifyExecutor(rondaId.value())
                .syncExecutor(useCase, new TriggeredEvent<>(evento)).orElseThrow().getDomainEvents();

        var listaEventos = new ArrayList<DomainEvent>(eventList());
        listaEventos.add(evento);
        listaEventos.add(events.get(0));

        var ronda = Ronda.from(rondaId, listaEventos);

        Assertions.assertEquals(3, ronda.etapas().iterator().next().dadosDestapados().size());
    }

    @Test
    void asignarDadosAEtapaTest_DosEtapas(){

        var rondaId = RondaId.of("xxxx");
        var evento = new DadosDestapados();
        var useCase = new AsignarDadosDestapadosAEtapaUseCase();
        evento.setAggregateRootId(rondaId.value());

        when(repository.getEventsBy(rondaId.value())).thenReturn(eventList_DosEtapas());
        useCase.addRepository(repository);

        var events = UseCaseHandler.getInstance().setIdentifyExecutor(rondaId.value())
                .syncExecutor(useCase, new TriggeredEvent<>(evento)).orElseThrow().getDomainEvents();

        var listaEventos = new ArrayList<DomainEvent>(eventList_DosEtapas());
        listaEventos.add(evento);
        listaEventos.add(events.get(0));

        var ronda = Ronda.from(rondaId, listaEventos);

        Assertions.assertEquals(5, ronda.etapas().iterator().next().dadosDestapados().size());
    }

    @Test
    void asignarDadosAEtapaTest_TresEtapas(){

        var rondaId = RondaId.of("xxxx");
        var evento = new DadosDestapados();
        var useCase = new AsignarDadosDestapadosAEtapaUseCase();
        evento.setAggregateRootId(rondaId.value());

        when(repository.getEventsBy(rondaId.value())).thenReturn(eventList_TresEtapas());
        useCase.addRepository(repository);

        var events = UseCaseHandler.getInstance().setIdentifyExecutor(rondaId.value())
                .syncExecutor(useCase, new TriggeredEvent<>(evento)).orElseThrow().getDomainEvents();

        var listaEventos = new ArrayList<DomainEvent>(eventList_TresEtapas());
        listaEventos.add(evento);
        listaEventos.add(events.get(0));

        var ronda = Ronda.from(rondaId, listaEventos);

        Assertions.assertEquals(6, ronda.etapas().iterator().next().dadosDestapados().size());
    }

    private List<DomainEvent> eventList() {

        var rondaId = RondaId.of("xxx");
        var juegoId = JuegoId.of("xxx-j");

        var jugadores = Set.of(
                JugadorId.of("xxx-1"),
                JugadorId.of("xxx-2")
        );

        var capitales = Map.of(
                JugadorId.of("xxx-1"), new Dinero(400),
                JugadorId.of("xxx-2"), new Dinero(300)
        );

        return List.of(
                new RondaCreada(rondaId, juegoId, jugadores, capitales),
                new DadoLanzado(),
                new EtapaCreada(capitales)
        );
    }

    private List<DomainEvent> eventList_DosEtapas() {

        var capitalesSegundaEtapa = Map.of(
                JugadorId.of("xxx-1"), new Dinero(500),
                JugadorId.of("xxx-2"), new Dinero(200)
        );

        var eventos_DosEtapas = new ArrayList<>(eventList());
        eventos_DosEtapas.add(new EtapaCreada(capitalesSegundaEtapa));
        return eventos_DosEtapas;
    }

    private List<DomainEvent> eventList_TresEtapas() {

        var capitales = Map.of(
                JugadorId.of("xxx-1"), new Dinero(400),
                JugadorId.of("xxx-2"), new Dinero(300)
        );

        var eventos_TresEtapas = new ArrayList<>(eventList_DosEtapas());
        eventos_TresEtapas.add(new EtapaCreada(capitales));
        return eventos_TresEtapas;
    }
}