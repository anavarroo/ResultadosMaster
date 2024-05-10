package com.resultadosmaster.persistence.repository;

import com.resultadosmaster.persistence.model.Partido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartidoRepositoryI extends JpaRepository<Partido, Long> {


    List<Partido> findByTorneoId(Long torneoId);
}
