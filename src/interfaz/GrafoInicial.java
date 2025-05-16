package interfaz;

import controlador.GrafoController;
import controlador.ResultadoAgregarEstacion;
import logica.Aristas;

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
import java.util.ResourceBundle.Control;

public class GrafoInicial extends JFrame {
    private JMapViewer mapa;
    private JButton botonIngresarSenderos;
    private JButton botonDibujarSenderos;
    private JButton botonVerAGM;
    private JButton botonVerAGMPrim;
    private JButton botonComparacion;
    private GrafoController controlador;

    public GrafoInicial(GrafoController controlador) {
        this.controlador = controlador;

        setTitle("Mapa de Estaciones");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mapa = new JMapViewer();
        mapa.setDisplayPosition(new Coordinate(-25.6953, -54.4367), 12);

        mapa.addMouseListener(eventoClickMapa());
        
      
        botonIngresarSenderos = new JButton("Ingresar Senderos");
        botonIngresarSenderos.addActionListener(e -> ingresarSendero());

        botonDibujarSenderos = new JButton("Dibujar Senderos");
        botonDibujarSenderos.addActionListener(e -> dibujarSenderos());

        botonVerAGM = new JButton("Ver AGM de Kruskal");
        botonVerAGM.addActionListener(e -> ejecutarSiEsConexo(this::mostrarAGMKruskal));

        botonVerAGMPrim = new JButton("Ver AGM de Prim");
        botonVerAGMPrim.addActionListener(e -> ejecutarSiEsConexo(this::mostrarAGMPrim));

        botonComparacion = new JButton("Comparacion de tiempos");
        botonComparacion.addActionListener(e -> ejecutarSiEsConexo(() -> {
            new ComparacionTiempos(controlador).setVisible(true);
        }));

        JPanel panelBotones = new JPanel();
        panelBotones.add(botonIngresarSenderos);
        panelBotones.add(botonDibujarSenderos);
        panelBotones.add(botonVerAGM);
        panelBotones.add(botonVerAGMPrim);
        panelBotones.add(botonComparacion);

        getContentPane().add(new JLabel("Presione sobre el mapa en el punto donde desea ingresar la estación"), BorderLayout.NORTH);
        getContentPane().add(mapa, BorderLayout.CENTER);
        getContentPane().add(panelBotones, BorderLayout.SOUTH);
        
        setVisible(true);
    }
    public MouseAdapter eventoClickMapa () {
    	return new MouseAdapter() {
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
                    }
                }
    		}
		};
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
    	
    	List<Aristas> senderos=controlador.getAristas() ;
    	
    	for (Aristas sendero:senderos) {
    		Coordinate coordenadaInicio=controlador.getCoordenadaEstacion(sendero.getInicio());
    		Coordinate coordenadafin=controlador.getCoordenadaEstacion(sendero.getFin());
    		
    		List<Coordinate> coords = new ArrayList<>();
            coords.add(coordenadaInicio);
            coords.add(coordenadafin);
            coords.add(coordenadaInicio); 
            
            MapPolygonImpl linea = new MapPolygonImpl(coords);
            linea=  setColorSendero(sendero.getPeso(), linea);
            mapa.addMapPolygon(linea);    		
    	}
    	
    }

    private MapPolygonImpl setColorSendero(int impactoAmbiental, MapPolygonImpl linea) {

        Color color;
        if (impactoAmbiental > 5) {
            color = Color.RED; 
        } else if (impactoAmbiental > 3) {
            color = Color.YELLOW;
        } else {
            color = Color.GREEN; 
        }

        linea.setColor(color);
        linea.setBackColor(color);
        linea.setStroke(new BasicStroke(2)); 
        return linea;
    }

	private void mostrarAGMKruskal() {
        GrafoKruskal ventanaKruskal = new GrafoKruskal(controlador);
        ventanaKruskal.setVisible(true);
    }

    private void mostrarAGMPrim() {
        GrafoPrim ventanaPrim = new GrafoPrim(controlador);
        ventanaPrim.setVisible(true);
    }

  

    private void ejecutarSiEsConexo(Runnable accion) {
        if (!controlador.esConexo()) {
            JOptionPane.showMessageDialog(this, "El grafo no es conexo. No se puede calcular el AGM.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        accion.run();
    }
}
