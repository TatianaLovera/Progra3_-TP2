package interfaz;

import controlador.GrafoController;
import logica.Aristas;

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
        setLayout(new BorderLayout());

        setTitle("AGM de Kruskal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        crearTitulo();
        crearMapa();
        dibujarEstaciones();
        dibujarAgm();
        crearfooter();
        
        add(impactoTotalLabel, BorderLayout.SOUTH);
    }
    
    private void crearfooter() {
        int impactoTotal = controlador.obtenerPesoTotalAGM();
        impactoTotalLabel = new JLabel("Impacto ambiental total: " + impactoTotal, SwingConstants.CENTER);
        impactoTotalLabel.setFont(new Font("Arial", Font.PLAIN, 14));	
	}

	private void dibujarAgm() {
    	 List<String[]> agm = controlador.obtenerAGMKruskal();
         for (String[] sendero : agm) {
             Coordinate inicio = controlador.getCoordenadaEstacion(sendero[0]);
             Coordinate fin = controlador.getCoordenadaEstacion(sendero[1]);
             String impacto = sendero[2];

             List<Coordinate> coords = new ArrayList<>();
             coords.add(inicio);
             coords.add(fin);
             coords.add(inicio);
             MapPolygonImpl linea = new MapPolygonImpl(coords);
             setColorSendero(linea, impacto);
             mapa.addMapPolygon(linea);
         }
	}

	private void dibujarEstaciones() {
    	for (String nombre : controlador.getEstacionesNombres()) {
            Coordinate coord = controlador.getCoordenadaEstacion(nombre);
            MapMarkerDot marcador = new MapMarkerDot(nombre, coord);
            marcador.setBackColor(Color.CYAN);  
            mapa.addMapMarker(marcador);
        }
	}

	private void crearTitulo() {
    	JLabel tituloLabel = new JLabel("Camino creado a través del Algoritmo de Kruskal", SwingConstants.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(tituloLabel, BorderLayout.NORTH);
    }
    
    private void crearMapa() {
    	  mapa = new JMapViewer();
          mapa.setDisplayPosition(new Coordinate(-25.6953, -54.4367), 12);
          add(mapa, BorderLayout.CENTER);
    }   
    
    private MapPolygonImpl setColorSendero(MapPolygonImpl linea, String impacto) {
       
        Color color;
        int impactoambiental=Integer.parseInt(impacto);

        if (impactoambiental > 5) {
            color = Color.RED; 
        } else if (impactoambiental > 3) {
            color = Color.YELLOW;
        } else {
            color = Color.GREEN; 
        }
        linea.setColor(color);
        linea.setBackColor(color);
        linea.setStroke(new BasicStroke(2)); 

        return linea;
    }
}
