package interfaz;
/*import controlador.GrafoController;
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
    private GrafoController controlador;

    public MainForm(GrafoController controlador) {
        this.controlador = controlador;

        setTitle("Mapa de Estaciones");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mapa = new JMapViewer();
        mapa.setDisplayPosition(new Coordinate(-25.6953, -54.4367), 12);
        mapa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Coordinate coord = (Coordinate) mapa.getPosition(e.getPoint());
                String nombre = JOptionPane.showInputDialog("Nombre de la estación:");
                if (nombre != null && !nombre.isEmpty()) {
                    Estacion estacion = new Estacion(nombre, coord);
                    controlador.agregarEstacion(estacion);
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
        List<Estacion> estaciones = controlador.getEstaciones();
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

                Aristas arista = new Aristas(inicio, fin, peso);
                if (controlador.existeArista(arista)) {
                    JOptionPane.showMessageDialog(this, "Este sendero ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                controlador.agregarArista(arista);
                JOptionPane.showMessageDialog(this, "Sendero ingresado con éxito.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Impacto ambiental inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void dibujarSenderos() {
        List<Aristas> aristas = controlador.getAristas();

        if (aristas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay senderos para dibujar.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (Aristas arista : aristas) {
            List<Coordinate> coords = new ArrayList<>();
            coords.add(arista.getInicio().getCoordenada());
            coords.add(arista.getFin().getCoordenada());
            coords.add(arista.getInicio().getCoordenada());

            MapPolygonImpl linea = new MapPolygonImpl(coords);
            mapa.addMapPolygon(linea);

            Coordinate medio = obtenerPuntoMedio(arista.getInicio().getCoordenada(), arista.getFin().getCoordenada());
            mapa.addMapMarker(new MapMarkerDot(String.valueOf(arista.getPeso()), medio));
        }
    }

    private Coordinate obtenerPuntoMedio(Coordinate a, Coordinate b) {
        double lat = (a.getLat() + b.getLat()) / 2;
        double lon = (a.getLon() + b.getLon()) / 2;
        return new Coordinate(lat, lon);
    }
}*/
/*
import controlador.GrafoController;
import logica.Aristas;
import logica.GrafoIngresado;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainForm extends JFrame {
    private GrafoController controller;
    private JTextArea resultadoArea;  // Área de texto para mostrar el resultado del AGM

    public MainForm(GrafoController controller) {
        this.controller = controller;
        inicializarUI();
    }

    private void inicializarUI() {
        setTitle("Grafo y Kruskal");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear área de texto para mostrar el resultado del AGM
        resultadoArea = new JTextArea();
        resultadoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultadoArea);
        add(scrollPane, BorderLayout.CENTER);

        // Crear botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());

        JButton botonMostrarAGM = new JButton("Mostrar AGM (Kruskal)");
        botonMostrarAGM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarAGM();
            }
        });

        // Añadir botones al panel
        panelBotones.add(botonMostrarAGM);
        add(panelBotones, BorderLayout.SOUTH);
    }

    // Método para mostrar el AGM usando Kruskal
    private void mostrarAGM() {
        List<Aristas> agm = controller.ejecutarKruskal();
        if (agm != null) {
            // Limpiar el área de texto antes de mostrar el resultado
            resultadoArea.setText("");
            // Mostrar las aristas del AGM
            for (Aristas arista : agm) {
                resultadoArea.append("Inicio: " + arista.getInicio().getNombre() +
                                     ", Fin: " + arista.getFin().getNombre() +
                                     ", Peso: " + arista.getPeso() + "\n");
            }
        } else {
            resultadoArea.setText("El grafo no es conexo.");
        }
    }

    public static void main(String[] args) {
        // Crear el modelo (GrafoIngresado) y el controlador
        GrafoIngresado modelo = new GrafoIngresado();
        GrafoController controller = new GrafoController(modelo);

        // Crear la vista (MainForm) y hacerla visible
        MainForm vista = new MainForm(controller);
        vista.setVisible(true);
    }
}*/
/*
import logica.GrafoIngresado;
import logica.Aristas;
import logica.Bfs;
import logica.Kruskal;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class MainForm extends JFrame {
    private GrafoIngresado modelo;
    private JButton agregarEstacionBtn;
    private JButton agregarAristaBtn;
    private JButton mostrarAGMBtn;
    private JTextArea textArea;
    private JTextField nombreEstacionField;
    private JTextField inicioAristaField;
    private JTextField finAristaField;
    private JTextField pesoAristaField;
    private JTextField origenBfsField;
    private JTextField destinoBfsField;

    public MainForm() {
        modelo = new GrafoIngresado();
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Grafo de Estaciones");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Campos de texto para nombre de estación y aristas
        nombreEstacionField = new JTextField(15);
        inicioAristaField = new JTextField(15);
        finAristaField = new JTextField(15);
        pesoAristaField = new JTextField(5);
        origenBfsField = new JTextField(15);
        destinoBfsField = new JTextField(15);

        // Botones
        agregarEstacionBtn = new JButton("Agregar Estación");
        agregarAristaBtn = new JButton("Agregar Arista");
        mostrarAGMBtn = new JButton("Mostrar AGM con Kruskal");

        // Text area para mostrar información
        textArea = new JTextArea(10, 40);
        textArea.setEditable(false);

        // Agregar eventos a botones
        agregarEstacionBtn.addActionListener(e -> agregarEstacion());
        agregarAristaBtn.addActionListener(e -> agregarArista());
        mostrarAGMBtn.addActionListener(e -> mostrarAGM());

        // Organizar la interfaz
        add(new JLabel("Nombre de Estación:"));
        add(nombreEstacionField);
        add(agregarEstacionBtn);

        add(new JLabel("Inicio Arista:"));
        add(inicioAristaField);
        add(new JLabel("Fin Arista:"));
        add(finAristaField);
        add(new JLabel("Peso Arista:"));
        add(pesoAristaField);
        add(agregarAristaBtn);

        add(new JLabel("Origen BFS:"));
        add(origenBfsField);
        add(new JLabel("Destino BFS:"));
        add(destinoBfsField);

        add(mostrarAGMBtn);

        add(new JScrollPane(textArea));

        setVisible(true);
    }

    private void agregarEstacion() {
        String nombre = nombreEstacionField.getText().trim();
        if (!nombre.isEmpty()) {
            modelo.agregarEstacion(nombre);
            nombreEstacionField.setText("");
            mostrarGrafo();
        } else {
            JOptionPane.showMessageDialog(this, "El nombre de la estación no puede estar vacío.");
        }
    }

    private void agregarArista() {
        String inicio = inicioAristaField.getText().trim();
        String fin = finAristaField.getText().trim();
        String pesoText = pesoAristaField.getText().trim();

        if (!inicio.isEmpty() && !fin.isEmpty() && !pesoText.isEmpty()) {
            try {
                int peso = Integer.parseInt(pesoText);
                modelo.agregarArista(new Aristas(inicio, fin, peso));
                inicioAristaField.setText("");
                finAristaField.setText("");
                pesoAristaField.setText("");
                mostrarGrafo();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El peso de la arista debe ser un número válido.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Todos los campos de arista deben estar completos.");
        }
    }

    private void mostrarGrafo() {
        textArea.setText("");
        for (Aristas arista : modelo.getAristas()) {
            textArea.append(arista.toString() + "\n");
        }
    }

    private void mostrarAGM() {
        Kruskal kruskal = new Kruskal(modelo);
        List<Aristas> agm = kruskal.encontrarAGM();
        if (agm != null) {
            textArea.setText("Árbol Generador Mínimo (AGM):\n");
            for (Aristas arista : agm) {
                textArea.append(arista.toString() + "\n");
            }
        } else {
            JOptionPane.showMessageDialog(this, "El grafo no es conexo.");
        }
    }
}*/

import controlador.GrafoController;
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
                Coordinate coord = (Coordinate)mapa.getPosition(e.getPoint());
                String nombre = JOptionPane.showInputDialog("Nombre de la estación:");

                if (nombre != null && !nombre.isEmpty()) {
                    controlador.agregarEstacion(nombre, coord);
                    mapa.addMapMarker(new MapMarkerDot(nombre, coord));
                }
            }
        });

        // Botones
        botonIngresarSenderos = new JButton("Ingresar Senderos");
        botonIngresarSenderos.addActionListener(e -> ingresarSendero());

        botonDibujarSenderos = new JButton("Dibujar Senderos");
        botonDibujarSenderos.addActionListener(e -> dibujarSenderos());

        botonVerAGM = new JButton("Ver AGM de Kruskal");
        botonVerAGM.addActionListener(e -> mostrarAGMKruskal());

        JPanel panelBotones = new JPanel();
        panelBotones.add(botonIngresarSenderos);
        panelBotones.add(botonDibujarSenderos);
        panelBotones.add(botonVerAGM);

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
            mapa.addMapMarker(new MapMarkerDot(impacto, medio));
        }
    }

    private void mostrarAGMKruskal() {
        GrafoKruskal ventanaKruskal = new GrafoKruskal(controlador);
        ventanaKruskal.setVisible(true);
    }

    private Coordinate obtenerPuntoMedio(Coordinate a, Coordinate b) {
        double lat = (a.getLat() + b.getLat()) / 2;
        double lon = (a.getLon() + b.getLon()) / 2;
        return new Coordinate(lat, lon);
    }
}