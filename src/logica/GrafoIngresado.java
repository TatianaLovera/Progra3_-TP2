package logica;

/*
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GrafoIngresado {
	private List<Estacion> estaciones;
    private List<Aristas> aristas;

    public GrafoIngresado() {
        estaciones = new ArrayList<>();
        aristas = new ArrayList<>();
    }

    public void agregarEstacion(Estacion estacion) {
        if (!estaciones.contains(estacion)) {
            estaciones.add(estacion);
        }
    }

    public void agregarArista(Aristas arista) {
        validarArista(arista);
        aristas.add(arista);
    }

    private void validarArista(Aristas arista) {
        if (arista.getPeso() < 0) {
            throw new IllegalArgumentException("El peso no puede ser negativo");
        }
        if (contieneArista(arista)) {
            throw new IllegalArgumentException("No se pueden repetir aristas");
        }
    }

    public List<Estacion> getEstaciones() {
        return estaciones;
    }

    public List<Aristas> getAristas() {
        return aristas;
    }

    public Estacion buscarEstacionPorNombre(String nombre) {
        return estaciones.stream()
                         .filter(e -> Objects.equals(e.getNombre(), nombre))
                         .findFirst()
                         .orElse(null);
    }

    public boolean contieneEstacion(String nombre) {
        return buscarEstacionPorNombre(nombre) != null;
    }

    public boolean contieneArista(Aristas nueva) {
        return aristas.stream().anyMatch(a ->
            (a.getInicio().equals(nueva.getInicio()) && a.getFin().equals(nueva.getFin())) ||
            (a.getInicio().equals(nueva.getFin()) && a.getFin().equals(nueva.getInicio())));
    }

    public void eliminarTodo() {
        estaciones.clear();
        aristas.clear();
    }

    public void mostrarGrafo() {
        aristas.forEach(arista ->
            System.out.println("Inicio: " + arista.getInicio().getNombre() +
                               ", Fin: " + arista.getFin().getNombre() +
                               ", Peso: " + arista.getPeso()));
    }
}*/
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GrafoIngresado {
    private List<Estacion> estaciones;
    private List<Aristas> aristas;

    public GrafoIngresado() {
        estaciones = new ArrayList<>();
        aristas = new ArrayList<>();
    }

    public void agregarEstacion(Estacion estacion) {
        if (!estaciones.contains(estacion)) {
            estaciones.add(estacion);
        }
    }

    public void agregarArista(Aristas arista) {
        validarArista(arista);
        aristas.add(arista);
    }

    private void validarArista(Aristas arista) {
        if (arista.getPeso() < 0) {
            throw new IllegalArgumentException("El peso no puede ser negativo");
        }
        if (contieneArista(arista)) {
            throw new IllegalArgumentException("No se pueden repetir aristas");
        }
    }

    public List<Estacion> getEstaciones() {
        return estaciones;
    }

    public List<Aristas> getAristas() {
        return aristas;
    }

    public boolean contieneArista(Aristas nueva) {
        return aristas.stream().anyMatch(a ->
            (a.getInicio().equals(nueva.getInicio()) && a.getFin().equals(nueva.getFin())) ||
            (a.getInicio().equals(nueva.getFin()) && a.getFin().equals(nueva.getInicio()))
        );
    }
}
