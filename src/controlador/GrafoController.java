package controlador;
/*
import interfaz.MainForm;
import logica.Estacion;
import logica.Aristas;
import logica.GrafoIngresado;

public class GrafoController {
    private MainForm vista;
    private GrafoIngresado modelo;

    public GrafoController(MainForm vista, GrafoIngresado modelo) {
        this.vista = vista;
        this.modelo = modelo;
    }

    public void ingresarSendero() {
        // Aquí deberías implementar la lógica para ingresar un sendero, como ya tenías antes.
        // Si todo está bien, usa la vista para mostrar mensajes:
        
        try {
            // Código para ingresar senderos (preguntar nombre, coordenadas, peso, etc.)
            vista.mostrarMensaje("Sendero ingresado con éxito.");
        } catch (Exception e) {
            vista.mostrarMensaje("Error al ingresar sendero.");
        }
    }

    public void dibujarSenderos() {
        // Aquí iría la lógica para dibujar los senderos.
        // Si no tienes una implementación directa de cómo se deben dibujar, puedes hacer algo similar a lo que tenías.
        
        vista.mostrarMensaje("Dibujando senderos...");
    }
}*/

import interfaz.MainForm;
import logica.*;

public class GrafoController {
    private GrafoIngresado modelo;
    private MainForm vista;

    public GrafoController(MainForm vista, GrafoIngresado modelo) {
        this.vista = vista;
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
