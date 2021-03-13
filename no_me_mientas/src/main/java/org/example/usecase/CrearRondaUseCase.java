package org.example.usecase;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import org.example.domain.ronda.Ronda;
import org.example.domain.ronda.command.CrearRonda;
import org.example.domain.ronda.values.RondaId;

public class CrearRondaUseCase extends UseCase<RequestCommand<CrearRonda>, ResponseEvents> {

    @Override
    public void executeUseCase(RequestCommand<CrearRonda> crearRondaRequestCommand) {

        var command = crearRondaRequestCommand.getCommand();
        var rondaId = new RondaId();

        var ronda = new Ronda(rondaId, command.getJugadores());

        var commits = ronda.getUncommittedChanges();

        emit().onResponse(new ResponseEvents(commits));

    }
}
