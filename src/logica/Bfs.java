package logica;

import java.util.*;

public class Bfs {

    public static Set<String> bfs(String origen, List<Aristas> aristas) {
        Set<String> visitados = new HashSet<>();
        Queue<String> cola = new LinkedList<>();
        cola.add(origen);
        visitados.add(origen);

        // Construimos las adyacencias
        Map<String, List<String>> adyacencias = new HashMap<>();
        for (Aristas a : aristas) {
            adyacencias.putIfAbsent(a.getInicio(), new ArrayList<>());
            adyacencias.putIfAbsent(a.getFin(), new ArrayList<>());
            adyacencias.get(a.getInicio()).add(a.getFin());
            adyacencias.get(a.getFin()).add(a.getInicio());
        }

        while (!cola.isEmpty()) {
            String actual = cola.poll();
            for (String vecino : adyacencias.getOrDefault(actual, Collections.emptyList())) {
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    cola.add(vecino);
                }
            }
        }

        return visitados;
    }
}
