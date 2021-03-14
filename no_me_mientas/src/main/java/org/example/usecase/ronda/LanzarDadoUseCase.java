package org.example.usecase.ronda;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import org.example.domain.ronda.Ronda;
import org.example.domain.ronda.command.LanzarDados;


public class LanzarDadoUseCase extends UseCase<RequestCommand<LanzarDados>, ResponseEvents> {

    @Override
    public void executeUseCase(RequestCommand<LanzarDados> lanzarDadosRequestCommand) {

        var command = lanzarDadosRequestCommand.getCommand();

        var ronda = Ronda.from(command.getRondaId(), retrieveEvents());

        ronda.lanzarDados();

        var commit = ronda.getUncommittedChanges();
        emit().onResponse(new ResponseEvents(commit));

    }
}
