package interfaz;

import controlador.GrafoController;
import org.openstreetmap.gui.jmapviewer.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ComparacionTiempos extends JFrame {

    private final GrafoController controlador;
    private JPanel mapasPanel;

    public ComparacionTiempos(GrafoController controlador) {
        this.controlador = controlador;

        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Comparacion de Algoritmos - Prim vs Kruskal");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void inicializarComponentes() {
        add(crearTitulo(), BorderLayout.NORTH);

        mapasPanel = new JPanel(new GridLayout(1, 2));
        mapasPanel.add(crearPanelPrim());
        mapasPanel.add(crearPanelKruskal());
        add(mapasPanel, BorderLayout.CENTER);

        add(crearPanelComparacionTiempos(), BorderLayout.SOUTH);
    }


    private JLabel crearTitulo() {
        JLabel titulo = new JLabel("Comparación de algoritmos AGM: Prim vs Kruskal", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        return titulo;
    }

    private JPanel crearPanelPrim() {
        return crearPanelAlgoritmo(
                "GRAFO PRIM",
                controlador.obtenerAGMPrim(),
                controlador.obtenerPesoTotalAGMPrim(),
                controlador.obtenerTiempoPrim()
        );
    }

    private JPanel crearPanelKruskal() {
        return crearPanelAlgoritmo(
                "GRAFO KRUSKAL",
                controlador.obtenerAGMKruskal(),
                controlador.obtenerPesoTotalAGM(),
                controlador.obtenerTiempoKruskal()
        );
    }

    private JPanel crearPanelComparacionTiempos() {
        long tiempoPrim = controlador.obtenerTiempoPrim();
        long tiempoKruskal = controlador.obtenerTiempoKruskal();

        String resultado;
        if (tiempoPrim < tiempoKruskal) {
            resultado = "Prim fue más rápido por " + (tiempoKruskal - tiempoPrim) + " ms.";
        } else if (tiempoKruskal < tiempoPrim) {
            resultado = "Kruskal fue más rápido por " + (tiempoPrim - tiempoKruskal) + " ms.";
        } else {
            resultado = "Ambos algoritmos tardaron lo mismo.";
        }

        JLabel comparacion = new JLabel(resultado, SwingConstants.CENTER);
        comparacion.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(comparacion, BorderLayout.CENTER);
        return panel;
    }


    private JPanel crearPanelAlgoritmo(String titulo, List<String[]> agm, int impactoTotal, long tiempoEjecucion) {
        JPanel panel = new JPanel(new BorderLayout());

        panel.add(crearTituloAlgoritmo(titulo), BorderLayout.NORTH);
        panel.add(crearMapaAGM(agm), BorderLayout.CENTER);
        panel.add(crearInfoPanel(impactoTotal, tiempoEjecucion), BorderLayout.SOUTH);

        return panel;
    }

    private JLabel crearTituloAlgoritmo(String titulo) {
        JLabel label = new JLabel(titulo, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        return label;
    }

    private JMapViewer crearMapaAGM(List<String[]> agm) {
        JMapViewer mapa = new JMapViewer();
        mapa.setDisplayPosition(new Coordinate(-25.6953, -54.4367), 11);
        mapa.setPreferredSize(new Dimension(450, 500));

        agregarEstacionesAlMapa(mapa);
        agregarSenderosAlMapa(mapa, agm);

        return mapa;
    }

    private JPanel crearInfoPanel(int impactoTotal, long tiempoEjecucion) {
        JPanel info = new JPanel(new GridLayout(2, 1));

        JLabel impacto = new JLabel("Impacto ambiental total: " + impactoTotal, SwingConstants.CENTER);
        JLabel tiempo = new JLabel("Tiempo en generarse: " + tiempoEjecucion + " ms", SwingConstants.CENTER);

        impacto.setFont(new Font("Arial", Font.PLAIN, 14));
        tiempo.setFont(new Font("Arial", Font.PLAIN, 14));

        info.add(impacto);
        info.add(tiempo);
        return info;
    }

    private void agregarEstacionesAlMapa(JMapViewer mapa) {
        for (String nombre : controlador.getEstacionesNombres()) {
            Coordinate coord = controlador.getCoordenadaEstacion(nombre);
            mapa.addMapMarker(new MapMarkerDot(nombre, coord));
        }
    }

    private void agregarSenderosAlMapa(JMapViewer mapa, List<String[]> agm) {
        for (String[] sendero : agm) {
            Coordinate inicio = controlador.getCoordenadaEstacion(sendero[0]);
            Coordinate fin = controlador.getCoordenadaEstacion(sendero[1]);

            List<Coordinate> coords = new ArrayList<>();
            coords.add(inicio);
            coords.add(fin);
            coords.add(inicio); 

            MapPolygonImpl linea = new MapPolygonImpl(coords);
            configurarColorLinea(linea, sendero[2]);

            mapa.addMapPolygon(linea);
        }
    }

    private void configurarColorLinea(MapPolygonImpl linea, String impactoStr) {
        int impacto = Integer.parseInt(impactoStr);
        Color color;

        if (impacto > 5) {
            color = Color.RED;
        } else if (impacto > 3) {
            color = Color.YELLOW;
        } else {
            color = Color.GREEN;
        }

        linea.setColor(color);
        linea.setBackColor(color);
        linea.setStroke(new BasicStroke(2));
    }
}
