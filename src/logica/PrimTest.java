package logica;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PrimTest {

	private GrafoIngresado grafo;

    @BeforeEach
    public void setUp() {
        grafo = new GrafoIngresado();
    }

    @Test
    public void AGMSimpleTresNodosTest() {
        grafo.agregarEstacion("A");
        grafo.agregarEstacion("B");
        grafo.agregarEstacion("C");

        grafo.agregarArista(new Aristas("A", "B", 2));
        grafo.agregarArista(new Aristas("B", "C", 1));
        grafo.agregarArista(new Aristas("A", "C", 5));

        Prim prim = new Prim(grafo);
        List<Aristas> agm = prim.encontrarAGM();

        assertNotNull(agm);
        assertEquals(2, agm.size());

        int total = 0;
        for (Aristas arista : agm) {
        	total += arista.getPeso();
        }
        assertEquals(3, total);
    }
    
    @Test
    public void AGMDevuelveNullSiNoEsConexoTest() {
        grafo.agregarEstacion("A");
        grafo.agregarEstacion("B");
        grafo.agregarEstacion("C");

        grafo.agregarArista(new Aristas("A", "B", 4));

        Prim prim = new Prim(grafo);
        List<Aristas> agm = prim.encontrarAGM();

        assertNull(agm);
    }

    @Test
    public void AGMConCuatroEstacionesEstrellaTest() {
        grafo.agregarEstacion("Centro");
        grafo.agregarEstacion("Norte");
        grafo.agregarEstacion("Sur");
        grafo.agregarEstacion("Este");

        grafo.agregarArista(new Aristas("Centro", "Norte", 1));
        grafo.agregarArista(new Aristas("Centro", "Sur", 2));
        grafo.agregarArista(new Aristas("Centro", "Este", 3));

        Prim prim = new Prim(grafo);
        List<Aristas> agm = prim.encontrarAGM();

        assertNotNull(agm);
        assertEquals(3, agm.size());

        int total = 0;
        for (Aristas arista : agm) {
        	total += arista.getPeso();
        }
        assertEquals(6, total);
    }

    @Test
    public void grafoSinAristasDevuelveNullTest() {
        grafo.agregarEstacion("A");
        grafo.agregarEstacion("B");

        Prim prim = new Prim(grafo);
        List<Aristas> agm = prim.encontrarAGM();

        assertNull(agm);
    }
}
