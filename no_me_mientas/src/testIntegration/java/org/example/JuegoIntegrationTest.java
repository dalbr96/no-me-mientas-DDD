package org.example;

import org.example.domain.juego.values.JuegoId;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Map;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JuegoIntegrationTest extends CommandBaseIntegrationTest{
    private static final String AGGREGATE_ID = new JuegoId().value();

    @Test
    @Order(1)
    void crearJuego(){
        executor(Map.of(
                "commandType", "no_me_mientas.juego.crear_juego",
                "aggregateId", AGGREGATE_ID,
                "jugadoresIds", "1111,2222,",
                "capitales", "3000,2000",
                "nombres", "Daniel Alejandro,Jorge Andrés"
        ), requestFields(
                fieldWithPath("commandType").description("Tipo de comando"),
                fieldWithPath("aggregateId").description("Identificador del agregado"),
                fieldWithPath("jugadoresIds").description("Identificadores de los jugadores, separados por comas"),
                fieldWithPath("capitales").description("Capital total de los jugadores, separados por comas"),
                fieldWithPath("nombres").description("Nombre de los jugadores, separados por comas")
        ), 3);
    }

    @Test
    @Order(2)
    void agregarJugador(){
        executor(Map.of(
                "commandType", "no_me_mientas.juego.agregar_jugador",
                "aggregateId", AGGREGATE_ID,
                "jugadorId", "3333",
                "capital", "5000",
                "nombre", "Gerson Fernando"
        ), requestFields(
                fieldWithPath("commandType").description("Tipo de comando"),
                fieldWithPath("aggregateId").description("Identificador del agregado"),
                fieldWithPath("jugadorId").description("Identificador del jugador a agregar"),
                fieldWithPath("capital").description("Capital inicial del jugador a agregar"),
                fieldWithPath("nombre").description("Nombre del jugador a agregar")
                ), 1);
    }

    @Test
    @Order(3)
    void iniciarJuego(){
        executor(Map.of(
                "commandType", "no_me_mientas.juego.iniciar_juego",
                "aggregateId", AGGREGATE_ID
        ), requestFields(
                fieldWithPath("commandType").description("Tipo de comando"),
                fieldWithPath("aggregateId").description("Identificador del agregado.")
        ), 1);
    }

    @Test
    @Order(4)
    void iniciarRonda(){
        executor(Map.of(
                "commandType", "no_me_mientas.juego.iniciar_ronda",
                "aggregateId", AGGREGATE_ID
        ), requestFields(
                fieldWithPath("commandType").description("Tipo de comando"),
                fieldWithPath("aggregateId").description("Identificador del agregado.")
        ), 2);
    }
}
