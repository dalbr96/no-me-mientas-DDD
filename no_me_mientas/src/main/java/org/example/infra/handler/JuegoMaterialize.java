package org.example.infra.handler;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.juego.events.JuegoCreado;
import org.example.domain.juego.events.JuegoIniciado;
import org.example.domain.juego.events.JugadorAgregado;
import org.example.domain.juego.events.RondaIniciada;
import org.springframework.context.event.EventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Component
public class JuegoMaterialize {

    private static final String COLLECTION_NAME = "juegos";
    private static final Logger logger = Logger.getLogger(JuegoMaterialize.class.getName());

    @Autowired
    @Qualifier("mongoTemplateQueries")
    private MongoTemplate mongoTemplate;

    @Async
    @EventListener
    public void handleEventJuegoCreado(JuegoCreado juegoCreado){
        logger.info("***** Handle event JuegoCreado");
        Map<String, Object> data = new HashMap<>();
        data.put("_id", juegoCreado.getJuegoId().value());
        data.put("isJuegoInicializado", false);
        mongoTemplate.save(data, COLLECTION_NAME);
    }

    @Async
    @EventListener
    public void handleEventJugadorAgregado(JugadorAgregado jugadorAgregado){
        logger.info("Handle event JugadorAdicionado");
        Update update = new Update();
        var id = jugadorAgregado.getJugadorId().value();
        update.set("jugadores."+id+".capital", jugadorAgregado.getNombre().value());
        update.set("jugadores."+id+".nombre", jugadorAgregado.getCapital().value());

        mongoTemplate.updateFirst(getFilterByAggregateId(jugadorAgregado), update, COLLECTION_NAME);
    }

    @Async
    @EventListener
    public void handleEventJuegoIniciado(JuegoIniciado juegoIniciado){
        logger.info("Hande event Juego Iniciado");
        Update update = new Update();
        update.set("isJuegoInicializado", true);
        mongoTemplate.updateFirst(getFilterByAggregateId(juegoIniciado), update, COLLECTION_NAME);
    }

    @Async
    @EventListener
    public void handleEventRondaIniciada(RondaIniciada rondaIniciada){
        logger.info("Handle event Ronda Iniciada");
        Update update = new Update();
        update.set("rondaId", rondaIniciada.getRondaId());
        mongoTemplate.updateFirst(getFilterByAggregateId(rondaIniciada), update, COLLECTION_NAME);
    }

    private Query getFilterByAggregateId(DomainEvent event) {
        return new Query(
                Criteria.where("_id").is(event.aggregateRootId())
        );
    }

}
