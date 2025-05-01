package interfaz;

import controlador.GrafoController;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GrafoKruskal extends JFrame {
    private JMapViewer mapa;
    private GrafoController controlador;
    private JLabel impactoTotalLabel;

    public GrafoKruskal(GrafoController controlador) {
        this.controlador = controlador;

        setTitle("AGM de Kruskal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Título arriba
        JLabel tituloLabel = new JLabel("Camino creado a través del Algoritmo de Kruskal", SwingConstants.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Mapa centrado en las Cataratas del Iguazú
        mapa = new JMapViewer();
        mapa.setDisplayPosition(new Coordinate(-25.6953, -54.4367), 12);

        // Dibujar estaciones
        for (String nombre : controlador.getEstacionesNombres()) {
            Coordinate coord = controlador.getCoordenadaEstacion(nombre);
            mapa.addMapMarker(new MapMarkerDot(nombre, coord));
        }

        // Dibujar AGM
        List<String[]> agm = controlador.obtenerAGMKruskal();
        for (String[] sendero : agm) {
            Coordinate inicio = controlador.getCoordenadaEstacion(sendero[0]);
            Coordinate fin = controlador.getCoordenadaEstacion(sendero[1]);
            String impacto = sendero[2];

            List<Coordinate> coords = new ArrayList<>();
            coords.add(inicio);
            coords.add(fin);
            coords.add(inicio); // cerrar polígono

            MapPolygonImpl linea = new MapPolygonImpl(coords);
            mapa.addMapPolygon(linea);

            Coordinate medio = obtenerPuntoMedio(inicio, fin);
            mapa.addMapMarker(new MapMarkerDot(impacto, medio));
        }

        // Etiqueta abajo para impacto total
        int impactoTotal = controlador.obtenerPesoTotalAGM();
        impactoTotalLabel = new JLabel("Impacto ambiental total: " + impactoTotal, SwingConstants.CENTER);
        impactoTotalLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // Layout
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(tituloLabel, BorderLayout.NORTH);
        getContentPane().add(mapa, BorderLayout.CENTER);
        getContentPane().add(impactoTotalLabel, BorderLayout.SOUTH);
    }

    private Coordinate obtenerPuntoMedio(Coordinate a, Coordinate b) {
        double lat = (a.getLat() + b.getLat()) / 2;
        double lon = (a.getLon() + b.getLon()) / 2;
        return new Coordinate(lat, lon);
    }
}
