package org.example.usecase.ronda;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.business.support.TriggeredEvent;
import org.example.domain.ronda.Ronda;
import org.example.domain.ronda.events.EtapaCreada;
import org.example.domain.ronda.values.RondaId;

public class AsignarOrdenUseCase extends UseCase<TriggeredEvent<EtapaCreada>, ResponseEvents> {

    @Override
    public void executeUseCase(TriggeredEvent<EtapaCreada> etapaCreadaTriggeredEvent) {

        var event = etapaCreadaTriggeredEvent.getDomainEvent();

        var rondaId = RondaId.of(event.aggregateRootId());

        var ronda = Ronda.from(rondaId, retrieveEvents());

        ronda.asignarTurnos();

        var uncommitedEvents = ronda.getUncommittedChanges();

        emit().onResponse(new ResponseEvents(uncommitedEvents));

    }
}
