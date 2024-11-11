import discografica.*;
import exceptions.ArtistaNoEncontradoException;
import persistencia.Serializacion;
import reportes.Reporte;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Clase GUI que implementa la interfaz gráfica de usuario para la gestión de una discográfica.
 * Permite la carga, consulta y manipulación de datos de artistas, discos y liquidaciones mensuales.
 */
public class GUI {
    static Discografica Spotify = new Discografica();

    public static void main(String[] args) {

        Discografica ob = Serializacion.cargarObjeto("discografica.dat", Discografica.class);
        if(ob != null && !ob.getArtistas().isEmpty()){
            Spotify = ob;
        }

        // Crear el marco (JFrame)
        JFrame frame = new JFrame("Gestion discografica");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500); // Tamaño del marco

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Llamada para guardar el estado de Spotify al cerrar la ventana
                Serializacion.guardarObjeto(Spotify, "discografica.dat");
                System.out.println("Datos guardados exitosamente.");
                System.exit(0);  // Cerrar la aplicación después de guardar
            }
        });

        // Crear un panel principal con CardLayout para cambiar entre pantallas
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        // Crear el panel de inicio (Pantalla principal)
        JPanel startPanel = new JPanel(new BorderLayout());

        JLabel tituloLabel = new JLabel("Gestion discografica", SwingConstants.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 24));
        startPanel.add(tituloLabel, BorderLayout.NORTH);

        // Crear el JLabel para la imagen
        ImageIcon originalIcon = new ImageIcon("src/resources/Logo.png");
        // Escalar la imagen para que sea más pequeña
        Image scaledImage = originalIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(scaledIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER); // Centrar la imagen horizontalmente

        // Agregar la imagen al centro del panel de inicio
        startPanel.add(imageLabel, BorderLayout.CENTER);

        setUpBotones(startPanel, mainPanel, cardLayout, frame);

        // Configurar el marco
        frame.add(mainPanel);
        frame.setLocationRelativeTo(null); // Centrar el marco en la pantalla
        frame.setVisible(true);
    }

    /**
     * Configura los botones en el panel de inicio, permitiendo la navegación a otras secciones.
     *
     * @param startPanel Panel inicial que contiene los botones.
     * @param mainPanel Panel principal que contiene todos los sub-paneles.
     * @param cardLayout Administrador de diseño para cambiar entre paneles.
     * @param frame Marco principal de la aplicación.
     */
    public static void setUpBotones(JPanel startPanel, JPanel mainPanel, CardLayout cardLayout, JFrame frame) {
        // Crear un panel para los botones y usar GridLayout
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 0, 90)); // 3 filas, 1 columna, 10 píxeles de espacio vertical
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 75)); // Espacio alrededor del panel de botone

        // Crear los botones
        JButton gestionDeArtistasButton = new JButton("Gestión de artistas");
        JButton reportesButton = new JButton("Reportes");
        JButton salirButton = new JButton("Salir");

        // Agregar los botones al panel de botones
        buttonPanel.add(gestionDeArtistasButton);
        buttonPanel.add(reportesButton);
        buttonPanel.add(salirButton);

        // Agregar el panel de botones al lado derecho del panel de inicio
        startPanel.add(buttonPanel, BorderLayout.EAST);

        // Agregar el panel de inicio al panel principal
        mainPanel.add(startPanel, "StartPanel");

        // Crear el segundo panel (Pantalla para "Gestión de artistas" e "Informes")
        JPanel artistaPanel = new JPanel(new FlowLayout());



        // Agregar el segundo panel al panel principal
        MenuArtistas(startPanel, mainPanel, artistaPanel, cardLayout, frame, gestionDeArtistasButton);

        reportesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reportes(mainPanel, cardLayout, frame);
            }
        });


        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cerrar la aplicación cuando se presiona el botón "Salir"
                if(Spotify != null && !Spotify.getArtistas().isEmpty()) {
                    Serializacion.guardarObjeto(Spotify, "discografica.dat");
                }
                frame.dispose();
            }
        });
    }

    /**
     * Configura el menú de gestión de artistas y sus opciones (carga, revisión, consulta, eliminación, etc.).
     *
     * @param startPanel Panel de inicio.
     * @param mainPanel Panel principal que contiene todos los sub-paneles.
     * @param artistaPanel Panel específico para la gestión de artistas.
     * @param cardLayout Administrador de diseño para cambiar entre paneles.
     * @param frame Marco principal de la aplicación.
     * @param gestionDeArtistasButton Botón que permite acceder a la gestión de artistas.
     */
    public static void MenuArtistas (JPanel startPanel, JPanel mainPanel, JPanel artistaPanel, CardLayout cardLayout, JFrame frame, JButton gestionDeArtistasButton){
        mainPanel.add(artistaPanel, "artistaPanel");

        JPanel buttonPanelArtistas = new JPanel(new GridLayout(6, 1, 0, 40));
        buttonPanelArtistas.setBorder(BorderFactory.createEmptyBorder(45, 0, 15, 0)); // Espacio alrededor del panel de botones
        JButton agregarArtistasButton = new JButton("Cargar artistas");
        JButton revisarArtistasButton = new JButton("Revisar todos los artistas");
        JButton consultaArtistasButton = new JButton("Consultar datos de artistas");
        JButton eliminarArtistaButton = new JButton("Eliminar artista");
        JButton liquidacionesButton = new JButton("Generar liquidacion");
        JButton volverButton = new JButton("Volver");

        buttonPanelArtistas.add(agregarArtistasButton);
        SeccionCarga(mainPanel, cardLayout, frame, gestionDeArtistasButton, agregarArtistasButton);

        buttonPanelArtistas.add(revisarArtistasButton);
        SeccionComprobacion(mainPanel, cardLayout, frame, revisarArtistasButton);

        buttonPanelArtistas.add(consultaArtistasButton);
        SeccionConsulta(mainPanel, artistaPanel,cardLayout, consultaArtistasButton);

        buttonPanelArtistas.add(eliminarArtistaButton);
        SeccionElimina(mainPanel, cardLayout, frame, eliminarArtistaButton);


        buttonPanelArtistas.add(liquidacionesButton);
        SeccionLiquidacion(mainPanel, cardLayout, frame, liquidacionesButton);

        buttonPanelArtistas.add(volverButton);
        artistaPanel.add(buttonPanelArtistas, BorderLayout.CENTER);


        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "StartPanel");
            }
        });
    }

    /**
     * Configura la sección de carga de datos de artistas.
     *
     * @param mainPanel Panel principal que contiene todos los sub-paneles.
     * @param cardLayout Administrador de diseño para cambiar entre paneles.
     * @param frame Marco principal de la aplicación.
     * @param gestionDeArtistasButton Botón para acceder a la sección de gestión de artistas.
     * @param agregarArtistasButton Botón que permite cargar datos de nuevos artistas.
     */
    public static void SeccionCarga(JPanel mainPanel, CardLayout cardLayout, JFrame frame, JButton gestionDeArtistasButton, JButton agregarArtistasButton) {
        gestionDeArtistasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "artistaPanel");

                agregarArtistasButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JPanel cargaPanel = new JPanel(new GridBagLayout());
                        mainPanel.add(cargaPanel, "cargaPanel");
                        cardLayout.show(mainPanel, "cargaPanel");

                        JLabel tituloLabel = new JLabel("Carga artistas");
                        tituloLabel.setFont(new Font("Arial", Font.BOLD, 24));
                        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);

                        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
                        JTextField archivoTextField = new JTextField("Ingrese el nombre del archivo con los artistas:", 30);

                        archivoTextField.addFocusListener(new FocusListener() {
                            @Override
                            public void focusGained(FocusEvent e) {
                                if (archivoTextField.getText().equals("Ingrese el nombre del archivo con los artistas:")) {
                                    archivoTextField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(FocusEvent e) {
                                if (archivoTextField.getText().isEmpty()) {
                                    archivoTextField.setText("Ingrese el nombre del archivo con los artistas:");
                                }
                            }
                        });

                        // Agregar el campo de texto al panel de entrada
                        inputPanel.add(archivoTextField);
                        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Espaciado horizontal entre los botones

                        JButton enviarButton = new JButton("Aceptar");
                        enviarButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                String Path = archivoTextField.getText() + ".xml";
                                try {
                                    Spotify.CargaDatos(Path);
                                    JOptionPane.showMessageDialog(frame, "El archivo se cargó de forma correcta.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                                    cardLayout.show(mainPanel, "artistaPanel");
                                } catch (FileNotFoundException ex) {
                                    JOptionPane.showMessageDialog(frame, "El archivo no existe.", "Error", JOptionPane.INFORMATION_MESSAGE);
                                }
                            }
                        });

                        JButton volverButton = new JButton("Volver");
                        volverButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                cardLayout.show(mainPanel, "artistaPanel");
                            }
                        });

                        enviarButton.setPreferredSize(new Dimension(140, 30));
                        volverButton.setPreferredSize(new Dimension(140, 30));

                        buttonPanel.add(enviarButton);
                        buttonPanel.add(volverButton);

                        GridBagConstraints gbc = new GridBagConstraints();
                        gbc.gridx = 0;
                        gbc.gridy = 0;
                        gbc.anchor = GridBagConstraints.CENTER;
                        gbc.insets = new Insets(10, 0, 20, 0);

                        // Agregar el título al panel de carga
                        cargaPanel.add(tituloLabel, gbc);

                        // Configurar las posiciones del inputPanel y buttonPanel
                        gbc.gridy = 1;
                        cargaPanel.add(inputPanel, gbc);

                        gbc.gridy = 2;
                        cargaPanel.add(buttonPanel, gbc);
                    }
                });
            }
        });
    }

    /**
     * Configura la sección de revisión de todos los artistas y muestra detalles de cada uno.
     *
     * @param mainPanel Panel principal que contiene todos los sub-paneles.
     * @param cardLayout Administrador de diseño para cambiar entre paneles.
     * @param frame Marco principal de la aplicación.
     * @param revisarArtistasButton Botón que permite revisar los datos de todos los artistas.
     */
    public static void SeccionComprobacion(JPanel mainPanel, CardLayout cardLayout, JFrame frame, JButton revisarArtistasButton) {
        revisarArtistasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final ArrayList<Artista> revisionArtistas = Spotify.listarTodosArtistas();
                JPanel revisionPanel = new JPanel(new BorderLayout());
                mainPanel.add(revisionPanel, "revisionPanel");
                cardLayout.show(mainPanel, "revisionPanel");

                JLabel tituloLabel = new JLabel("Consultar datos", SwingConstants.CENTER);
                tituloLabel.setFont(new Font("Arial", Font.BOLD, 24));
                revisionPanel.add(tituloLabel, BorderLayout.NORTH);

                JPanel volverPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                JButton volverButton = new JButton("Volver");
                volverButton.setPreferredSize(new Dimension(240, 25));
                volverPanel.add(volverButton);
                revisionPanel.add(volverPanel, BorderLayout.SOUTH);

                volverButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cardLayout.show(mainPanel, "artistaPanel");
                    }
                });

                if (!revisionArtistas.isEmpty()) {
                    JComboBox<String> artistaComboBox = new JComboBox<>();
                    artistaComboBox.setBackground(Color.WHITE); // Establecer fondo blanco
                    for (Artista artista : revisionArtistas) {
                        artistaComboBox.addItem(artista.getNombre());
                    }
                    artistaComboBox.setPreferredSize(new Dimension(180, 30)); // Ajuste vertical
                    artistaComboBox.setMaximumSize(new Dimension(180, 30)); // Establecer tamaño máximo

                    JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    topPanel.add(artistaComboBox);

                    JPanel detallesPanel = new JPanel();
                    detallesPanel.setLayout(new GridLayout(3, 2, 10, -5)); // 3 filas, 2 columnas, espacio de 10px

                    Border labelBorder = BorderFactory.createLineBorder(Color.GRAY);

                    JLabel idLabel = new JLabel("ID: ");
                    idLabel.setBorder(labelBorder);
                    JLabel idValue = new JLabel();
                    idValue.setBorder(labelBorder);
                    idValue.setHorizontalAlignment(SwingConstants.CENTER);
                    JLabel generoLabel = new JLabel("Género: ");
                    generoLabel.setBorder(labelBorder);
                    JLabel generoValue = new JLabel();
                    generoValue.setBorder(labelBorder);
                    generoValue.setHorizontalAlignment(SwingConstants.CENTER);
                    JLabel integrantesLabel = new JLabel("Integrantes: ");
                    integrantesLabel.setBorder(labelBorder);
                    JLabel integrantesValue = new JLabel();
                    integrantesValue.setBorder(labelBorder);
                    integrantesValue.setHorizontalAlignment(SwingConstants.CENTER);

                    detallesPanel.add(idLabel);
                    detallesPanel.add(idValue);
                    detallesPanel.add(generoLabel);
                    detallesPanel.add(generoValue);
                    detallesPanel.add(integrantesLabel);
                    detallesPanel.add(integrantesValue);

                    JPanel mainDetailsPanel = new JPanel();
                    mainDetailsPanel.setLayout(new BoxLayout(mainDetailsPanel, BoxLayout.Y_AXIS));
                    mainDetailsPanel.add(topPanel);
                    mainDetailsPanel.add(detallesPanel);

                    revisionPanel.add(mainDetailsPanel, BorderLayout.CENTER);

                    final Artista[] selectedArtista = new Artista[1];
                    artistaComboBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int selectedIndex = artistaComboBox.getSelectedIndex();
                            if (selectedIndex != -1) {
                                selectedArtista[0] = revisionArtistas.get(selectedIndex);
                                idValue.setText(selectedArtista[0].getID());
                                generoValue.setText(selectedArtista[0].getGenero());
                                integrantesValue.setText(String.valueOf(selectedArtista[0].getCantIntegrantes()));
                            }
                        }
                    });

                    // Seleccionar el primer artista por defecto
                    artistaComboBox.setSelectedIndex(0);
                    Artista firstArtista = revisionArtistas.get(0);
                    idValue.setText(firstArtista.getID());
                    generoValue.setText(firstArtista.getGenero());
                    integrantesValue.setText(String.valueOf(firstArtista.getCantIntegrantes()));

                    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    JButton discosButton = new JButton("Mostrar discos");
                    JButton recitalesButton = new JButton("Mostrar recitales");
                    JButton cancionesButton = new JButton("Mostrar canciones");
                    buttonPanel.add(discosButton);
                    buttonPanel.add(recitalesButton);
                    buttonPanel.add(cancionesButton);
                    topPanel.add(buttonPanel);

                    JPanel volverSubMenuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    JButton volverSubMenuButton = new JButton("Volver");
                    volverSubMenuButton.setPreferredSize(new Dimension(240, 25));
                    volverSubMenuPanel.add(volverSubMenuButton);

                    volverSubMenuButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            cardLayout.show(mainPanel, "revisionPanel");
                        }
                    });

                    JPanel subMenuPanel = new JPanel(new BorderLayout());
                    mainPanel.add(subMenuPanel, "subMenuPanel");

                    JPanel tablaPanel = new JPanel(new BorderLayout());
                    discosButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            subMenuPanel.removeAll();
                            subMenuPanel.add(volverSubMenuPanel, BorderLayout.SOUTH);
                            cardLayout.show(mainPanel, "subMenuPanel");
                            HashSet<Disco> revisionDiscos;
                            revisionDiscos = selectedArtista[0].getDiscos();
                            String[] columnas = {"Nombre", "Unidades vendidas"};
                            DefaultTableModel tablaModelo = new DefaultTableModel(columnas, 0) {
                                @Override
                                public boolean isCellEditable(int row, int column) {
                                    return false; // Desactiva la edición de celdas
                                }
                            };
                            for (Disco discos : revisionDiscos) {
                                Object[] Data = {discos.getNombre(), discos.getUnidadesVendidas()};
                                tablaModelo.addRow(Data);
                            }
                            JTable tabla = new JTable(tablaModelo);
                            tabla.getTableHeader().setReorderingAllowed(false);
                            JScrollPane scrollPane = new JScrollPane(tabla);
                            tabla.setRowHeight(tabla.getRowCount() - 1, 20);

                            tablaPanel.removeAll();
                            tablaPanel.add(scrollPane, BorderLayout.CENTER);
                            subMenuPanel.add(tablaPanel, BorderLayout.CENTER);
                            subMenuPanel.revalidate();
                            subMenuPanel.repaint();
                        }
                    });

                    recitalesButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            subMenuPanel.removeAll();
                            subMenuPanel.add(volverSubMenuPanel, BorderLayout.SOUTH);
                            cardLayout.show(mainPanel, "subMenuPanel");

                            final ArrayList<Recital> recitalesRevision = selectedArtista[0].getRecitales();
                            String[] columnas = {"Fecha", "Monto recuadado", "Costo de produccion"};
                            DefaultTableModel tablaModelo = new DefaultTableModel(columnas, 0) {
                                @Override
                                public boolean isCellEditable(int row, int column) {
                                    return false; // Desactiva la edición de celdas
                                }
                            };

                            for (Recital recitales : recitalesRevision) {
                                Object[] Data = {recitales.GetFecha(), recitales.GetRecaudacion(), recitales.GetCostoProduccion()};
                                tablaModelo.addRow(Data);
                            }

                            JTable tabla = new JTable(tablaModelo);
                            tabla.getTableHeader().setReorderingAllowed(false);
                            JScrollPane scrollPane = new JScrollPane(tabla);
                            tabla.setRowHeight(tabla.getRowCount() - 1, 20);

                            tablaPanel.removeAll();
                            tablaPanel.add(scrollPane, BorderLayout.CENTER);
                            subMenuPanel.add(tablaPanel, BorderLayout.CENTER);
                            subMenuPanel.revalidate();
                            subMenuPanel.repaint();
                        }
                    });

                    cancionesButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            subMenuPanel.removeAll();
                            subMenuPanel.add(volverSubMenuPanel, BorderLayout.SOUTH);
                            cardLayout.show(mainPanel, "subMenuPanel");
                            HashSet<Disco> revisionDiscos;
                            revisionDiscos = selectedArtista[0].getDiscos();

                            String[] columnas = {"Nombre", "Duracion", "Cantidad de reproducciones"};
                            DefaultTableModel tablaModelo = new DefaultTableModel(columnas, 0) {
                                @Override
                                public boolean isCellEditable(int row, int column) {
                                    return false; // Desactiva la edición de celdas
                                }
                            };

                            for (Disco discos : revisionDiscos) {
                                final ArrayList<Cancion> cancionesRevision = discos.getCanciones();
                                for (Cancion canciones : cancionesRevision) {
                                    Object[] Data = {canciones.getNombre(), canciones.getDuracion(), canciones.getCantReproducciones()};
                                    tablaModelo.addRow(Data);
                                }
                            }



                            JTable tabla = new JTable(tablaModelo);
                            tabla.getTableHeader().setReorderingAllowed(false);
                            JScrollPane scrollPane = new JScrollPane(tabla);
                            tabla.setRowHeight(tabla.getRowCount() - 1, 20);

                            tablaPanel.removeAll();
                            tablaPanel.add(scrollPane, BorderLayout.CENTER);
                            subMenuPanel.add(tablaPanel, BorderLayout.CENTER);
                            subMenuPanel.revalidate();
                            subMenuPanel.repaint();
                        }
                    });

                } else {
                    JOptionPane.showMessageDialog(revisionPanel, "No se encontraron artistas.", "Error", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    /**
     * Configura la sección de liquidación mensual, permitiendo seleccionar mes, año y artista desde JComboBox.
     *
     * @param mainPanel Panel principal que contiene todos los sub-paneles.
     * @param cardLayout Administrador de diseño para cambiar entre paneles.
     * @param frame Marco principal de la aplicación.
     * @param liquidacionesButton Botón que permite acceder a la sección de liquidaciones.
     */
    public static void SeccionLiquidacion(JPanel mainPanel, CardLayout cardLayout, JFrame frame, JButton liquidacionesButton) {
        liquidacionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel liquidacionPanel = new JPanel(new BorderLayout());
                mainPanel.add(liquidacionPanel, "liquidacionPanel");
                cardLayout.show(mainPanel, "liquidacionPanel");

                JLabel tituloLabel = new JLabel("Liquidacion mensual", SwingConstants.CENTER);
                tituloLabel.setFont(new Font("Arial", Font.BOLD, 24));
                liquidacionPanel.add(tituloLabel, BorderLayout.NORTH);

                // Crear JComboBox para el mes, año y nombre del artista
                JPanel filtrosPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

                // ComboBox de Mes
                JLabel mesLabel = new JLabel("Mes: ");
                JComboBox<String> mesComboBox = new JComboBox<>(new String[]{
                        "01 - Enero", "02 - Febrero", "03 - Marzo", "04 - Abril", "05 - Mayo", "06 - Junio",
                        "07 - Julio", "08 - Agosto", "09 - Septiembre", "10 - Octubre", "11 - Noviembre", "12 - Diciembre"
                });

                // ComboBox de Año
                JLabel yearLabel = new JLabel("Año: ");
                JComboBox<String> yearComboBox = new JComboBox<>();
                int currentYear = LocalDate.now().getYear();
                for (int i = currentYear; i >= currentYear - 10; i--) {
                    yearComboBox.addItem(String.valueOf(i));
                }

                // ComboBox para seleccionar el nombre del artista
                JLabel artistaLabel = new JLabel("Artista: ");
                JComboBox<String> artistaComboBox = new JComboBox<>();
                for (Artista artista : Spotify.listarTodosArtistas()) {
                    artistaComboBox.addItem(artista.getNombre());
                }

                JButton enviarButton = new JButton("Generar");
                enviarButton.setPreferredSize(new Dimension(90, 20));

                filtrosPanel.add(mesLabel);
                filtrosPanel.add(mesComboBox);
                filtrosPanel.add(yearLabel);
                filtrosPanel.add(yearComboBox);
                filtrosPanel.add(artistaLabel);
                filtrosPanel.add(artistaComboBox);
                filtrosPanel.add(enviarButton);
                liquidacionPanel.add(filtrosPanel, BorderLayout.CENTER);

                JPanel volverPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                JButton volverButton = new JButton("Volver");
                volverButton.setPreferredSize(new Dimension(240, 25));
                volverPanel.add(volverButton);
                liquidacionPanel.add(volverPanel, BorderLayout.SOUTH);

                volverButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cardLayout.show(mainPanel, "artistaPanel");
                    }
                });

                // Panel para mostrar la liquidación generada
                JPanel muestraLiquidacionPanel = new JPanel(new BorderLayout());
                mainPanel.add(muestraLiquidacionPanel, "muestraLiquidacionPanel");

                enviarButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Limpiar el panel muestraLiquidacionPanel antes de agregar nuevos componentes
                        muestraLiquidacionPanel.removeAll();

                        try {
                            String mesSeleccionado = mesComboBox.getSelectedItem().toString().substring(0, 2); // Extraer el número de mes
                            String añoSeleccionado = yearComboBox.getSelectedItem().toString();
                            String nombreSeleccionado = artistaComboBox.getSelectedItem().toString();

                            try {
                                Artista artista = Spotify.buscarArtistaPorNombre(nombreSeleccionado);
                                YearMonth fecha = YearMonth.parse(mesSeleccionado + "-" + añoSeleccionado, DateTimeFormatter.ofPattern("MM-yyyy"));

                                JLabel tituloLabel = new JLabel("Liquidacion mensual", SwingConstants.CENTER);
                                tituloLabel.setFont(new Font("Arial", Font.BOLD, 24));
                                muestraLiquidacionPanel.add(tituloLabel, BorderLayout.NORTH);

                                ArrayList<ObjetoLiquidacion> mensualDisco = artista.getLiquidacion(fecha).getLiquidacionDisco();
                                ArrayList<ObjetoLiquidacion> mensualReproducciones = artista.getLiquidacion(fecha).getLiquidacionReproducciones();
                                ArrayList<ObjetoLiquidacion> mensualRecitales = artista.getLiquidacion(fecha).getLiquidacionRecitales();
                                float total = 0;

                                JPanel tablaPanel = new JPanel(new BorderLayout());
                                String[] columnas = {"Concepto", "Importe"};
                                DefaultTableModel tablaModelo = new DefaultTableModel(columnas, 0) {
                                    @Override
                                    public boolean isCellEditable(int row, int column) {
                                        return false;
                                    }
                                };

                                for (ObjetoLiquidacion discos : mensualDisco) {
                                    tablaModelo.addRow(new Object[]{"Disco: " + discos.getDescripcion(), discos.getMonto()});
                                    total += discos.getMonto();
                                }
                                for (ObjetoLiquidacion canciones : mensualReproducciones) {
                                    tablaModelo.addRow(new Object[]{"Cancion: " + canciones.getDescripcion(), canciones.getMonto()});
                                    total += canciones.getMonto();
                                }
                                for (ObjetoLiquidacion recitales : mensualRecitales) {
                                    tablaModelo.addRow(new Object[]{"Recital el dia " + recitales.getDescripcion(), recitales.getMonto()});
                                    total += recitales.getMonto();
                                }

                                tablaModelo.addRow(new Object[]{"Total: ", total});

                                JTable tabla = new JTable(tablaModelo);
                                tabla.getTableHeader().setReorderingAllowed(false);
                                JScrollPane scrollPane = new JScrollPane(tabla);

                                tablaPanel.add(scrollPane, BorderLayout.CENTER);
                                muestraLiquidacionPanel.add(tablaPanel, BorderLayout.CENTER);

                                JPanel volverMuestraPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                                JButton volverMuestraButton = new JButton("Volver");
                                volverMuestraButton.setPreferredSize(new Dimension(240, 25));
                                volverMuestraPanel.add(volverMuestraButton);
                                muestraLiquidacionPanel.add(volverMuestraPanel, BorderLayout.SOUTH);

                                volverMuestraButton.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        cardLayout.show(mainPanel, "liquidacionPanel");
                                    }
                                });

                                muestraLiquidacionPanel.revalidate();
                                muestraLiquidacionPanel.repaint();

                                cardLayout.show(mainPanel, "muestraLiquidacionPanel");

                            } catch (ArtistaNoEncontradoException ex) {
                                JOptionPane.showMessageDialog(frame, "No se encontró el artista: " + nombreSeleccionado, "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (NullPointerException ex) {
                            JOptionPane.showMessageDialog(frame, "No hay artistas cargados.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
            }
        });
    }

    /**
     * Configura la sección de consulta de datos de artistas, permitiendo realizar búsquedas filtradas.
     *
     * @param mainPanel Panel principal que contiene todos los sub-paneles.
     * @param artistaPanel Panel específico para la gestión de artistas.
     * @param cardLayout Administrador de diseño para cambiar entre paneles.
     * @param consultaArtistasButton Botón que permite acceder a la consulta de artistas.
     */
    public static void SeccionConsulta(JPanel mainPanel, JPanel artistaPanel, CardLayout cardLayout, JButton consultaArtistasButton) {
        consultaArtistasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JPanel buscaPanel = new JPanel(new BorderLayout());
                mainPanel.add(buscaPanel, "buscaPanel");
                cardLayout.show(mainPanel, "buscaPanel");

                JLabel tituloLabel = new JLabel("Consultar datos", SwingConstants.CENTER);
                tituloLabel.setFont(new Font("Arial", Font.BOLD, 24));
                buscaPanel.add(tituloLabel, BorderLayout.NORTH);

                JPanel filtrosPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
                JLabel generoLabel = new JLabel("Genero: ");
                JTextField generoText = new JTextField(15);
                JLabel integrantesLabel = new JLabel("Cantidad de integrantes: ");
                JTextField integrantesText = new JTextField(15);

                JButton enviarButton = new JButton("Enviar");
                enviarButton.setPreferredSize(new Dimension(90, 20));
                filtrosPanel.add(generoLabel);
                filtrosPanel.add(generoText);
                filtrosPanel.add(integrantesLabel);
                filtrosPanel.add(integrantesText);
                filtrosPanel.add(enviarButton);

                buscaPanel.add(filtrosPanel, BorderLayout.CENTER);

                JButton volverButton = new JButton("Volver");
                volverButton.setPreferredSize(new Dimension(90, 20));
                JPanel volverPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                volverPanel.add(volverButton);
                buscaPanel.add(volverPanel, BorderLayout.SOUTH);

                JButton volverTablaButton = new JButton("Volver");
                volverTablaButton.setPreferredSize(new Dimension(90, 20));
                JPanel volverTablaPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                volverTablaPanel.add(volverTablaButton);

                JPanel tablaPanel = new JPanel(new BorderLayout());

                enviarButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ArrayList<Artista> datosArtistas = new ArrayList<>();
                        String genero = generoText.getText();
                        String integrantes = integrantesText.getText();
                        if(Spotify != null && !Spotify.getArtistas().isEmpty()) {
                            if(integrantes.isEmpty() && genero.isEmpty()) {
                                JOptionPane.showMessageDialog(artistaPanel, "Complete uno o ambos campos.", "Error", JOptionPane.INFORMATION_MESSAGE);
                                datosArtistas = null;
                            }else {
                                if (integrantes.isEmpty())
                                    datosArtistas = Spotify.consultaDatos(genero);
                                else {
                                    if (genero.isEmpty())
                                        datosArtistas = Spotify.consultaDatos(Integer.parseInt(integrantes));
                                    else {
                                        datosArtistas = Spotify.consultaDatos(genero, Integer.parseInt(integrantes));
                                    }
                                }
                            }
                            if(datosArtistas != null) {
                                if (!datosArtistas.isEmpty()) {
                                    String[] columnas = {"ID", "Nombre", "Cantidad de integrantes", "Genero"};
                                    DefaultTableModel tablaModelo = new DefaultTableModel(columnas, 0) {
                                        @Override
                                        public boolean isCellEditable(int row, int column) {
                                            return false; // Desactiva la edición de celdas
                                        }
                                    };

                                    for (Artista artista : datosArtistas) {
                                        Object[] Data = {artista.getID(), artista.getNombre(), artista.getCantIntegrantes(), artista.getGenero()};
                                        tablaModelo.addRow(Data);
                                    }

                                    JTable tabla = new JTable(tablaModelo);
                                    tabla.getTableHeader().setReorderingAllowed(false);
                                    JScrollPane scrollPane = new JScrollPane(tabla);

                                    tablaPanel.removeAll();
                                    tablaPanel.add(scrollPane, BorderLayout.CENTER);
                                    buscaPanel.remove(filtrosPanel);
                                    buscaPanel.add(volverTablaPanel, BorderLayout.SOUTH);
                                    buscaPanel.remove(volverPanel);
                                    buscaPanel.add(tablaPanel, BorderLayout.CENTER);
                                    buscaPanel.revalidate();
                                    buscaPanel.repaint();

                                } else {
                                    JOptionPane.showMessageDialog(artistaPanel, "No se encontraron artistas con esas condiciones.", "Error", JOptionPane.INFORMATION_MESSAGE);
                                }
                            }
                        }else {
                            JOptionPane.showMessageDialog(artistaPanel, "Los artistas no han sido cargados.", "Error", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                });

                volverButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cardLayout.show(mainPanel, "artistaPanel");
                    }
                });

                volverTablaButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Volver a mostrar el panel de filtros
                        buscaPanel.remove(tablaPanel);
                        buscaPanel.add(filtrosPanel, BorderLayout.CENTER);
                        buscaPanel.add(volverPanel, BorderLayout.SOUTH);
                        buscaPanel.remove(volverTablaPanel);
                        buscaPanel.revalidate();
                        buscaPanel.repaint();
                    }
                });


            }
        });
    }

    /**
     * Configura la sección de eliminación de artistas.
     *
     * @param mainPanel Panel principal que contiene todos los sub-paneles.
     * @param cardLayout Administrador de diseño para cambiar entre paneles.
     * @param frame Marco principal de la aplicación.
     * @param eliminarArtistaButton Botón que permite acceder a la sección de eliminación de artistas.
     */
    public static void SeccionElimina(JPanel mainPanel, CardLayout cardLayout, JFrame frame, JButton eliminarArtistaButton) {
        eliminarArtistaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel eliminarPanel = new JPanel(new BorderLayout());
                mainPanel.add(eliminarPanel, "eliminaPanel");
                cardLayout.show(mainPanel, "eliminaPanel");

                JLabel tituloLabel = new JLabel("Baja de artistas", SwingConstants.CENTER);
                tituloLabel.setFont(new Font("Arial", Font.BOLD, 24));
                eliminarPanel.add(tituloLabel, BorderLayout.NORTH);

                // Panel para los datos del artista a eliminar
                JPanel datosPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 60));
                JLabel artistaLabel = new JLabel("Seleccione un artista: ");

                // Crear JComboBox con los nombres de los artistas
                JComboBox<String> artistaComboBox = new JComboBox<>();
                for (Artista artista : Spotify.listarTodosArtistas()) {
                    artistaComboBox.addItem(artista.getNombre());
                }

                JButton enviarButton = new JButton("Eliminar");
                enviarButton.setPreferredSize(new Dimension(90, 20));

                // Agregar el panel de "Volver"
                JPanel volverPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                JButton volverButton = new JButton("Volver");
                volverButton.setPreferredSize(new Dimension(90, 20));
                volverPanel.add(volverButton);
                eliminarPanel.add(volverPanel, BorderLayout.SOUTH);

                // Acción del botón "Eliminar"
                enviarButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String nombreSeleccionado = (String) artistaComboBox.getSelectedItem();
                        if(nombreSeleccionado!=null) {
                            try {
                                Artista artista = Spotify.buscarArtistaPorNombre(nombreSeleccionado);
                                if (artista != null) {
                                    Spotify.bajaArtista(artista.getID());
                                    JOptionPane.showMessageDialog(frame, "El artista se dio de baja de forma correcta.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                                    cardLayout.show(mainPanel, "artistaPanel");
                                }
                            } catch (ArtistaNoEncontradoException ex) {
                                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(frame, "No hay artistas cargados", "Error", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                });

                // Acción del botón "Volver"
                volverButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cardLayout.show(mainPanel, "artistaPanel");
                    }
                });

                // Añadir elementos al panel
                datosPanel.add(artistaLabel);
                datosPanel.add(artistaComboBox);
                datosPanel.add(enviarButton);
                eliminarPanel.add(datosPanel, BorderLayout.CENTER);
            }
        });
    }

    /**
     * Configura la sección de reportes, permitiendo generar y mostrar reportes de canciones y discos.
     *
     * @param mainPanel Panel principal que contiene todos los sub-paneles.
     * @param cardLayout Administrador de diseño para cambiar entre paneles.
     * @param frame Marco principal de la aplicación.
     */
    public static void reportes(JPanel mainPanel, CardLayout cardLayout, JFrame frame) {
        JPanel reportesPanel = new JPanel(new BorderLayout());
        mainPanel.add(reportesPanel, "reportesPanel");
        cardLayout.show(mainPanel, "reportesPanel");

        JLabel tituloLabel = new JLabel("Reportes");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 24));
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        reportesPanel.add(tituloLabel, BorderLayout.NORTH);

        JPanel tipoReportePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 60));

        JButton topCancionesButton = new JButton("Reporte top 10 canciones");
        topCancionesButton.setPreferredSize(new Dimension(260, 25));
        JButton discosVendidosButton = new JButton("Reporte unidades vendidas por disco");
        discosVendidosButton.setPreferredSize(new Dimension(260, 25));
        tipoReportePanel.add(topCancionesButton);
        tipoReportePanel.add(discosVendidosButton);
        reportesPanel.add(tipoReportePanel, BorderLayout.CENTER);

        JPanel volverPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 60));
        JButton volverButton = new JButton("Volver");
        volverButton.setPreferredSize(new Dimension(160, 25));
        volverPanel.add(volverButton);
        reportesPanel.add(volverPanel, BorderLayout.SOUTH);

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "StartPanel");
            }
        });

        reporteCanciones(mainPanel, cardLayout, tituloLabel, topCancionesButton);
        reporteDiscos(mainPanel, cardLayout, tituloLabel, frame, discosVendidosButton);

        reportesPanel.add(tipoReportePanel, BorderLayout.CENTER);
        reportesPanel.add(volverPanel, BorderLayout.SOUTH);
    }

    /**
     * Configura y muestra el reporte de las top 10 canciones de un género específico.
     *
     * @param mainPanel Panel principal que contiene todos los sub-paneles.
     * @param cardLayout Administrador de diseño para cambiar entre paneles.
     * @param tituloLabel Etiqueta de título del reporte.
     * @param topCancionesButton Botón que permite acceder al reporte de top canciones.
     */
    public static void reporteCanciones(JPanel mainPanel, CardLayout cardLayout, JLabel tituloLabel, JButton topCancionesButton) {
        JPanel reporteCancionesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 60));
        JLabel tituloCancionesLabel = new JLabel("Top 10 canciones", SwingConstants.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 24));

        mainPanel.add(reporteCancionesPanel, "reporteCancionesPanel");
        reporteCancionesPanel.add(tituloCancionesLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JTextField archivoTextField = new JTextField("Ingrese el genero que desea analizar: ", 30);

        topCancionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Limpiar el panel para evitar agregar componentes duplicados
                reporteCancionesPanel.removeAll();
                reporteCancionesPanel.setLayout(new BoxLayout(reporteCancionesPanel, BoxLayout.Y_AXIS));

                // Configurar el título
                JLabel tituloCancionesLabel = new JLabel("Top 10 canciones", SwingConstants.CENTER);
                tituloCancionesLabel.setFont(new Font("Arial", Font.BOLD, 24));
                tituloCancionesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                reporteCancionesPanel.add(tituloCancionesLabel);

                // Espacio entre el título y el campo de texto
                reporteCancionesPanel.add(Box.createRigidArea(new Dimension(0, 20)));

                // Configurar el campo de texto
                JTextField generoTextField = new JTextField("Ingrese el género que desea analizar: ", 30);
                generoTextField.setMaximumSize(new Dimension(300, 25));
                generoTextField.setAlignmentX(Component.CENTER_ALIGNMENT);

                generoTextField.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        if (generoTextField.getText().equals("Ingrese el género que desea analizar: ")) {
                            generoTextField.setText("");
                        }
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        if (generoTextField.getText().isEmpty()) {
                            generoTextField.setText("");
                        }
                    }
                });

                reporteCancionesPanel.add(generoTextField);

                // Espacio entre el campo de texto y los botones
                reporteCancionesPanel.add(Box.createRigidArea(new Dimension(0, 20)));

                // Configurar los botones
                JPanel botonesPanel = new JPanel();
                botonesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

                JButton enviarButton = new JButton("Enviar");
                JButton volverReporteButton = new JButton("Volver");
                botonesPanel.add(enviarButton);
                botonesPanel.add(volverReporteButton);

                volverReporteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cardLayout.show(mainPanel, "reportesPanel");
                    }
                });
                JPanel tablaPanel = new JPanel(new BorderLayout());
                enviarButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String genero = generoTextField.getText().trim();
                        if (genero.isEmpty()){
                            JOptionPane.showMessageDialog(reporteCancionesPanel, "Ingrese un genero.", "Error", JOptionPane.INFORMATION_MESSAGE);
                        }else {
                            final ArrayList<Cancion> reporteCanciones = Spotify.topCancionesGenero(genero);
                            Reporte.topCanciones(reporteCanciones, genero);
                            String[] columnas = {"Nombre", "Duracion", "Cantidad de reproducciones"};
                            DefaultTableModel tablaModelo = new DefaultTableModel(columnas, 0) {
                                @Override
                                public boolean isCellEditable(int row, int column) {
                                    return false; // Desactiva la edición de celdas
                                }
                            };

                            for (Cancion canciones : reporteCanciones) {
                                Object[] Data = {canciones.getNombre(), canciones.getDuracion(), canciones.getCantReproducciones()};
                                tablaModelo.addRow(Data);
                            }

                            JTable tabla = new JTable(tablaModelo);
                            tabla.getTableHeader().setReorderingAllowed(false);
                            JScrollPane scrollPane = new JScrollPane(tabla);

                            tablaPanel.removeAll();
                            tablaPanel.add(scrollPane, BorderLayout.CENTER);
                            reporteCancionesPanel.add(tablaPanel, BorderLayout.CENTER);
                            reporteCancionesPanel.revalidate();
                            reporteCancionesPanel.repaint();
                        }
                    }
                });

                reporteCancionesPanel.add(botonesPanel);

                // Ajustar el panel
                reporteCancionesPanel.revalidate();
                reporteCancionesPanel.repaint();

                // Mostrar el panel
                cardLayout.show(mainPanel, "reporteCancionesPanel");
            }
        });
    }

    /**
     * Configura y muestra el reporte de unidades vendidas por disco de un artista específico,
     * utilizando un JComboBox para seleccionar el nombre del artista.
     *
     * @param mainPanel Panel principal que contiene todos los sub-paneles.
     * @param cardLayout Administrador de diseño para cambiar entre paneles.
     * @param tituloLabel Etiqueta de título del reporte.
     * @param frame Marco principal de la aplicación.
     * @param discosVendidosButton Botón que permite acceder al reporte de discos vendidos.
     */
    public static void reporteDiscos(JPanel mainPanel, CardLayout cardLayout, JLabel tituloLabel, JFrame frame, JButton discosVendidosButton) {
        JPanel reporteDiscosPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 60));
        mainPanel.add(reporteDiscosPanel, "reporteDiscosPanel");

        discosVendidosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Limpiar el panel antes de agregar nuevos componentes
                reporteDiscosPanel.removeAll();
                reporteDiscosPanel.setLayout(new BoxLayout(reporteDiscosPanel, BoxLayout.Y_AXIS));

                // Configurar el título
                JLabel tituloDiscoLabel = new JLabel("Unidades vendidas por disco", SwingConstants.CENTER);
                tituloDiscoLabel.setFont(new Font("Arial", Font.BOLD, 24));
                tituloDiscoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                reporteDiscosPanel.add(tituloDiscoLabel);

                // Espacio entre el título y el ComboBox
                reporteDiscosPanel.add(Box.createRigidArea(new Dimension(0, 20)));

                // Configurar el ComboBox para seleccionar el artista
                JLabel artistaLabel = new JLabel("Seleccione un artista: ");
                JComboBox<String> artistaComboBox = new JComboBox<>();
                for (Artista artista : Spotify.listarTodosArtistas()) {
                    artistaComboBox.addItem(artista.getNombre());
                }
                artistaComboBox.setMaximumSize(new Dimension(300, 25));
                artistaComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);

                reporteDiscosPanel.add(artistaLabel);
                reporteDiscosPanel.add(artistaComboBox);

                // Espacio entre el ComboBox y los botones
                reporteDiscosPanel.add(Box.createRigidArea(new Dimension(0, 20)));

                // Configurar los botones
                JPanel botonesPanel = new JPanel();
                botonesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

                JButton enviarButton = new JButton("Generar Reporte");
                JButton volverReporteButton = new JButton("Volver");
                botonesPanel.add(enviarButton);
                botonesPanel.add(volverReporteButton);

                volverReporteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cardLayout.show(mainPanel, "reportesPanel");
                    }
                });

                JPanel tablaPanel = new JPanel(new BorderLayout());
                enviarButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String nombreSeleccionado = (String) artistaComboBox.getSelectedItem();

                        try {
                            Artista artista = Spotify.buscarArtistaPorNombre(nombreSeleccionado);
                            HashSet<Disco> reporteDiscos = artista.getDiscos();
                            float promedioUnidades = Reporte.promedio(reporteDiscos, artista.getID());

                            String[] columnas = {"Nombre del Disco", "Unidades Vendidas"};
                            DefaultTableModel tablaModelo = new DefaultTableModel(columnas, 0) {
                                @Override
                                public boolean isCellEditable(int row, int column) {
                                    return false; // Desactiva la edición de celdas
                                }
                            };
                            for (Disco disco : reporteDiscos) {
                                tablaModelo.addRow(new Object[]{disco.getNombre(), disco.getUnidadesVendidas()});
                            }

                            // Agregar una fila para el promedio al final de la tabla
                            tablaModelo.addRow(new Object[]{"Promedio de Unidades Vendidas", promedioUnidades});

                            JTable tabla = new JTable(tablaModelo);
                            tabla.getTableHeader().setReorderingAllowed(false);
                            JScrollPane scrollPane = new JScrollPane(tabla);

                            tablaPanel.removeAll();
                            tablaPanel.add(scrollPane, BorderLayout.CENTER);
                            reporteDiscosPanel.add(tablaPanel, BorderLayout.CENTER);
                            reporteDiscosPanel.revalidate();
                            reporteDiscosPanel.repaint();

                        } catch (ArtistaNoEncontradoException ex) {
                            JOptionPane.showMessageDialog(frame, "No se encontró el artista: " + nombreSeleccionado, "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                reporteDiscosPanel.add(botonesPanel);
                cardLayout.show(mainPanel, "reporteDiscosPanel");

                // Actualizar y mostrar el panel
                reporteDiscosPanel.revalidate();
                reporteDiscosPanel.repaint();
            }
        });
    }
}