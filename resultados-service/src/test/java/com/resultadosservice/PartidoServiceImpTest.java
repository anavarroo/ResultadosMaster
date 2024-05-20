package com.resultadosservice;

import com.resultadosmaster.persistence.model.Partido;
import com.resultadosmaster.persistence.repository.PartidoRepositoryI;
import com.resultadosmaster.services.PartidoServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class PartidoServiceImpTest {

    @Mock
    private PartidoRepositoryI partidoRepository;

    @InjectMocks
    private PartidoServiceImp partidoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGettAllPartidos() {
        // Configuración: Crear dos objetos Partido y configurar el mock del repositorio para que devuelva estos partidos cuando se llame a findAll
        Partido partido1 = new Partido();
        Partido partido2 = new Partido();
        when(partidoRepository.findAll()).thenReturn(Arrays.asList(partido1, partido2));

        // Acción: Llamar al método gettAllPartidos del servicio
        List<Partido> result = partidoService.gettAllPartidos();

        // Afirmación: Verificar que el tamaño de la lista devuelta es 2 y que los objetos en la lista son los mismos que los configurados en el mock
        assertEquals(2, result.size());
        assertEquals(partido1, result.get(0));
        assertEquals(partido2, result.get(1));
    }

    @Test
    public void testGetPartidosByTorneo() {
        // Configuración: Crear dos objetos Partido con el mismo torneoId y configurar el mock del repositorio para que devuelva estos partidos cuando se llame a findByTorneoId
        Long torneoId = 1L;
        Partido partido1 = new Partido();
        partido1.setTorneoId(torneoId);
        Partido partido2 = new Partido();
        partido2.setTorneoId(torneoId);

        when(partidoRepository.findByTorneoId(torneoId)).thenReturn(Arrays.asList(partido1, partido2));

        // Acción: Llamar al método getPartidosByTorneo del servicio con el torneoId específico
        List<Partido> result = partidoService.getPartidosByTorneo(torneoId);

        // Afirmación: Verificar que el tamaño de la lista devuelta es 2 y que los objetos en la lista son los mismos que los configurados en el mock
        assertEquals(2, result.size());
        assertEquals(partido1, result.get(0));
        assertEquals(partido2, result.get(1));
    }
}