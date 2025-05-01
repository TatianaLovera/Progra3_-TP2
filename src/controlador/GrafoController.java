package controlador;
/*
import interfaz.MainForm;
import logica.*;

public class GrafoController {
    private GrafoIngresado modelo;
    

    public GrafoController( GrafoIngresado modelo) {
        
        this.modelo = modelo;
    }

    public void agregarEstacion(Estacion estacion) {
        modelo.agregarEstacion(estacion);
    }

    public void agregarArista(Aristas arista) {
        modelo.agregarArista(arista);
    }

    public java.util.List<Estacion> getEstaciones() {
        return modelo.getEstaciones();
    }

    public java.util.List<Aristas> getAristas() {
        return modelo.getAristas();
    }

    public boolean existeArista(Aristas arista) {
        return modelo.contieneArista(arista);
    }
}*/

import logica.Aristas;
import logica.GrafoIngresado;
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
    
    public List<String[]> getSenderos() {
        return grafo.getAristasComoStrings();
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
                    // Si el impacto no es un número, lo ignoramos o podrías lanzar una excepción según prefieras
                }
            }
        }

        return total;
    }

	
}