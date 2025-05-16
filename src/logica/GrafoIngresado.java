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

    public void agregarEstacion(String estacion) {
        if (!estaciones.contains(estacion)) {
            estaciones.add(estacion);
        }
    }

    public void agregarArista(Aristas arista) {
        validarArista(arista);
        aristas.add(arista);
    }

    private void validarArista(Aristas arista) {
        if (arista.getPeso() < 1 || arista.getPeso() > 10) {
            throw new IllegalArgumentException("El impacto ambiental debe ser entre 1 y 10.");
        }
        if (contieneArista(arista.getInicio(), arista.getFin())) {
            throw new IllegalArgumentException("El sendero ya existe.");
        }
    }

    public List<String> getEstaciones() {
        return estaciones;
    }

    public List<Aristas> getAristas() {
        return aristas;
    }

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

    public boolean esConexo() {
        if (getEstaciones().isEmpty()) {
            return false;
        }
        if (getAristas().isEmpty() && getEstaciones().size() > 1) {
            return false;
        }
        String inicio = getEstaciones().get(0);
        Set<String> visitadas = Bfs.bfs(inicio, aristas);
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
        for (Aristas a : aristas) {
        	String inicio=a.getInicio();
        	String fin=a.getFin();
        	int peso=a.getPeso();
        	String[] datos=new String [3];
        	datos[0]=inicio;
        	datos[1]=fin;
        	datos[2]=peso+"";
            lista.add(datos);
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
