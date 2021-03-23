package org.example.infra.handler;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.ronda.events.DadosLanzados;
import org.example.domain.ronda.events.RondaCreada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
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
public class RondaMaterialize {

    private static final String COLLECTION_NAME = "rondas";
    private static final Logger logger = Logger.getLogger(RondaMaterialize.class.getName());


    @Autowired
    @Qualifier("mongoTemplateQueries")
    private MongoTemplate mongoTemplate;

    @Async
    @EventListener
    public void handleEventRondaCreada(RondaCreada rondaCreada){
        logger.info("***** Handle event RondaCreado");
        Map<String, Object> data = new HashMap<>();
        data.put("_id", rondaCreada.getRondaId().value());
        data.put("juegoId", rondaCreada.getJuegoId().value());
        data.put("capitales", rondaCreada.getJugadoresRonda());
        mongoTemplate.save(data, COLLECTION_NAME);
    }

    @Async
    @EventListener
    public void handleEventDadosLanzados(DadosLanzados dadosLanzados){
        logger.info("***** Handle event DadoLanzado");
        Update update = new Update();
        update.set("dados", dadosLanzados.getDados());
        mongoTemplate.updateFirst(getFilterByAggregateId(dadosLanzados), update, COLLECTION_NAME);
    }

    private Query getFilterByAggregateId(DomainEvent event) {
        return new Query(
                Criteria.where("_id").is(event.aggregateRootId())
        );
    }
}
