package com.rankingservice;

import com.master.ranking.persistence.model.Jugador;
import com.master.ranking.persistence.repository.JugadorRepositoryI;
import com.master.ranking.services.JugadorServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class JugadorServiceImpTest {

    // Mockea la dependencia del repositorio de jugadores
    @Mock
    private JugadorRepositoryI jugadorRepository;

    // Inyecta los mocks en la clase de servicio de jugadores
    @InjectMocks
    private JugadorServiceImp jugadorService;

    // Inicializa los mocks antes de cada prueba
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Prueba para el método getAllJugadores
    @Test
    public void testGetAllJugadores() {
        // Preparar: Crea dos objetos Jugador y simula el comportamiento del repositorio
        Jugador jugador1 = new Jugador();
        Jugador jugador2 = new Jugador();
        when(jugadorRepository.findAll()).thenReturn(Arrays.asList(jugador1, jugador2));

        // Actuar: Llama al método getAllJugadores del servicio
        List<Jugador> result = jugadorService.getAllJugadores();

        // Assert (afirmar): Verifica que el tamaño de la lista devuelta es 2 y que los jugadores son los esperados
        assertEquals(2, result.size());
        assertEquals(jugador1, result.get(0));
        assertEquals(jugador2, result.get(1));
    }

    // Prueba para el método getAllJugadoresByGenero
    @Test
    public void testGetAllJugadoresByGenero() {
        // Preparar: Define el género y crea dos objetos Jugador con ese género
        String genero = "Masculino";
        Jugador jugador1 = new Jugador();
        jugador1.setGenero(genero);
        Jugador jugador2 = new Jugador();
        jugador2.setGenero(genero);
        when(jugadorRepository.findAllByGenero(genero)).thenReturn(Arrays.asList(jugador1, jugador2));

        // Actuar: Llama al método getAllJugadoresByGenero del servicio
        List<Jugador> result = jugadorService.getAllJugadoresByGenero(genero);

        // Afimar: Verifica que el tamaño de la lista devuelta es 2 y que los jugadores son los esperados
        assertEquals(2, result.size());
        assertEquals(jugador1, result.get(0));
        assertEquals(jugador2, result.get(1));
    }
}
