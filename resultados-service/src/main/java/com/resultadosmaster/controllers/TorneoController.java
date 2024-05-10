package com.resultadosmaster.controllers;

import com.resultadosmaster.persistence.model.Partido;
import com.resultadosmaster.persistence.model.Torneo;
import com.resultadosmaster.services.PartidoService;
import com.resultadosmaster.services.TorneoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/torneos")
@CrossOrigin
public class TorneoController {

    @Autowired
    private TorneoService torneoService;


    /**
     * Controlador para obtener todos los torneos.
     *
     * @return ResponseEntity con la lista de todos los torneos.
     */
    @GetMapping
    public ResponseEntity<List<Torneo>> getAllTorneos() {
        try {
            List<Torneo> torneos = torneoService.getAllTorneos();
            return ResponseEntity.ok(torneos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/year/{year}")
    public ResponseEntity<List<Torneo>> getTorneosByYear(@PathVariable int year) {
        try {
            List<Torneo> torneos = torneoService.getTorneosByYear(year);
            return ResponseEntity.ok(torneos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
