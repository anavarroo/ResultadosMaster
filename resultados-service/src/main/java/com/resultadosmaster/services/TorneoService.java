package com.resultadosmaster.services;

import com.resultadosmaster.persistence.model.Torneo;

import java.util.List;

public interface TorneoService {

    List<Torneo> getAllTorneos();
    List<Torneo> getTorneosByYear(int year);

}
