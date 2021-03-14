package org.example.usecase.ronda;

import co.com.sofka.business.generic.BusinessException;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.business.support.TriggeredEvent;
import org.example.domain.ronda.Ronda;
import org.example.domain.ronda.events.EtapaCreada;
import org.example.domain.ronda.values.RondaId;

public class DestaparDadosUseCase extends UseCase<TriggeredEvent<EtapaCreada>, ResponseEvents> {
    @Override
    public void executeUseCase(TriggeredEvent<EtapaCreada> crearEtapaTriggeredEvent) {

        var event = crearEtapaTriggeredEvent.getDomainEvent();

        var rondaId = RondaId.of(event.aggregateRootId());

        var ronda = Ronda.from(rondaId, retrieveEvents());

        ronda.destaparDados();

        var commit = ronda.getUncommittedChanges();

        emit().onResponse(new ResponseEvents(commit));

    }
}
