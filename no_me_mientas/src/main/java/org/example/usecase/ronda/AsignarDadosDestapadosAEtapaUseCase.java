package org.example.usecase.ronda;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.business.support.TriggeredEvent;
import org.example.domain.ronda.Ronda;
import org.example.domain.ronda.events.DadosDestapados;
import org.example.domain.ronda.values.RondaId;

public class AsignarDadosDestapadosAEtapaUseCase extends UseCase<TriggeredEvent<DadosDestapados>, ResponseEvents> {
    @Override
    public void executeUseCase(TriggeredEvent<DadosDestapados> dadosDestapadosTriggeredEvent) {
        var event = dadosDestapadosTriggeredEvent.getDomainEvent();

        var rondaId = RondaId.of(event.aggregateRootId());

        var ronda = Ronda.from(rondaId, retrieveEvents());

        ronda.asignarDadosAEtapa();

        var commit = ronda.getUncommittedChanges();

        emit().onResponse(new ResponseEvents(commit));
    }
}
