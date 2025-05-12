package controlador;

import logica.Aristas;
import logica.GrafoIngresado;
import logica.Prim;
import org.openstreetmap.gui.jmapviewer.Coordinate;

import java.util.*;

public class GrafoController {
    private GrafoIngresado grafo;
    private Map<String, Coordinate> estacionesCoordenadas;

    public GrafoController(GrafoIngresado grafo) {
        this.grafo = grafo;
        this.estacionesCoordenadas = new HashMap<>();
    }

    // Agregar estación por nombre y coordenada
    public void agregarEstacion(String nombreEstacion, Coordinate coordenada) {
        if (!grafo.getEstaciones().contains(nombreEstacion)) {
            grafo.agregarEstacion(nombreEstacion);
            estacionesCoordenadas.put(nombreEstacion, coordenada);
        }
    }

    // Obtener lista de nombres de estaciones
    public List<String> getEstacionesNombres() {
        return grafo.getEstaciones();
    }

    // Obtener coordenada por nombre
    public Coordinate getCoordenadaEstacion(String nombreEstacion) {
        return estacionesCoordenadas.get(nombreEstacion);
    }

    // Agregar arista entre dos estaciones por nombre y peso
    public void agregarArista(String nombreInicio, String nombreFin, int peso) {
        Aristas arista = new Aristas(nombreInicio, nombreFin, peso);
        grafo.agregarArista(arista);
    }

    // Verificar si ya existe una arista entre dos estaciones
    public boolean existeArista(String nombreInicio, String nombreFin) {
        return grafo.contieneArista(nombreInicio, nombreFin);
    }

    // Obtener todas las aristas
    public List<Aristas> getAristas() {
        return grafo.getAristas();
    }
    
    public List<String[]> getSenderos() {
        return grafo.getAristasComoStrings();
    }

    // Obtener AGM con Kruskal
    public List<Aristas> ejecutarKruskal() {
        return grafo.ejecutarKruskal();
    }
    
    public List<String[]> obtenerAGMKruskal() {
        List<Aristas> agm = grafo.ejecutarKruskal();
        List<String[]> resultado = new ArrayList<>();
        for (Aristas a : agm) {
            resultado.add(new String[]{a.getInicio(), a.getFin(), String.valueOf(a.getPeso())});
        }
        return resultado;
    }
    
   
    
    public int obtenerPesoTotalAGM() {
        List<String[]> agm = obtenerAGMKruskal();
        int total = 0;

        if (agm != null) {
            for (String[] arista : agm) {
                try {
                    int impacto = Integer.parseInt(arista[2]);
                    total += impacto;
                } catch (NumberFormatException e) {
                    // Si el impacto no es un número, lo ignoramos 
                }
            }
        }

        return total;
    }
    
    public long obtenerTiempoKruskal() {
        return grafo.getTiempoKruskal();
    }

    public List<Aristas> ejecutarPrim() {
        long inicio = System.nanoTime();
        List<Aristas> agm = grafo.ejecutarPrim();
        long fin = System.nanoTime();
        grafo.setTiempoPrim(fin - inicio);
        return agm;
    }

    public List<String[]> obtenerAGMPrim() {
        List<Aristas> agm = ejecutarPrim();
        List<String[]> resultado = new ArrayList<>();

        if (agm != null) {
            for (Aristas arista : agm) {
                resultado.add(new String[]{
                    arista.getInicio(),
                    arista.getFin(),
                    String.valueOf(arista.getPeso())
                });
            }
        }

        return resultado;
    }

    public int obtenerPesoTotalAGMPrim() {
        List<Aristas> agm = grafo.ejecutarPrim(); 
        int total = 0;

        if (agm != null) {
            for (Aristas arista : agm) {
                total += arista.getPeso();
            }
        }

        return total;
    }
    
    public long obtenerTiempoPrim() {
        return grafo.getTiempoPrim();
    }
    
    public ResultadoAgregarEstacion puedeAgregarEstacion(String nombre, Coordinate coord) {
        // Verificar nombre repetido
        if (estacionesCoordenadas.containsKey(nombre)) {
            return ResultadoAgregarEstacion.NOMBRE_REPETIDO;
        }

        // Verificar coordenada repetida
        for (Coordinate existente : estacionesCoordenadas.values()) {
            if (Math.abs(existente.getLat() - coord.getLat()) < 1e-4 &&
                Math.abs(existente.getLon() - coord.getLon()) < 1e-4) {
                return ResultadoAgregarEstacion.COORDENADA_REPETIDA;
            }
        }

        return ResultadoAgregarEstacion.OK;
    }
    
    public boolean esConexo() {
        return grafo.esConexo();
    }

	
}