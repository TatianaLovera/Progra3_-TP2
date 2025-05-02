package interfaz;

import controlador.GrafoController;
import controlador.ResultadoAgregarEstacion;
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

public class GrafoInicial extends JFrame {
    private JMapViewer mapa;
    private JButton botonIngresarSenderos;
    private JButton botonDibujarSenderos;
    private JButton botonVerAGM;
    private JButton botonVerAGMPrim;
    private GrafoController controlador;

    public GrafoInicial(GrafoController controlador) {
        this.controlador = controlador;

        setTitle("Mapa de Estaciones");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Inicialización del mapa con la posición centrada en las Cataratas del Iguazú
        mapa = new JMapViewer();
        mapa.setDisplayPosition(new Coordinate(-25.6953, -54.4367), 12);

        // Agregar un MouseListener para capturar el clic en el mapa
        mapa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Coordinate coord = (Coordinate) mapa.getPosition(e.getPoint());
                String nombre = JOptionPane.showInputDialog("Nombre de la estación:");

                if (nombre != null && !nombre.isEmpty()) {
                	ResultadoAgregarEstacion resultado = controlador.puedeAgregarEstacion(nombre, coord);

                    switch (resultado) {
                        case NOMBRE_REPETIDO:
                            JOptionPane.showMessageDialog(GrafoInicial.this,
                                    "Ya existe una estación con ese nombre.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        case COORDENADA_REPETIDA:
                            JOptionPane.showMessageDialog(GrafoInicial.this,
                                    "Ya existe una estación en esa ubicación.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        case OK:
                        	controlador.agregarEstacion(nombre, coord);
                        	MapMarkerDot marcador = new MapMarkerDot(nombre, coord);
                        	marcador.setBackColor(Color.MAGENTA);
                        	mapa.addMapMarker(marcador);
                        	break;
                }}
            }
        });

        // Botones
        botonIngresarSenderos = new JButton("Ingresar Senderos");
        botonIngresarSenderos.addActionListener(e -> ingresarSendero());

        botonDibujarSenderos = new JButton("Dibujar Senderos");
        botonDibujarSenderos.addActionListener(e -> dibujarSenderos());

        botonVerAGM = new JButton("Ver AGM de Kruskal");
        botonVerAGM.addActionListener(e -> ejecutarSiEsConexo(this::mostrarAGMKruskal));

        botonVerAGMPrim = new JButton("Ver AGM de Prim");
        botonVerAGMPrim.addActionListener(e -> ejecutarSiEsConexo(this::mostrarAGMPrim));

        JPanel panelBotones = new JPanel();
        panelBotones.add(botonIngresarSenderos);
        panelBotones.add(botonDibujarSenderos);
        panelBotones.add(botonVerAGM);
        panelBotones.add(botonVerAGMPrim);

        getContentPane().add(new JLabel("Presione sobre el mapa en el punto donde desea ingresar la estación"), BorderLayout.NORTH);
        getContentPane().add(mapa, BorderLayout.CENTER);
        getContentPane().add(panelBotones, BorderLayout.SOUTH);
    }

    private void ingresarSendero() {
        List<String> estaciones = controlador.getEstacionesNombres();
        if (estaciones.size() < 2) {
            JOptionPane.showMessageDialog(this, "Debe haber al menos dos estaciones.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JComboBox<String> comboInicio = new JComboBox<>(estaciones.toArray(new String[0]));
        JComboBox<String> comboFin = new JComboBox<>(estaciones.toArray(new String[0]));
        JTextField campoImpacto = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Estación de inicio:"));
        panel.add(comboInicio);
        panel.add(new JLabel("Estación de fin:"));
        panel.add(comboFin);
        panel.add(new JLabel("Impacto ambiental (1-10):"));
        panel.add(campoImpacto);

        int resultado = JOptionPane.showConfirmDialog(this, panel, "Ingresar Sendero", JOptionPane.OK_CANCEL_OPTION);
        if (resultado == JOptionPane.OK_OPTION) {
            String inicio = (String) comboInicio.getSelectedItem();
            String fin = (String) comboFin.getSelectedItem();

            if (inicio.equals(fin)) {
                JOptionPane.showMessageDialog(this, "Debe elegir estaciones diferentes.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int impacto;
            try {
                impacto = Integer.parseInt(campoImpacto.getText());
                if (impacto < 1 || impacto > 10) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El impacto ambiental debe ser un número entre 1 y 10.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (controlador.existeArista(inicio, fin)) {
                JOptionPane.showMessageDialog(this, "El sendero entre estas estaciones ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            controlador.agregarArista(inicio, fin, impacto);
            JOptionPane.showMessageDialog(this, "Sendero agregado exitosamente.");
        }
    }

    private void dibujarSenderos() {
        List<String[]> senderos = controlador.getSenderos();
        if (senderos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay senderos para dibujar.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (String[] sendero : senderos) {
            Coordinate inicio = controlador.getCoordenadaEstacion(sendero[0]);
            Coordinate fin = controlador.getCoordenadaEstacion(sendero[1]);
            String impacto = sendero[2];

            List<Coordinate> coords = new ArrayList<>();
            coords.add(inicio);
            coords.add(fin);
            coords.add(inicio); // para cerrar el polígono

            MapPolygonImpl linea = new MapPolygonImpl(coords);
            mapa.addMapPolygon(linea);

            Coordinate medio = obtenerPuntoMedio(inicio, fin);
            MapMarkerDot marcadorImpacto = new MapMarkerDot(impacto, medio);
            marcadorImpacto.setBackColor(Color.MAGENTA);
            mapa.addMapMarker(marcadorImpacto);
        }
    }

    private void mostrarAGMKruskal() {
        GrafoKruskal ventanaKruskal = new GrafoKruskal(controlador);
        ventanaKruskal.setVisible(true);
    }

    private void mostrarAGMPrim() {
        GrafoPrim ventanaPrim = new GrafoPrim(controlador);
        ventanaPrim.setVisible(true);
    }

    private Coordinate obtenerPuntoMedio(Coordinate a, Coordinate b) {
        double lat = (a.getLat() + b.getLat()) / 2;
        double lon = (a.getLon() + b.getLon()) / 2;
        return new Coordinate(lat, lon);
    }

    private void ejecutarSiEsConexo(Runnable accion) {
        if (!controlador.esConexo()) {
            JOptionPane.showMessageDialog(this, "El grafo no es conexo. No se puede calcular el AGM.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        accion.run();
    }
}