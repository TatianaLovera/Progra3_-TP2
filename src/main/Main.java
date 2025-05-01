package main;
/*
import controlador.GrafoController;
import interfaz.MainForm;
import logica.GrafoIngresado;

public class Main {
    public static void main(String[] args) {
        GrafoIngresado modelo = new GrafoIngresado();
        MainForm vista = new MainForm(new GrafoController(vista, modelo)); // Pasamos el controlador al constructor de MainForm
        vista.setVisible(true);
    }
}*/

import controlador.GrafoController;
import interfaz.MainForm;
import logica.GrafoIngresado;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GrafoIngresado modelo = new GrafoIngresado();
            MainForm vista = new MainForm(null);  // temporal
            GrafoController controlador = new GrafoController(vista, modelo);
            vista = new MainForm(controlador);  // pasamos el controlador correcto
            vista.setVisible(true);
        });
    }
}