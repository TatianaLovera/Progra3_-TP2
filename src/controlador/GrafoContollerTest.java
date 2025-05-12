package controlador;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.openstreetmap.gui.jmapviewer.Coordinate;

import logica.Aristas;
import logica.GrafoIngresado;

public class GrafoContollerTest {
	
	@Test
	public void agregarEstacionGuardaCoordenadaCorrectaTest() {
	    GrafoController controller = new GrafoController(new GrafoIngresado());
	    Coordinate coord = new Coordinate(-25.5, -54.5);
	    controller.agregarEstacion("Estacion1", coord);

	    assertEquals(coord, controller.getCoordenadaEstacion("Estacion1"));
	}
	
	@Test
	public void puedeAgregarEstacionDetectaNombreRepetidoTest() {
	    GrafoController controller = new GrafoController(new GrafoIngresado());
	    controller.agregarEstacion("A", new Coordinate(0, 0));

	    ResultadoAgregarEstacion resultado = controller.puedeAgregarEstacion("A", new Coordinate(1, 1));
	    assertEquals(ResultadoAgregarEstacion.NOMBRE_REPETIDO, resultado);
	}

	@Test
	public void puedeAgregarEstacionDetectaCoordenadaRepetidaTest() {
	    GrafoController controller = new GrafoController(new GrafoIngresado());
	    Coordinate coord = new Coordinate(-25.1234, -54.5678);
	    controller.agregarEstacion("A", coord);

	    ResultadoAgregarEstacion resultado = controller.puedeAgregarEstacion("B", new Coordinate(-25.12345, -54.56785));
	    assertEquals(ResultadoAgregarEstacion.COORDENADA_REPETIDA, resultado);
	}

	@Test
	public void obtenerPesoTotalAGMCalculaCorrectamenteTest() {
	    GrafoIngresado grafo = new GrafoIngresado();
	    grafo.agregarEstacion("A");
	    grafo.agregarEstacion("B");
	    grafo.agregarEstacion("C");

	    grafo.agregarArista(new Aristas("A", "B", 2));
	    grafo.agregarArista(new Aristas("B", "C", 3));

	    GrafoController controller = new GrafoController(grafo);
	    int total = controller.obtenerPesoTotalAGM();

	    assertEquals(5, total);
	}

}
