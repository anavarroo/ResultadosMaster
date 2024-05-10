package com.master.ranking.controllers;

import com.master.ranking.services.JugadorService;
import com.master.ranking.persistence.model.Jugador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ranking")
@CrossOrigin
public class JugadorController {

    @Autowired
    private JugadorService jugadorService;


    /**
     * Controlador para obtener todas las partidos.
     *
     * @return ResponseEntity con la lista de todos los partidos.
     */
    @GetMapping()
    public ResponseEntity<List<Jugador>> getAllJugadores() {
        try {
            List<Jugador> jugadores = jugadorService.getAllJugadores();
            return ResponseEntity.ok(jugadores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{genero}")
    public ResponseEntity<List<Jugador>> getAllJugadoresByGenero(@PathVariable String genero) {
        try {
            List<Jugador> jugadores = jugadorService.getAllJugadoresByGenero(genero);
            return ResponseEntity.ok(jugadores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
