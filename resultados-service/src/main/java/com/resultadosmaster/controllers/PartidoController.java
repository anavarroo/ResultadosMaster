package com.resultadosmaster.controllers;

import com.resultadosmaster.persistence.model.Partido;
import com.resultadosmaster.services.PartidoService;
import com.resultadosmaster.services.PartidoServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/v1/partidos")
@CrossOrigin
public class PartidoController {

    @Autowired
    private PartidoService partidoService;


    /**
     * Controlador para obtener todas las partidos.
     *
     * @return ResponseEntity con la lista de todos los partidos.
     */
    @GetMapping
    public ResponseEntity<List<Partido>> getAllPartidos() {
        try {
            List<Partido> partidos = partidoService.gettAllPartidos();
            return ResponseEntity.ok(partidos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/torneo/{torneoId}/partidos")
    public ResponseEntity<List<Partido>> getPartidosByTorneo(@PathVariable Long torneoId) {
        try {
            List<Partido> partidos = partidoService.getPartidosByTorneo(torneoId);
            return ResponseEntity.ok(partidos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
