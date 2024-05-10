package com.master.ranking.persistence.repository;

import com.master.ranking.persistence.model.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JugadorRepositoryI extends JpaRepository<Jugador, Long> {


    List<Jugador> findAllByGenero(String genero);

}
