package com.master.ranking.services;

import com.master.ranking.persistence.model.Jugador;

import java.util.List;

public interface JugadorService {

    List<Jugador> getAllJugadores();

    List<Jugador> getAllJugadoresByGenero(String genero);

}
