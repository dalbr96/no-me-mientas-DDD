package org.example.domain.ronda.events;

import co.com.sofka.domain.generic.DomainEvent;
import org.example.domain.ronda.values.Dado;

import java.util.List;

public class DadosLanzados extends DomainEvent {
    private final List<Dado> dados;
    public DadosLanzados( List<Dado> dados) {
        super("nomemientas.ronda.LanzarDado");
        this.dados = dados;
    }

    public List<Dado> getDados() {
        return dados;
    }
}
