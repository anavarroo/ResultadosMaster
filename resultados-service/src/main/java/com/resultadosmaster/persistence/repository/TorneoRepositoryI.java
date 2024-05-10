package com.resultadosmaster.persistence.repository;

import com.resultadosmaster.persistence.model.Torneo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TorneoRepositoryI extends JpaRepository<Torneo, Long> {

}
