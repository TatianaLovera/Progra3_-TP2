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

        Map<String, List<String>> adjacencia = new HashMap<>();
        for (String estacion : grafo.getEstaciones()) {
            adjacencia.put(estacion, new ArrayList<>());
        }

        for (Aristas arista : aristas) {
            String inicio = arista.getInicio();
            String fin = arista.getFin();

            if (!hayCamino(inicio, fin, adjacencia, new HashSet<>())) {
                agm.add(arista);
                adjacencia.get(inicio).add(fin);
                adjacencia.get(fin).add(inicio);

                // Verificamos si el grafo sigue siendo conexo usando BFS
                if (!Bfs.bfs(inicio, grafo.getAristas()).containsAll(grafo.getEstaciones())) {
                    agm.remove(arista); // Si no está conectado, removemos la arista
                }
            }
        }

        if (esConexo(agm)) {
            return agm;
        } else {
            return null;
        }
    }

    private boolean hayCamino(String origen, String destino, Map<String, List<String>> adjacencia, Set<String> visitados) {
        if (origen.equals(destino)) return true;
        visitados.add(origen);

        for (String vecino : adjacencia.get(origen)) {
            if (!visitados.contains(vecino)) {
                if (hayCamino(vecino, destino, adjacencia, visitados)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean esConexo(List<Aristas> agm) {
        if (agm.size() != grafo.getEstaciones().size() - 1) {
            return false;
        }
        return true;
    }

    private void ordenarAristasPorPeso(List<Aristas> aristas) {
        aristas.sort(Comparator.comparingInt(Aristas::getPeso));
    }
}