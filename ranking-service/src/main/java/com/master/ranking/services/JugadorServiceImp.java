package com.master.ranking.services;

import com.master.ranking.persistence.model.Jugador;
import com.master.ranking.persistence.repository.JugadorRepositoryI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JugadorServiceImp implements JugadorService{

    @Autowired
    private JugadorRepositoryI jugadorRepository;



    @Override
    public List<Jugador> getAllJugadores() {
        List<Jugador> jugadores = jugadorRepository.findAll();

        return jugadores;
    }

    @Override
    public List<Jugador> getAllJugadoresByGenero(String genero) {
        List<Jugador> jugadores = jugadorRepository.findAllByGenero(genero);
        return jugadores;
    }
}
