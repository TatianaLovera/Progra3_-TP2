package main;

import controlador.GrafoController;
import interfaz.GrafoInicial;
import logica.GrafoIngresado;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        GrafoIngresado modelo = new GrafoIngresado();
        GrafoController controlador = new GrafoController(modelo);
        GrafoInicial vista = new GrafoInicial(controlador);
    }
}