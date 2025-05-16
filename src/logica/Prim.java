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

        String verticeInicial = grafo.getAristas().get(0).getInicio();
        visitados.add(verticeInicial);

        while (visitados.size() < grafo.getEstaciones().size()) {
            Aristas aristaMinima = encontrarAristaMinima(visitados);
            if (aristaMinima == null) {
                return null;
            }
            agm.add(aristaMinima);
            String nuevoVertice = visitados.contains(aristaMinima.getInicio()) ? aristaMinima.getFin() : aristaMinima.getInicio();
            visitados.add(nuevoVertice);

            Set<String> estacionesVisitadas = Bfs.bfs(verticeInicial, grafo.getAristas());
            if (!estacionesVisitadas.containsAll(grafo.getEstaciones())) {
                return null; 
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

            boolean unoVisitado = visitados.contains(inicio) ^ visitados.contains(fin);
            if (unoVisitado && arista.getPeso() < pesoMinimo) {
                pesoMinimo = arista.getPeso();
                aristaMinima = arista;
            }
        }
        return aristaMinima;
    }
}