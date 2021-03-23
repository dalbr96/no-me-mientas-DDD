package org.example.usecase.ronda;

import co.com.sofka.business.generic.BusinessException;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.juego.values.Dinero;
import org.example.domain.ronda.values.CapitalesJugadoresRonda;
import org.example.domain.juego.values.JuegoId;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.ronda.Etapa;
import org.example.domain.ronda.Ronda;
import org.example.domain.ronda.command.AsignarApuesta;
import org.example.domain.ronda.events.ApuestaAsignada;
import org.example.domain.ronda.events.DadosLanzados;
import org.example.domain.ronda.events.EtapaCreada;
import org.example.domain.ronda.events.RondaCreada;
import org.example.domain.ronda.values.Adivinanza;
import org.example.domain.ronda.values.Apuesta;
import org.example.domain.ronda.values.Dado;
import org.example.domain.ronda.values.RondaId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AsignarApuestaUseCaseTest {

    @Mock
    DomainEventRepository repository;

    @Test
    void asignarApuesta(){

        JugadorId jugadorId = JugadorId.of("xxx");
        Apuesta apuesta = new Apuesta(new Dinero(200), new Adivinanza(3, 3));
        RondaId rondaId = RondaId.of("ppp");
        var command = new AsignarApuesta(rondaId, jugadorId, apuesta);
        var useCase = new AsignarApuestaUseCase();

        when(repository.getEventsBy(rondaId.value())).thenReturn(domainList());
        useCase.addRepository(repository);

        var events = UseCaseHandler.getInstance().setIdentifyExecutor(rondaId.value())
                .syncExecutor(useCase, new RequestCommand<>(command)).orElseThrow().getDomainEvents();

        var domainEvents = new ArrayList<>(domainList());
        domainEvents.add(events.get(0));

        Ronda rondaConApuesta = Ronda.from(rondaId, domainEvents);

        var apuestaRealizada = rondaConApuesta.etapas().stream().filter(Etapa::esActual).findFirst().get().turnos().get(jugadorId);
        var dineroApostado = apuestaRealizada.value().dineroApostado();
        var adivinanzaRealizada = apuestaRealizada.value().adivinanzaRealizada();

        Assertions.assertEquals(200, dineroApostado.value());
        Assertions.assertEquals(3, adivinanzaRealizada.value().carasRepetidas());
        Assertions.assertEquals(3, adivinanzaRealizada.value().cara());

    }

    @Test
    void asignarApuesta_ErrorEsperado(){

        JugadorId jugadorId = JugadorId.of("xxx-2");
        Apuesta apuesta = new Apuesta(new Dinero(200), new Adivinanza(3, 3));
        RondaId rondaId = RondaId.of("ppp");
        var command = new AsignarApuesta(rondaId, jugadorId, apuesta);
        var useCase = new AsignarApuestaUseCase();

        when(repository.getEventsBy(rondaId.value())).thenReturn(domainList());
        useCase.addRepository(repository);

        Assertions.assertThrows(BusinessException.class, () ->{
            UseCaseHandler.getInstance().setIdentifyExecutor(rondaId.value())
                    .syncExecutor(useCase, new RequestCommand<>(command)).orElseThrow().getDomainEvents();
        });

    }

    @Test
    void asignarApuesta_ErrorApuestaMaxima(){

        JugadorId jugadorId = JugadorId.of("xxx");
        Apuesta apuesta = new Apuesta(new Dinero(1000), new Adivinanza(3, 3));
        RondaId rondaId = RondaId.of("ppp");
        var command = new AsignarApuesta(rondaId, jugadorId, apuesta);
        var useCase = new AsignarApuestaUseCase();

        when(repository.getEventsBy(rondaId.value())).thenReturn(domainList());
        useCase.addRepository(repository);

        Assertions.assertThrows(BusinessException.class, () ->{
            UseCaseHandler.getInstance().setIdentifyExecutor(rondaId.value())
                    .syncExecutor(useCase, new RequestCommand<>(command)).orElseThrow().getDomainEvents();
        });

    }

    @Test
    void asignarApuesta_ErrorFondosJugador(){

        JugadorId jugadorId = JugadorId.of("xxx");
        Apuesta apuesta = new Apuesta(new Dinero(600), new Adivinanza(3, 3));
        RondaId rondaId = RondaId.of("ppp");
        var command = new AsignarApuesta(rondaId, jugadorId, apuesta);
        var useCase = new AsignarApuestaUseCase();

        when(repository.getEventsBy(rondaId.value())).thenReturn(domainList());
        useCase.addRepository(repository);

        Assertions.assertThrows(BusinessException.class, () ->{
            UseCaseHandler.getInstance().setIdentifyExecutor(rondaId.value())
                    .syncExecutor(useCase, new RequestCommand<>(command)).orElseThrow().getDomainEvents();
        });

    }

    @Test
    void asignarApuesta_ErrorApuestaRepetida(){

        JugadorId jugadorId = JugadorId.of("aaa");
        Apuesta apuesta = new Apuesta(new Dinero(100), new Adivinanza(5, 3));
        RondaId rondaId = RondaId.of("ppp");
        var command = new AsignarApuesta(rondaId, jugadorId, apuesta);
        var useCase = new AsignarApuestaUseCase();

        when(repository.getEventsBy(rondaId.value())).thenReturn(domainEvents_ErrorApuestaRepetida());
        useCase.addRepository(repository);

        Assertions.assertThrows(BusinessException.class, () ->{
            UseCaseHandler.getInstance().setIdentifyExecutor(rondaId.value())
                    .syncExecutor(useCase, new RequestCommand<>(command)).orElseThrow().getDomainEvents();
        });

    }

    private List<DomainEvent> domainList() {
        var rondaId = RondaId.of("ppp");
        var juegoId = JuegoId.of("xxx-j");

        var jugadoresRonda = List.of(
                JugadorId.of("xxx"),
                JugadorId.of("yyy"),
                JugadorId.of("zzz"),
                JugadorId.of("aaa"),
                JugadorId.of("bbb")
        );

        var capitales = Map.of(
                JugadorId.of("xxx"), new Dinero(400),
                JugadorId.of("yyy"), new Dinero(300),
                JugadorId.of("zzz"), new Dinero(700),
                JugadorId.of("aaa"), new Dinero(900),
                JugadorId.of("bbb"), new Dinero(800)
        );

        var jugadores = List.of(
                new CapitalesJugadoresRonda(new Dinero(400), JugadorId.of("xxx")),
                new CapitalesJugadoresRonda(new Dinero(300), JugadorId.of("yyy")),
                new CapitalesJugadoresRonda(new Dinero(700), JugadorId.of("zzz")),
                new CapitalesJugadoresRonda(new Dinero(900), JugadorId.of("aaa")),
                new CapitalesJugadoresRonda(new Dinero(800), JugadorId.of("bbb"))
        );

        var dados = new ArrayList<Dado>();

        for(int i = 0; i < 6; i++){
            dados.add(new Dado());
        }

        return List.of(
                new RondaCreada(rondaId, juegoId, jugadores),
                new DadosLanzados(dados),
                new EtapaCreada(900, jugadoresRonda)
        );
    }

    private List<DomainEvent> domainEvents_ErrorApuestaRepetida(){
        var events = new ArrayList<>(domainList());
        events.add(new ApuestaAsignada(JugadorId.of("xxx"),
                new Apuesta(new Dinero(200), new Adivinanza(3, 3))));
        events.add(new ApuestaAsignada(JugadorId.of("yyy"),
                new Apuesta(new Dinero(200), new Adivinanza(4, 3))));
        events.add(new ApuestaAsignada(JugadorId.of("zzz"),
                new Apuesta(new Dinero(200), new Adivinanza(5, 3))));

        return events;
    }

}