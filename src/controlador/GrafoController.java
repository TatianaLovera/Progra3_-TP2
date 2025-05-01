package controlador;

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
}
