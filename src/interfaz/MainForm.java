package interfaz;

import logica.Aristas;
import logica.Estacion;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class MainForm extends JFrame {
    private JMapViewer mapa;
    private JButton botonIngresarSenderos;
    private JButton botonDibujarSenderos;
    private List<Estacion> estaciones;
    private List<Aristas> aristas;

    public MainForm() {
        setTitle("Mapa de Estaciones");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        estaciones = new ArrayList<>();
        aristas = new ArrayList<>();

        mapa = new JMapViewer();
        mapa.setDisplayPosition(new Coordinate(-25.6953, -54.4367), 12); // Cataratas del Iguazú
        mapa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Coordinate coord = (Coordinate) mapa.getPosition(e.getPoint()); // Obtener Coordinate
                String nombre = JOptionPane.showInputDialog("Nombre de la estación:");
                if (nombre != null && !nombre.isEmpty()) {
                    Estacion estacion = new Estacion(nombre, coord);
                    estaciones.add(estacion);
                    mapa.addMapMarker(new MapMarkerDot(nombre, coord));
                }
            }
        });

        botonIngresarSenderos = new JButton("Ingresar Senderos");
        botonIngresarSenderos.addActionListener(e -> ingresarSendero());

        botonDibujarSenderos = new JButton("Dibujar Senderos");
        botonDibujarSenderos.addActionListener(e -> dibujarSenderos());

        JPanel panelBotones = new JPanel();
        panelBotones.add(botonIngresarSenderos);
        panelBotones.add(botonDibujarSenderos);

        getContentPane().add(mapa, BorderLayout.CENTER);
        getContentPane().add(panelBotones, BorderLayout.SOUTH);
    }

    
    private void ingresarSendero() {
        if (estaciones.size() < 2) {
            JOptionPane.showMessageDialog(this, "Debe haber al menos dos estaciones.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JComboBox<Estacion> comboInicio = new JComboBox<>(estaciones.toArray(new Estacion[0]));
        JComboBox<Estacion> comboFin = new JComboBox<>(estaciones.toArray(new Estacion[0]));
        JTextField campoPeso = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Estación inicio:"));
        panel.add(comboInicio);
        panel.add(new JLabel("Estación fin:"));
        panel.add(comboFin);
        panel.add(new JLabel("Impacto ambiental (1-10):"));
        panel.add(campoPeso);

        int resultado = JOptionPane.showConfirmDialog(this, panel, "Ingresar Sendero", JOptionPane.OK_CANCEL_OPTION);
        if (resultado == JOptionPane.OK_OPTION) {
            try {
                Estacion inicio = (Estacion) comboInicio.getSelectedItem();
                Estacion fin = (Estacion) comboFin.getSelectedItem();
                int peso = Integer.parseInt(campoPeso.getText());

                if (inicio.equals(fin)) {
                    JOptionPane.showMessageDialog(this, "Una estación no puede conectarse a sí misma.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Verificar si ya existe la arista (en ambas direcciones)
                boolean yaExiste = aristas.stream().anyMatch(a ->
                    (a.getInicio().equals(inicio) && a.getFin().equals(fin)) ||
                    (a.getInicio().equals(fin) && a.getFin().equals(inicio))
                );

                if (yaExiste) {
                    JOptionPane.showMessageDialog(this, "Este sendero ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Si todo está bien, crear y agregar la arista
                Aristas arista = new Aristas(inicio, fin, peso);
                aristas.add(arista);
                JOptionPane.showMessageDialog(this, "Sendero ingresado con éxito.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Impacto ambiental inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void dibujarSenderos() {
        if (aristas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay senderos para dibujar.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (Aristas arista : aristas) {
            List<Coordinate> coords = new ArrayList<>();
            coords.add(arista.getInicio().getCoordenada());
            coords.add(arista.getFin().getCoordenada());
            coords.add(arista.getInicio().getCoordenada()); // Cierre del polígono

            MapPolygonImpl linea = new MapPolygonImpl(coords);
            mapa.addMapPolygon(linea);

            Coordinate medio = obtenerPuntoMedio(arista.getInicio().getCoordenada(), arista.getFin().getCoordenada());
            mapa.addMapMarker(new MapMarkerDot("" + arista.getPeso() + "", medio));
        }
    }

    private Coordinate obtenerPuntoMedio(Coordinate a, Coordinate b) {
        double lat = (a.getLat() + b.getLat()) / 2;
        double lon = (a.getLon() + b.getLon()) / 2;
        return new Coordinate(lat, lon);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainForm ventana = new MainForm();
            ventana.setVisible(true);
        });
    }
}
