package logica;

import java.util.*;

public class Bfs {

    public static boolean bfs(String origen, Set<String> visitados, List<Aristas> aristas, String destino) {
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
            if (actual.equals(destino)) {
                return true;
            }

            for (String vecino : adyacencias.getOrDefault(actual, Collections.emptyList())) {
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    cola.add(vecino);
                }
            }
        }

        return false;
    }
}
