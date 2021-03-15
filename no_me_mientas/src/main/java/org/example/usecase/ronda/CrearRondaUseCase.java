package org.example.usecase.ronda;

import co.com.sofka.business.generic.BusinessException;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.business.support.TriggeredEvent;
import org.example.domain.juego.events.RondaIniciada;
import org.example.domain.juego.values.JuegoId;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.ronda.Ronda;

import java.util.List;


public class CrearRondaUseCase extends UseCase<TriggeredEvent<RondaIniciada>, ResponseEvents> {

    public static final int MINIMO_JUGADORES = 2;

    @Override
    public void executeUseCase(TriggeredEvent<RondaIniciada> rondaIniciadaTriggeredEvent) {
        var event = rondaIniciadaTriggeredEvent.getDomainEvent();
        var rondaId = event.getRondaId();
        var juegoId = JuegoId.of(event.aggregateRootId());
        List<JugadorId> jugadores = event.getJugadoresIds();
        var capitales = event.getCapitales();

        if(event.getJugadoresIds().size() < MINIMO_JUGADORES){
            throw new BusinessException(rondaId.value(), "No se puede iniciar ronda por falta de jugadores");
        }

        var ronda = new Ronda(rondaId, juegoId, jugadores, capitales);


        var commits = ronda.getUncommittedChanges();

        emit().onResponse(new ResponseEvents(commits));
    }
}
