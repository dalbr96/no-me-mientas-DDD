package org.example.usecase.ronda;

import co.com.sofka.business.annotation.EventListener;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.business.support.TriggeredEvent;
import org.example.domain.ronda.Ronda;
import org.example.domain.ronda.events.RondaCreada;

@EventListener(eventType = "nomemientas.ronda.RondaCreada")
public class LanzarDadoUseCase extends UseCase<TriggeredEvent<RondaCreada>, ResponseEvents> {

    @Override
    public void executeUseCase(TriggeredEvent<RondaCreada> lanzarDadosRequestCommand) {

        var event = lanzarDadosRequestCommand.getDomainEvent();

        var ronda = Ronda.from(event.getRondaId(), retrieveEvents());

        ronda.lanzarDados();

        var commit = ronda.getUncommittedChanges();
        emit().onResponse(new ResponseEvents(commit));

    }
}
