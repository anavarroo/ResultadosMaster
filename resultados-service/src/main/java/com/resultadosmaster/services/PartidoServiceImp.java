package com.resultadosmaster.services;

import com.resultadosmaster.persistence.model.Partido;
import com.resultadosmaster.persistence.repository.PartidoRepositoryI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
    @Service
public class PartidoServiceImp implements PartidoService {

    @Autowired
    private PartidoRepositoryI partidoRepository;


    /**
     * Método para obtener todos los partidos.
     *
     * @return Lista de los partidos.
     */
    @Override
    public List<Partido> gettAllPartidos() {
        List<Partido> partidos = partidoRepository.findAll();
        return partidos.stream()
                .collect(Collectors.toList());
    }

    @Override
    public List<Partido> getPartidosByTorneo(Long torneoId) {
        // Implementa la lógica para obtener los partidos asociados a un torneo específico
        return partidoRepository.findByTorneoId(torneoId);
    }
}
