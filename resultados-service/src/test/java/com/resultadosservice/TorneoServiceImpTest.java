package com.resultadosservice;

import com.resultadosmaster.persistence.model.Torneo;
import com.resultadosmaster.persistence.repository.TorneoRepositoryI;
import com.resultadosmaster.services.TorneoServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TorneoServiceImpTest {

	@Mock
	private TorneoRepositoryI torneoRepository;

	@InjectMocks
	private TorneoServiceImp torneoService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testGetAllTorneos() {
		// Configuración: Crear dos objetos Torneo y configurar el mock del repositorio para que devuelva estos torneos cuando se llame a findAll
		Torneo torneo1 = new Torneo();
		Torneo torneo2 = new Torneo();
		when(torneoRepository.findAll()).thenReturn(Arrays.asList(torneo1, torneo2));

		// Acción: Llamar al método getAllTorneos del servicio
		List<Torneo> result = torneoService.getAllTorneos();

		// Afirmación: Verificar que el tamaño de la lista devuelta es 2 y que los objetos en la lista son los mismos que los configurados en el mock
		assertEquals(2, result.size());
		assertEquals(torneo1, result.get(0));
		assertEquals(torneo2, result.get(1));
	}

	@Test
	public void testGetTorneosByYear() {
		// Configuración: Crear tres objetos Torneo con diferentes años en sus fechas y configurar el mock del repositorio para que devuelva estos torneos cuando se llame a findAll
		Torneo torneo1 = new Torneo();
		torneo1.setFechatorneo("March 2022");
		Torneo torneo2 = new Torneo();
		torneo2.setFechatorneo("April 2022");
		Torneo torneo3 = new Torneo();
		torneo3.setFechatorneo("May 2023");

		when(torneoRepository.findAll()).thenReturn(Arrays.asList(torneo1, torneo2, torneo3));

		// Acción: Llamar al método getTorneosByYear del servicio con el año específico 2022
		List<Torneo> result = torneoService.getTorneosByYear(2022);

		// Afirmación: Verificar que el tamaño de la lista devuelta es 2 y que los objetos en la lista corresponden al año 2022
		assertEquals(2, result.size());
		assertEquals(torneo1, result.get(0));
		assertEquals(torneo2, result.get(1));
	}
}
