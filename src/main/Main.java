package main;

import controlador.GrafoController;
import interfaz.GrafoInicial;
import logica.GrafoIngresado;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Crear el modelo
            GrafoIngresado modelo = new GrafoIngresado();
            
            // Crear el controlador, pasándole el modelo
            GrafoController controlador = new GrafoController(modelo);
            
            // Crear la vista (MainForm), pasándole el controlador
            GrafoInicial vista = new GrafoInicial(controlador);
            
            // Hacer visible la vista
            vista.setVisible(true);
        });
    }
}