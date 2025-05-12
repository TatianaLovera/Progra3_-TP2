package logica;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class KruskalTest {
	
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

        Kruskal kruskal = new Kruskal(grafo);
        List<Aristas> agm = kruskal.encontrarAGM();

        assertNotNull(agm);
        assertEquals(2, agm.size());

        int pesoTotal = 0;
        for (Aristas arista : agm) {
            pesoTotal += arista.getPeso();
        }
        assertEquals(3, pesoTotal);
    }
    
    @Test
    public void AGMDevuelveNullSiNoEsConexoTest() {
        grafo.agregarEstacion("A");
        grafo.agregarEstacion("B");
        grafo.agregarEstacion("C");

        grafo.agregarArista(new Aristas("A", "B", 3));

        Kruskal kruskal = new Kruskal(grafo);
        List<Aristas> agm = kruskal.encontrarAGM();

        assertNull(agm);
    }
    
    @Test
    public void AGMConCuatroNodosTest() {
        grafo.agregarEstacion("A");
        grafo.agregarEstacion("B");
        grafo.agregarEstacion("C");
        grafo.agregarEstacion("D");

        grafo.agregarArista(new Aristas("A", "B", 1));
        grafo.agregarArista(new Aristas("A", "C", 2));
        grafo.agregarArista(new Aristas("A", "D", 3));

        Kruskal kruskal = new Kruskal(grafo);
        List<Aristas> agm = kruskal.encontrarAGM();

        assertNotNull(agm);
        assertEquals(3, agm.size());

        int total = 0;
        for (Aristas arista : agm) {
        	total += arista.getPeso();
        }
        assertEquals(6, total);
    }
    
    
}
