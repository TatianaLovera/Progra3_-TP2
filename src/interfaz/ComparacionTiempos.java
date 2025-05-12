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

public class ComparacionTiempos extends JFrame {

    private GrafoController controlador;

    public ComparacionTiempos(GrafoController controlador) {
        this.controlador = controlador;

        setTitle("Comparación de Algoritmos - Prim vs Kruskal");        
        setSize(1000, 600);  
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel contenedor principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Título general
        JLabel tituloPrincipal = new JLabel("Comparación de algoritmos AGM: Prim vs Kruskal", SwingConstants.CENTER);
        tituloPrincipal.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(tituloPrincipal, BorderLayout.NORTH);

        // Panel de mapas lado a lado
        JPanel mapasPanel = new JPanel(new GridLayout(1, 2));

        // Panel izquierdo: Prim
        JPanel panelPrim = crearPanelAlgoritmo(
                "GRAFO PRIM",
                controlador.obtenerAGMPrim(),
                Color.YELLOW,
                Color.RED,
                controlador.obtenerPesoTotalAGMPrim(),
                controlador.obtenerTiempoPrim()
        );

        // Panel derecho: Kruskal
        JPanel panelKruskal = crearPanelAlgoritmo(
                "GRAFO KRUSKAL",
                controlador.obtenerAGMKruskal(),
                Color.MAGENTA,
                Color.CYAN,
                controlador.obtenerPesoTotalAGM(),
                controlador.obtenerTiempoKruskal()
        );

        mapasPanel.add(panelPrim);
        mapasPanel.add(panelKruskal);
        mainPanel.add(mapasPanel, BorderLayout.CENTER);

        // Comparación final de tiempos
        long tiempoPrim = controlador.obtenerTiempoPrim();
        long tiempoKruskal = controlador.obtenerTiempoKruskal();
        String comparacion;
        if (tiempoPrim < tiempoKruskal) {
            comparacion = "Prim fue más rápido por " + (tiempoKruskal - tiempoPrim) + " ms.";
        } else if (tiempoKruskal < tiempoPrim) {
            comparacion = "Kruskal fue más rápido por " + (tiempoPrim - tiempoKruskal) + " ms.";
        } else {
            comparacion = "Ambos algoritmos tardaron lo mismo.";
        }

        JLabel resultadoComparacion = new JLabel(comparacion, SwingConstants.CENTER);
        resultadoComparacion.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(resultadoComparacion, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
    }

    private JPanel crearPanelAlgoritmo(String titulo, List<String[]> agm, Color colorLinea, Color colorMarcador, int impactoTotal, long tiempoEjecucion) {
        JPanel panel = new JPanel(new BorderLayout());

        // Título
        JLabel tituloLabel = new JLabel(titulo, SwingConstants.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(tituloLabel, BorderLayout.NORTH);

        // Mapa
        JMapViewer mapa = new JMapViewer();
        mapa.setDisplayPosition(new Coordinate(-25.6953, -54.4367),11);
        mapa.setPreferredSize(new Dimension(450, 500));  

        // Estaciones
        for (String nombre : controlador.getEstacionesNombres()) {
            Coordinate coord = controlador.getCoordenadaEstacion(nombre);
            MapMarkerDot marcador = new MapMarkerDot(nombre, coord);
            marcador.setBackColor(colorMarcador);
            mapa.addMapMarker(marcador);
        }

        // Senderos (aristas)
        for (String[] sendero : agm) {
            Coordinate inicio = controlador.getCoordenadaEstacion(sendero[0]);
            Coordinate fin = controlador.getCoordenadaEstacion(sendero[1]);
            String impacto = sendero[2];

            List<Coordinate> coords = new ArrayList<>();
            coords.add(inicio);
            coords.add(fin);
            coords.add(inicio); // cerrar polígono

            MapPolygonImpl linea = new MapPolygonImpl(coords);
            linea.setColor(colorLinea);
            mapa.addMapPolygon(linea);

            Coordinate medio = obtenerPuntoMedio(inicio, fin);
            MapMarkerDot marcadorImpacto = new MapMarkerDot(impacto, medio);
            marcadorImpacto.setBackColor(colorMarcador);
            mapa.addMapMarker(marcadorImpacto);
        }

        panel.add(mapa, BorderLayout.CENTER);

        // Información de impacto y tiempo
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        JLabel impactoLabel = new JLabel("Impacto ambiental total: " + impactoTotal, SwingConstants.CENTER);
        JLabel tiempoLabel = new JLabel("Tiempo en generarse: " + tiempoEjecucion + " ms", SwingConstants.CENTER);

        impactoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        tiempoLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        infoPanel.add(impactoLabel);
        infoPanel.add(tiempoLabel);

        panel.add(infoPanel, BorderLayout.SOUTH);

        return panel;
    }

    private Coordinate obtenerPuntoMedio(Coordinate a, Coordinate b) {
        double lat = (a.getLat() + b.getLat()) / 2;
        double lon = (a.getLon() + b.getLon()) / 2;
        return new Coordinate(lat, lon);
    }
}

