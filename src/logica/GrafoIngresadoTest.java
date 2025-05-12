package logica;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GrafoIngresadoTest {

	private GrafoIngresado grafo;
	
	@BeforeEach
	public void setUp() {
        grafo = new GrafoIngresado();
    }

	@Test
    public void agregarEstacionTest() {
		grafo.agregarEstacion("A");
        grafo.agregarEstacion("B");
        grafo.agregarEstacion("A");

        List<String> estaciones = grafo.getEstaciones();
        assertEquals(2, estaciones.size());
        assertTrue(estaciones.contains("A"));
        assertTrue(estaciones.contains("B"));
    }
	
	@Test
    public void agregarAristaValidaTest() {
        grafo.agregarEstacion("A");
        grafo.agregarEstacion("B");

        Aristas arista = new Aristas("A", "B", 5);
        grafo.agregarArista(arista);

        assertEquals(1, grafo.getAristas().size());
    }
	
	@Test
    public void agregarAristaDuplicadaLanzaExcepcionTest() {
        grafo.agregarEstacion("A");
        grafo.agregarEstacion("B");

        Aristas arista1 = new Aristas("A", "B", 3);
        Aristas arista2 = new Aristas("B", "A", 4);

        grafo.agregarArista(arista1);
        assertThrows(IllegalArgumentException.class, () -> grafo.agregarArista(arista2));
    }
	
	@Test
    public void agregarAristaConPesoInvalidoLanzaExcepcionTest() {
        grafo.agregarEstacion("A");
        grafo.agregarEstacion("B");

        Aristas arista1 = new Aristas("A", "B", 0);
        Aristas arista2 = new Aristas("A", "B", 11);

        assertThrows(IllegalArgumentException.class, () -> grafo.agregarArista(arista1));
        assertThrows(IllegalArgumentException.class, () -> grafo.agregarArista(arista2));
    }
	
	@Test
    public void contieneAristaTest() {
        grafo.agregarEstacion("X");
        grafo.agregarEstacion("Y");
        Aristas arista = new Aristas("X", "Y", 4);
        grafo.agregarArista(arista);

        assertTrue(grafo.contieneArista("X", "Y"));
        assertTrue(grafo.contieneArista("Y", "X"));
        assertFalse(grafo.contieneArista("X", "Z"));
    }
	
	@Test
    public void esConexoConEstacionesConectadasTest() {
        grafo.agregarEstacion("A");
        grafo.agregarEstacion("B");
        grafo.agregarEstacion("C");

        grafo.agregarArista(new Aristas("A", "B", 3));
        grafo.agregarArista(new Aristas("B", "C", 5));

        assertTrue(grafo.esConexo());
    }
	
	@Test
    public void esConexoFalsoConEstacionesAisladasTest() {
        grafo.agregarEstacion("A");
        grafo.agregarEstacion("B");
        grafo.agregarEstacion("C");

        grafo.agregarArista(new Aristas("A", "B", 2));

        assertFalse(grafo.esConexo());
    }
	
	@Test
    public void getAristasComoStringsTest() {
        grafo.agregarEstacion("A");
        grafo.agregarEstacion("B");
        grafo.agregarEstacion("C");

        grafo.agregarArista(new Aristas("A", "B", 3));
        grafo.agregarArista(new Aristas("B", "C", 5));

        List<String[]> resultado = grafo.getAristasComoStrings();

        assertEquals(2, resultado.size());

        String[] arista1 = resultado.get(0);
        assertArrayEquals(new String[] {"A", "B", "3"}, arista1);

        String[] arista2 = resultado.get(1);
        assertArrayEquals(new String[] {"B", "C", "5"}, arista2);
    }

}
