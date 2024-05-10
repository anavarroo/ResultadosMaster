package com.resultadosmaster.services;

import com.resultadosmaster.persistence.model.Torneo;
import com.resultadosmaster.persistence.repository.TorneoRepositoryI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TorneoServiceImp implements TorneoService{

    @Autowired
    private TorneoRepositoryI torneoRepository;

    /**
     * Método para obtener todos los partidos.
     *
     * @return Lista de los partidos.
     */
    @Override
    public List<Torneo> getAllTorneos() {
        List<Torneo> torneos = torneoRepository.findAll();
        return torneos;
    }

    @Override
    public List<Torneo> getTorneosByYear(int year) {
        List<Torneo> allTorneos = torneoRepository.findAll();
        List<Torneo> torneosByYear = new ArrayList<>();

        for (Torneo torneo : allTorneos) {
            // Extraer el año de la fecha del torneo
            String fechaTorneo = torneo.getFechatorneo();
            int index = fechaTorneo.lastIndexOf(' ');
            int torneoYear = Integer.parseInt(fechaTorneo.substring(index + 1));

            // Comparar con el año proporcionado
            if (torneoYear == year) {
                torneosByYear.add(torneo);
            }
        }

        return torneosByYear;
    }





}
