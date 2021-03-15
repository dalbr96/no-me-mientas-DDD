package org.example.usecase.ronda;

import co.com.sofka.business.generic.BusinessException;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import org.example.domain.ronda.Ronda;
import org.example.domain.ronda.command.CrearEtapa;

public class CrearEtapaUseCase extends UseCase<RequestCommand<CrearEtapa>, ResponseEvents> {

    public static final int MINIMO_JUGADORES = 2;
    public static final int ETAPAS_MAXIMAS = 3;

    @Override
    public void executeUseCase(RequestCommand<CrearEtapa> crearEtapaRequestCommand) {

        var command = crearEtapaRequestCommand.getCommand();
        var rondaId = command.getRondaId();
        var ronda = Ronda.from(rondaId, retrieveEvents());

        if(ronda.jugadoresRonda().size() < MINIMO_JUGADORES){
            throw new BusinessException(rondaId.value(), "No se puede crear una Etapa con solo un jugador");
        }

        if(ronda.etapas().size() >= ETAPAS_MAXIMAS){
            throw new BusinessException(rondaId.value(), "El Máximo numero de etapas es 3");
        }

        ronda.crearEtapa();

        var commits = ronda.getUncommittedChanges();

        emit().onResponse(new ResponseEvents(commits));
    }
}
