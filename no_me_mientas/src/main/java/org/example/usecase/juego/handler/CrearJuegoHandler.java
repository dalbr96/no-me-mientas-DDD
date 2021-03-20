package org.example.usecase.juego.handler;


import co.com.sofka.business.annotation.CommandHandles;
import co.com.sofka.business.annotation.CommandType;
import co.com.sofka.business.asyn.UseCaseExecutor;
import co.com.sofka.business.support.RequestCommand;
import org.example.domain.juego.command.CrearJuego;
import org.example.domain.juego.values.Dinero;
import org.example.domain.juego.values.JuegoId;
import org.example.domain.juego.values.JugadorId;
import org.example.domain.juego.values.Name;
import org.example.usecase.juego.CrearJuegoUseCase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@CommandHandles
@CommandType(name = "no_me_mientas.juego.crear_juego", aggregate = "juego")
public class CrearJuegoHandler extends UseCaseExecutor {
    
    private RequestCommand<CrearJuego> request;
    
    @Override
    public void run() {
        runUseCase(new CrearJuegoUseCase(), request);
    }

    @Override
    public void accept(Map<String, String> args) {
        
        Map<JugadorId, Name> nombreMap = new HashMap<>();
        Map<JugadorId, Dinero> capitalMap = new HashMap<>();

        var ids = Objects.requireNonNull(args.get("jugadoresIds")).split(",");
        var nombres = Objects.requireNonNull(args.get("nombres")).split(",");
        var capitales = Objects.requireNonNull(args.get("capitales")).split(",");

        for(var i = 0; i <ids.length; i++){
            nombreMap.put(JugadorId.of(ids[i]), new Name(nombres[i]));
            capitalMap.put(JugadorId.of(ids[i]), new Dinero(Integer.parseInt(capitales[i])));
        }

        request = new RequestCommand<>(new CrearJuego(nombreMap, capitalMap, JuegoId.of(aggregateId())));
    }
}
