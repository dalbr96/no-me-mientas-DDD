package org.example.usecase.juego;

import co.com.sofka.business.generic.BusinessException;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import org.example.domain.juego.Juego;
import org.example.domain.juego.command.CrearJuego;
import org.example.domain.juego.factory.JugadorFactory;
import org.example.domain.juego.values.JuegoId;

public class CrearJuegoUseCase extends UseCase<RequestCommand<CrearJuego>, ResponseEvents> {

    @Override
    public void executeUseCase(RequestCommand<CrearJuego> crearJuegoRequestCommand) {

        var command = crearJuegoRequestCommand.getCommand();

        var factory = JugadorFactory.builder();

        command.getNombres().forEach(
                (jugadorId, nombre) -> factory.nuevoJugador(jugadorId,
                        nombre,
                        command.getCapitales().get(jugadorId))
        );

        var juego = new Juego(command.getJuegoId(), factory);

        var commits = juego.getUncommittedChanges();

        emit().onResponse(new ResponseEvents(commits));
    }
}
