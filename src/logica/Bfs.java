package logica;

import java.util.*;

public class Bfs {

    public static Set<String> bfs(String origen, List<Aristas> aristas) {
        Set<String> visitados = new HashSet<>();
        Queue<String> cola = new LinkedList<>();
        
        cola.add(origen);
        visitados.add(origen);

        Map<String, List<String>> adyacencias = new HashMap<>();
        
        for (Aristas a : aristas) {
        	String inicio=a.getInicio();
        	String fin=a.getFin();
        	
        	if (!adyacencias.containsKey(inicio)) {
        		adyacencias.put(inicio,new ArrayList<>());
        	}
        	if(!adyacencias.containsKey(fin)) {
        		adyacencias.put(fin,new ArrayList<>());
        	}
        	
        	adyacencias.get(inicio).add(fin);
        	adyacencias.get(fin).add(inicio);
        }

        while (!cola.isEmpty()) {
    
        	String actual=cola.poll();
        	List<String> vecinos=adyacencias.get(actual);
        	
        	if (vecinos==null) {
        		vecinos=Collections.emptyList();
        	
                }
        	for (String vecino:vecinos) {
        		if (!visitados.contains(vecino)) {
        			visitados.add(vecino);
        			cola.add(vecino);
        		}
            }
        }

        return visitados;
    }
}
