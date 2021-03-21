package org.example.usecase.juego.handler;

import co.com.sofka.business.annotation.CommandHandles;
import co.com.sofka.business.annotation.CommandType;
import co.com.sofka.business.asyn.UseCaseExecutor;
import co.com.sofka.business.support.RequestCommand;
import org.example.domain.juego.command.IniciarRonda;
import org.example.domain.juego.values.JuegoId;
import org.example.usecase.juego.IniciarRondaUseCase;

import java.util.Map;

@CommandHandles
@CommandType(name = "no_me_mientas.juego.iniciar_ronda", aggregate = "juego")
public class IniciarRondaHandler extends UseCaseExecutor {

    private RequestCommand<IniciarRonda> request;

    @Override
    public void run() {
        runUseCase(new IniciarRondaUseCase(), request);
    }

    @Override
    public void accept(Map<String, String> args) {
        var command = new IniciarRonda(JuegoId.of(aggregateId()));
        request = new RequestCommand<>(command);
    }
}
