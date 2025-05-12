package logica;

import java.util.*;

public class GrafoIngresado {
    private List<String> estaciones;
    private List<Aristas> aristas;
    private long tiempoEjecucionKruskal;
    private long tiempoEjecucionPrim;

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

    // Verifica si ya existe una arista (ignorando dirección ya que  no es dirigido)
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

    // Verifica si el grafo es conexo
    public boolean esConexo() {
        if (getEstaciones().isEmpty()) {
            return false; // No hay estaciones, no es conexo
        }

        if (getAristas().isEmpty() && getEstaciones().size() > 1) {
            return false; // Si no hay aristas y más de una estación, no es conexo
        }

        // Tomamos el primer vértice de la lista de estaciones
        String inicio = getEstaciones().get(0);

        // Obtenemos las estaciones visitadas a partir del primer vértice usando BFS
        Set<String> visitadas = Bfs.bfs(inicio, aristas);

        // Si el número de estaciones visitadas es igual al total de estaciones, el grafo es conexo
        return visitadas.size() == getEstaciones().size();
    }
    
    public List<Aristas> ejecutarKruskal() {
        Kruskal kruskal = new Kruskal(this);
        long inicio = System.nanoTime();
        List<Aristas> agm = kruskal.encontrarAGM();
        long fin = System.nanoTime();
        this.tiempoEjecucionKruskal = fin - inicio;
        return agm;
    }
    
    public List<Aristas> ejecutarPrim() {
        Prim prim = new Prim(this);
        long inicio = System.nanoTime();
        List<Aristas> agm = prim.encontrarAGM();
        long fin = System.nanoTime();
        this.tiempoEjecucionPrim = fin - inicio;
        return agm;
    }
    
    public List<String[]> getAristasComoStrings() {
        List<String[]> lista = new ArrayList<>();
        for (Aristas arista : aristas) {
            lista.add(new String[] { arista.getInicio(), arista.getFin(), String.valueOf(arista.getPeso()) });
        }
        return lista;
    }
    
    public void setTiempoKruskal(long tiempo) {
        this.tiempoEjecucionKruskal = tiempo;
    }

    public long getTiempoKruskal() {
        return this.tiempoEjecucionKruskal;
    }

    public void setTiempoPrim(long tiempo) {
        this.tiempoEjecucionPrim = tiempo;
    }

    public long getTiempoPrim() {
        return this.tiempoEjecucionPrim;
    }
}
