package logica;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GrafoIngresado {
    private List<String> estaciones;
    private List<Aristas> aristas;

    public GrafoIngresado() {
        estaciones = new ArrayList<>();
        aristas = new ArrayList<>();
    }

    // Agrega una estación solo si no existe
    public void agregarEstacion(String estacion) {
        if (!estaciones.contains(estacion)) {
            estaciones.add(estacion);
        }
    }

    // Agrega una arista validada
    public void agregarArista(Aristas arista) {
        validarArista(arista);
        aristas.add(arista);
    }

    // Valida peso y existencia previa de la arista
    private void validarArista(Aristas arista) {
        if (arista.getPeso() < 1 || arista.getPeso() > 10) {
            throw new IllegalArgumentException("El impacto ambiental debe ser entre 1 y 10.");
        }
        if (contieneArista(arista.getInicio(), arista.getFin())) {
            throw new IllegalArgumentException("El sendero ya existe.");
        }
    }

    // Devuelve la lista de nombres de estaciones
    public List<String> getEstaciones() {
        return estaciones;
    }

    // Devuelve la lista de aristas
    public List<Aristas> getAristas() {
        return aristas;
    }

    // Verifica si ya existe una arista (ignorando dirección si es no dirigido)
    public boolean contieneArista(String inicio, String fin) {
        for (Aristas a : aristas) {
            boolean mismoSentido = a.getInicio().equals(inicio) && a.getFin().equals(fin);
            boolean reverso = a.getInicio().equals(fin) && a.getFin().equals(inicio);
            if (mismoSentido || reverso) {
                return true;
            }
        }
        return false;
    }

    
    public List<Aristas> ejecutarKruskal() {
        Kruskal kruskal = new Kruskal(this);
        return kruskal.encontrarAGM();
    }
    
    public List<Aristas> ejecutarPrim() {
        Prim prim = new Prim(this);
        return prim.encontrarAGM();
    }
    
    public List<String[]> getAristasComoStrings() {
        List<String[]> lista = new ArrayList<>();
        for (Aristas arista : aristas) {
            lista.add(new String[] { arista.getInicio(), arista.getFin(), String.valueOf(arista.getPeso()) });
        }
        return lista;
    }
    
    public boolean esConexo() {
        if (aristas.isEmpty()) {
            return false; // No hay senderos → no es conexo
        }

        Set<String> visitadas = new HashSet<>();
        String inicio = estaciones.get(0);

        // Recorremos BFS para llenar el conjunto de visitadas
        for (String destino : estaciones) {
            if (!visitadas.contains(destino)) {
                Bfs.bfs(inicio, visitadas, aristas, destino);
            }
        }

        return visitadas.size() == estaciones.size();
    }
}
