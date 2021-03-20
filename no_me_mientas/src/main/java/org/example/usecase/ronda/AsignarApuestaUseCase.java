package org.example.usecase.ronda;

import co.com.sofka.business.generic.BusinessException;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.ronda.Etapa;
import org.example.domain.ronda.Ronda;
import org.example.domain.ronda.command.AsignarApuesta;
import org.example.domain.ronda.values.Apuesta;
import org.example.domain.ronda.values.RondaId;

public class AsignarApuestaUseCase extends UseCase<RequestCommand<AsignarApuesta>, ResponseEvents> {
    @Override
    public void executeUseCase(RequestCommand<AsignarApuesta> asignarApuestaRequestCommand) {

        var command = asignarApuestaRequestCommand.getCommand();
        var rondaId = command.getRondaId();
        var jugadorId = command.getJugadorId();
        var apuesta = command.getApuesta();

        Ronda ronda = Ronda.from(rondaId, retrieveEvents());

        verificarJugadorEnEtapa(rondaId, jugadorId, ronda);

        verificarApuestaEsMenorAApuestaMaxima(rondaId, apuesta, ronda);

        verificarFondosJugador(rondaId, jugadorId, apuesta, ronda);

        verificarAdivinanza(rondaId, apuesta, ronda);

        ronda.asignarApuesta(jugadorId, apuesta);

        var uncommitedEvents = ronda.getUncommittedChanges();

        emit().onResponse(new ResponseEvents(uncommitedEvents));
    }

    private void verificarAdivinanza(RondaId rondaId, Apuesta apuesta, Ronda ronda) {
        ronda.etapas().stream().filter(Etapa::esActual).findFirst().get().turnos().entrySet().forEach(entry -> {
            if(entry.getValue().value().adivinanzaRealizada().equals(apuesta.value().adivinanzaRealizada())){
                throw new BusinessException(rondaId.value(),
                        "No se puede asignar la apuesta porque alguien ya escogió esa adivinanza");
            }});
    }

    private void verificarFondosJugador(RondaId rondaId, JugadorId jugadorId, Apuesta apuesta, Ronda ronda) {
        if(ronda.capitales().get(jugadorId).value() < apuesta.value().dineroApostado().value()){
            throw new BusinessException(rondaId.value(), "El jugador no cuenta con fondos suficientes");
        }
    }

    private void verificarApuestaEsMenorAApuestaMaxima(RondaId rondaId, Apuesta apuesta, Ronda ronda) {
        if(ronda.etapas().stream().filter(Etapa::esActual).findFirst().get().apuestaMaxima().value() < apuesta.value().dineroApostado().value()){
            throw new BusinessException(rondaId.value(), "El dinero apostado sobrepasa la apuesta maxima.");
        }
    }

    private void verificarJugadorEnEtapa(RondaId rondaId, JugadorId jugadorId, Ronda ronda) {
        if(!ronda.etapas().stream().filter(Etapa::esActual).findFirst().get().orden().contains(jugadorId)){
            throw new BusinessException(rondaId.value(), "El Jugador no hace parte de la etapa");
        }
    }
}
