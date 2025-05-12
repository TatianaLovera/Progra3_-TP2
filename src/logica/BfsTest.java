package logica;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class BfsTest {
	
	@Test
    public void bfsConGrafoConexoTest() {
		List<Aristas> aristas = new ArrayList<>();
		aristas.add(new Aristas("A", "B", 1));
		aristas.add(new Aristas("B", "C", 1));
		aristas.add(new Aristas("C", "D", 1));

        Set<String> visitados = Bfs.bfs("A", aristas);

        assertEquals(Set.of("A", "B", "C", "D"), visitados);
    }
	
	@Test
    public void bfsConNodoAisladoTest() {
        List<Aristas> aristas = new ArrayList<>();
		aristas.add(new Aristas("A", "B", 1));
		aristas.add(new Aristas("B", "C", 1));
		
        Set<String> visitados = Bfs.bfs("A", aristas);

        assertTrue(visitados.contains("A"));
        assertTrue(visitados.contains("B"));
        assertTrue(visitados.contains("C"));
        assertFalse(visitados.contains("D"));
    }
	
	@Test
    public void bfsConUnSoloNodoTest() {
        List<Aristas> aristas = Collections.emptyList();

        Set<String> visitados = Bfs.bfs("A", aristas);

        assertEquals(Set.of("A"), visitados);
    }
	
	@Test
    public void bfsConCicloTest() {
		List<Aristas> aristas = new ArrayList<>();
		aristas.add(new Aristas("A", "B", 1));
		aristas.add(new Aristas("B", "C", 1));
		aristas.add(new Aristas("C", "A", 1));
		
        Set<String> visitados = Bfs.bfs("A", aristas);

        assertEquals(Set.of("A", "B", "C"), visitados);
    }
	
	@Test
    public void bfsDesdeNodoNoConectadoTest() {
		List<Aristas> aristas = new ArrayList<>();
		aristas.add(new Aristas("X", "Y", 1));
		aristas.add(new Aristas("Y", "Z", 1));
        
        Set<String> visitados = Bfs.bfs("W", aristas);

        assertEquals(Set.of("W"), visitados);
    }
}
