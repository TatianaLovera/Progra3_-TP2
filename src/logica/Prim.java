package logica;

import java.util.*;

public class Prim {
    private GrafoIngresado grafo;

    public Prim(GrafoIngresado grafo) {
        this.grafo = grafo;
    }

    public List<Aristas> encontrarAGM() {
        List<Aristas> agm = new ArrayList<>();
        Set<String> visitados = new HashSet<>();

        if (grafo.getAristas().isEmpty()) {
            return null;
        }

        // Tomamos cualquier vértice inicial (primer arista)
        String verticeInicial = grafo.getAristas().get(0).getInicio();
        visitados.add(verticeInicial);

        while (visitados.size() < grafo.getEstaciones().size()) {
            Aristas aristaMinima = encontrarAristaMinima(visitados);
            if (aristaMinima == null) {
                // Si no encontramos arista válida, el grafo no es conexo
                return null;
            }
            agm.add(aristaMinima);
            String nuevoVertice = visitados.contains(aristaMinima.getInicio()) ? aristaMinima.getFin() : aristaMinima.getInicio();
            visitados.add(nuevoVertice);

            // Verificamos si el grafo sigue siendo conexo usando BFS
            if (!Bfs.bfs(verticeInicial, new HashSet<>(), grafo.getAristas(), nuevoVertice)) {
                return null; // Si no está conectado, retornamos null
            }
        }

        return agm;
    }

    private Aristas encontrarAristaMinima(Set<String> visitados) {
        Aristas aristaMinima = null;
        int pesoMinimo = Integer.MAX_VALUE;

        for (Aristas arista : grafo.getAristas()) {
            String inicio = arista.getInicio();
            String fin = arista.getFin();

            boolean unoVisitado = visitados.contains(inicio) ^ visitados.contains(fin); // solo uno visitado
            if (unoVisitado && arista.getPeso() < pesoMinimo) {
                pesoMinimo = arista.getPeso();
                aristaMinima = arista;
            }
        }

        return aristaMinima;
    }
}