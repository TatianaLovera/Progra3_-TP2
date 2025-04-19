package logica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Bfs {
	public static boolean bfs(Estacion origen, Set<Estacion> visitados, List<Aristas> aristas, Estacion destino) {
        Queue<Estacion> cola = new LinkedList<>();
        cola.add(origen);
        visitados.add(origen);

        // Construimos las adyacencias
        Map<Estacion, List<Estacion>> adyacencias = new HashMap<>();
        for (Aristas a : aristas) {
            adyacencias.putIfAbsent(a.getInicio(), new ArrayList<>());
            adyacencias.putIfAbsent(a.getFin(), new ArrayList<>());
            adyacencias.get(a.getInicio()).add(a.getFin());
            adyacencias.get(a.getFin()).add(a.getInicio());
        }

        while (!cola.isEmpty()) {
            Estacion actual = cola.poll();
            if (actual.equals(destino)) {
                return true;
            }

            for (Estacion vecino : adyacencias.getOrDefault(actual, Collections.emptyList())) {
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    cola.add(vecino);
                }
            }
        }

        return false;
    }
}
