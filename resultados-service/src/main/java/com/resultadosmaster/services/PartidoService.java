package com.resultadosmaster.services;

import com.resultadosmaster.persistence.model.Partido;

import java.util.List;

public interface PartidoService {

    List<Partido> gettAllPartidos();

    List<Partido> getPartidosByTorneo(Long torneoId);


}
