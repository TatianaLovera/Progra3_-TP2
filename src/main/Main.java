package main;

import controlador.GrafoController;
import interfaz.MainForm;
import logica.GrafoIngresado;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GrafoIngresado modelo = new GrafoIngresado();
            MainForm vista = new MainForm(null);  // temporal
            GrafoController controlador = new GrafoController( modelo);
            vista = new MainForm(controlador);  // pasamos el controlador correcto
            vista.setVisible(true);
        });
    }
}