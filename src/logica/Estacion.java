	package logica;
	
	import org.openstreetmap.gui.jmapviewer.Coordinate;

	import java.util.Objects;

	public class Estacion {
	    private String nombre;
	    private Coordinate coordenada;

	    public Estacion(String nombre, Coordinate coordenada) {
	        this.nombre = nombre;
	        this.coordenada = coordenada;
	    }

	    public String getNombre() {
	        return nombre;
	    }

	    public Coordinate getCoordenada() {
	        return coordenada;
	    }

	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        Estacion estacion = (Estacion) o;
	        return Objects.equals(nombre, estacion.nombre);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(nombre);
	    }

	    @Override
	    public String toString() {
	        return nombre;
	    }
	}


