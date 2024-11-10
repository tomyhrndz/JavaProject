import discografica.Artista;
import discografica.Discografica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;


public class GUI {
    static Discografica Spotify = new Discografica();


    public static void main(String[] args) {
        // Crear el marco (JFrame)
        JFrame frame = new JFrame("GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500); // Tamaño del marco

        // Crear un panel principal con CardLayout para cambiar entre pantallas
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        // Crear el panel de inicio (Pantalla principal)
        JPanel startPanel = new JPanel(new BorderLayout());

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

    public static void setUpBotones(JPanel startPanel, JPanel mainPanel, CardLayout cardLayout, JFrame frame) {
        // Crear un panel para los botones y usar GridLayout
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 0, 90)); // 3 filas, 1 columna, 10 píxeles de espacio vertical
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 75)); // Espacio alrededor del panel de botones

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
                // Cambiar a la segunda pantalla cuando se presiona el botón "Informes"
                JPanel reportesPanel = new JPanel(new BorderLayout());
                mainPanel.add(reportesPanel, "reportesPanel");
                cardLayout.show(mainPanel, "reportesPanel");

                JLabel tituloLabel = new JLabel("Carga artistas");
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

                JPanel volverPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 60));
                JButton volverButton = new JButton("Volver");
                volverButton.setPreferredSize(new Dimension(160, 25));
                volverPanel.add(volverButton);

                volverButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cardLayout.show(mainPanel, "StartPanel");
                    }
                });

                reportesPanel.add(tipoReportePanel, BorderLayout.CENTER);
                reportesPanel.add(volverPanel, BorderLayout.SOUTH);
            }
        });


        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cerrar la aplicación cuando se presiona el botón "Salir"
                frame.dispose();
            }
        });
    }

    public static void MenuArtistas (JPanel startPanel, JPanel mainPanel, JPanel artistaPanel, CardLayout cardLayout, JFrame frame, JButton gestionDeArtistasButton){
        mainPanel.add(artistaPanel, "artistaPanel");


        JPanel buttonPanelArtistas = new JPanel(new GridLayout(5, 1, 0, 60));
        buttonPanelArtistas.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0)); // Espacio alrededor del panel de botones
        JButton agregarArtistasButton = new JButton("Cargar artistas");
        JButton consultaArtistasButton = new JButton("Consultar datos de artistas");
        JButton eliminarArtistaButton = new JButton("Eliminar artista");
        JButton liquidacionesButton = new JButton("Generar liquidacion");
        JButton volverButton = new JButton("Volver");

        buttonPanelArtistas.add(agregarArtistasButton);
        SeccionCarga(mainPanel, cardLayout, frame, gestionDeArtistasButton, agregarArtistasButton);

        buttonPanelArtistas.add(consultaArtistasButton);
        SeccionConsulta(mainPanel, artistaPanel,cardLayout, consultaArtistasButton);

        buttonPanelArtistas.add(eliminarArtistaButton);
        SeccionElimina(mainPanel, cardLayout, frame, eliminarArtistaButton);


        buttonPanelArtistas.add(liquidacionesButton);
        buttonPanelArtistas.add(volverButton);
        artistaPanel.add(buttonPanelArtistas, BorderLayout.CENTER);


        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "StartPanel");
            }
        });
    }

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

                        archivoTextField.addFocusListener(new java.awt.event.FocusListener() {
                            @Override
                            public void focusGained(java.awt.event.FocusEvent e) {
                                if (archivoTextField.getText().equals("Ingrese el nombre del archivo con los artistas:")) {
                                    archivoTextField.setText("");
                                }
                            }

                            @Override
                            public void focusLost(java.awt.event.FocusEvent e) {
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
                                File Arch = new File(Path);
                                if (Arch.exists()) {
                                    Spotify.CargaDatos(Path);
                                    JOptionPane.showMessageDialog(frame, "El archivo se cargó de forma correcta.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                                    cardLayout.show(mainPanel, "artistaPanel");
                                } else {
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

    public static void SeccionConsulta(JPanel mainPanel, JPanel artistaPanel, CardLayout cardLayout, JButton consultaArtistasButton) {
        consultaArtistasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final List<Artista>[] datosArtistas = new List[1];

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

                        String genero = generoText.getText();
                        String integrantes = integrantesText.getText();
                        if (integrantes.isEmpty())
                            datosArtistas[0] = Spotify.consultaDatos(genero);
                        else {
                            if (genero.isEmpty())
                                datosArtistas[0] = Spotify.consultaDatos(Integer.parseInt(integrantes));
                            else {
                                datosArtistas[0] = Spotify.consultaDatos(genero, Integer.parseInt(integrantes));
                            }
                        }

                        String[] columnas = {"ID", "Nombre", "Cantidad de integrantes", "Genero"};
                        DefaultTableModel tablaModelo = new DefaultTableModel(columnas, 0);

                        for(Artista artista: datosArtistas[0]) {
                            Object[] Data = {artista.getID(), artista.getNombre(), artista.getCantIntegrantes(), artista.getGenero()};
                            tablaModelo.addRow(Data);
                        }

                        JTable tabla = new JTable(tablaModelo);
                        JScrollPane scrollPane = new JScrollPane(tabla);

                        tablaPanel.removeAll();
                        tablaPanel.add(scrollPane, BorderLayout.CENTER);
                        buscaPanel.remove(filtrosPanel);
                        buscaPanel.add(volverTablaPanel, BorderLayout.SOUTH);
                        buscaPanel.remove(volverPanel);
                        buscaPanel.add(tablaPanel, BorderLayout.CENTER);
                        buscaPanel.revalidate();
                        buscaPanel.repaint();


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

                JPanel datosPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 60));
                JLabel idLabel = new JLabel("ID del artista: ");
                JTextField idText = new JTextField(15);
                JButton enviarButton = new JButton("Enviar");
                enviarButton.setPreferredSize(new Dimension(90, 20));

                JPanel volvelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

                JButton volverButton = new JButton("Volver");
                volverButton.setPreferredSize(new Dimension(90, 20));
                JPanel volverPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                volverPanel.add(volverButton);
                eliminarPanel.add(volverPanel, BorderLayout.SOUTH);

                enviarButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String ID = idText.getText();
                        Spotify.bajaArtista(ID);
                        JOptionPane.showMessageDialog(frame, "El artista se dio de baja de forma correcta.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        cardLayout.show(mainPanel, "artistaPanel");
                        //BUSCAR FORMA DE QUE INFORME DE UN ERROR

                    }
                });

                volverButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cardLayout.show(mainPanel, "artistaPanel");
                    }
                });


                datosPanel.add(idLabel);
                datosPanel.add(idText);
                datosPanel.add(enviarButton);

                eliminarPanel.add(datosPanel);

            }
        });
    }


}

