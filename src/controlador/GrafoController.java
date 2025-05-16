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

    public void agregarEstacion(String nombreEstacion, Coordinate coordenada) {
        if (!grafo.getEstaciones().contains(nombreEstacion)) {
            grafo.agregarEstacion(nombreEstacion);
            estacionesCoordenadas.put(nombreEstacion, coordenada);
        }
    }

    public List<String> getEstacionesNombres() {
        return grafo.getEstaciones();
    }

    public Coordinate getCoordenadaEstacion(String nombreEstacion) {
        return estacionesCoordenadas.get(nombreEstacion);
    }

    public void agregarArista(String nombreInicio, String nombreFin, int peso) {
        Aristas arista = new Aristas(nombreInicio, nombreFin, peso);
        grafo.agregarArista(arista);
    }

    public boolean existeArista(String nombreInicio, String nombreFin) {
        return grafo.contieneArista(nombreInicio, nombreFin);
    }

    public List<Aristas> getAristas() {
        return grafo.getAristas();
    }
    
    public List<String[]> getSenderos() {
        return grafo.getAristasComoStrings();
    }

    public List<Aristas> ejecutarKruskal() {
        return grafo.ejecutarKruskal();
    }
    
    public List<String[]> obtenerAGMKruskal() {
        List<Aristas> agm = grafo.ejecutarKruskal();
        List<String[]> resultado = new ArrayList<>();
        for (Aristas a : agm) {
        	String inicio=a.getInicio();
        	String fin=a.getFin();
        	int peso=a.getPeso();
        	String[] datos=new String [3];
        	datos[0]=inicio;
        	datos[1]=fin;
        	datos[2]=peso+"";
        	
            resultado.add(datos);
        }
        return resultado;
    }
    
   
    
    public int obtenerPesoTotalAGM() {
        List<String[]> agm = obtenerAGMKruskal();
        int total = 0;

        if (agm != null) {
            for (String[] arista : agm) {
            	int impacto = Integer.parseInt(arista[2]);
            	total += impacto;
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
            	String inicio=arista.getInicio();
	        	String fin=arista.getFin();
	        	int peso=arista.getPeso();
	        	String[] datos=new String [3];
	        	datos[0]=inicio;
	        	datos[1]=fin;
	        	datos[2]=peso+"";
	        	
	            resultado.add(datos);
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
        if (estacionesCoordenadas.containsKey(nombre)) {
            return ResultadoAgregarEstacion.NOMBRE_REPETIDO;
        }

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