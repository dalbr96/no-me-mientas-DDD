package org.example.usecase.ronda;

import co.com.sofka.business.generic.BusinessException;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import org.example.domain.ronda.Ronda;
import org.example.domain.ronda.command.CrearEtapa;

public class CrearEtapaUseCase extends UseCase<RequestCommand<CrearEtapa>, ResponseEvents> {

    @Override
    public void executeUseCase(RequestCommand<CrearEtapa> crearEtapaRequestCommand) {

        var command = crearEtapaRequestCommand.getCommand();
        var rondaId = command.getRondaId();
        var ronda = Ronda.from(rondaId, retrieveEvents());

        if(ronda.jugadoresRonda().size() < 2){
            throw new BusinessException(rondaId.value(), "No se puede crear una Etapa con solo un jugador");
        }

        if(ronda.etapas().size() >= 3){
            throw new BusinessException(rondaId.value(), "El Máximo numero de etapas es 3");
        }

        ronda.crearEtapa();

        var commits = ronda.getUncommittedChanges();

        emit().onResponse(new ResponseEvents(commits));
    }
}
