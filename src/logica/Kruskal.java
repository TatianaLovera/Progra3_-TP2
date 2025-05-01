package logica;

import java.util.*;

public class Kruskal {
    private GrafoIngresado grafo;

    public Kruskal(GrafoIngresado grafo) {
        this.grafo = grafo;
    }

    public List<Aristas> encontrarAGM() {
        List<Aristas> agm = new ArrayList<>();
        List<Aristas> aristas = new ArrayList<>(grafo.getAristas());
        ordenarAristasPorPeso(aristas);
        
        Set<String> padres = new HashSet<>(); // Conjunto de padres para evitar ciclos

        for (Aristas arista : aristas) {
            if (!formarCiclo(arista, agm, padres)) {
                agm.add(arista);
            }
        }
        return esConexo(agm) ? agm : null;
    }

    private boolean formarCiclo(Aristas arista, List<Aristas> agm, Set<String> padres) {
        if (!padres.contains(arista.getInicio()) || !padres.contains(arista.getFin())) {
            padres.add(arista.getInicio());
            padres.add(arista.getFin());
            return false;
        }
        return true;
    }

    private boolean esConexo(List<Aristas> agm) {
        Set<String> visitados = new HashSet<>();
        for (Aristas arista : agm) {
            visitados.add(arista.getInicio());
            visitados.add(arista.getFin());
        }
        return visitados.size() == grafo.getEstaciones().size();
    }

    private void ordenarAristasPorPeso(List<Aristas> aristas) {
        aristas.sort(Comparator.comparingInt(Aristas::getPeso));
    }
}