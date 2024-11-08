import discografica.Discografica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


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

        setUpButtonListeners(startPanel, mainPanel, cardLayout, frame);

        // Configurar el marco
        frame.add(mainPanel);
        frame.setLocationRelativeTo(null); // Centrar el marco en la pantalla
        frame.setVisible(true);
    }

    public static void setUpButtonListeners(JPanel startPanel, JPanel mainPanel, CardLayout cardLayout, JFrame frame) {
        // Crear un panel para los botones y usar GridLayout
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 0, 90)); // 3 filas, 1 columna, 10 píxeles de espacio vertical
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 75)); // Espacio alrededor del panel de botones

        // Crear los botones
        JButton gestionDeArtistasButton = new JButton("Gestión de artistas");
        JButton informesButton = new JButton("Informes");
        JButton salirButton = new JButton("Salir");

        // Agregar los botones al panel de botones
        buttonPanel.add(gestionDeArtistasButton);
        buttonPanel.add(informesButton);
        buttonPanel.add(salirButton);

        // Agregar el panel de botones al lado derecho del panel de inicio
        startPanel.add(buttonPanel, BorderLayout.EAST);

        // Agregar el panel de inicio al panel principal
        mainPanel.add(startPanel, "StartPanel");

        // Crear el segundo panel (Pantalla para "Gestión de artistas" e "Informes")
        JPanel artistaPanel = new JPanel(new FlowLayout());



        // Agregar el segundo panel al panel principal
        mainPanel.add(artistaPanel, "artistaPanel");

        // Añadir ActionListener a los botones
        gestionDeArtistasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cambiar a la segunda pantalla cuando se presiona el botón "Gestión de artistas"
                cardLayout.show(mainPanel, "artistaPanel");
                JPanel buttonPanelArtistas = new JPanel(new GridLayout(4, 1, 0, 90));
                buttonPanelArtistas.setBorder(BorderFactory.createEmptyBorder(40, 0, 50, 0)); // Espacio alrededor del panel de botones
                JButton agregarArtistasButton = new JButton("Cargar artistas");
                agregarArtistasButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        listenerCargaArtista(startPanel, mainPanel, cardLayout, frame);
                    }
                });
                JButton eliminarArtistaButton = new JButton("Eliminar artista");
                JButton liquidacionesButton = new JButton("Generar liquidacion");
                JButton volverButton = new JButton("Volver");
                buttonPanelArtistas.add(agregarArtistasButton);
                buttonPanelArtistas.add(eliminarArtistaButton);
                buttonPanelArtistas.add(liquidacionesButton);
                buttonPanelArtistas.add(volverButton);
                artistaPanel.add(buttonPanelArtistas, BorderLayout.CENTER);
            }
        });

        informesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cambiar a la segunda pantalla cuando se presiona el botón "Informes"
                cardLayout.show(mainPanel, "SecondPanel");
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

    public static void listenerCargaArtista(JPanel startPanel, JPanel mainPanel, CardLayout cardLayout, JFrame frame) {
        // Crear un panel para la carga de artistas
        JPanel cargaPanel = new JPanel(new GridBagLayout()); // Usar GridBagLayout para un mejor control del centrado
        mainPanel.add(cargaPanel, "cargaPanel");
        cardLayout.show(mainPanel, "cargaPanel");

        // Crear un panel para el campo de texto
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Espaciado centrado

        // Crear el JTextField con texto inicial
        JTextField archivoTextField = new JTextField("Ingrese el nombre del archivo con los artistas:", 30); // Campo de texto más ancho

        // Agregar un FocusListener para manejar el texto como un placeholder
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
        System.out.println(archivoTextField.getText());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Espaciado horizontal entre los botones

        // Crear los botones más angostos
        JButton enviarButton = new JButton("Aceptar");

        enviarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Path = archivoTextField.getText() + ".xml";

                // Llamar al método para cargar datos


                // Comprobar si el archivo de informe de errores existe
                File Arch = new File(Path);
                if (Arch.exists()) {
                    Spotify.CargaDatos(Path);
                    // Mostrar un mensaje de éxito usando JOptionPane
                    JOptionPane.showMessageDialog(frame, "El archivo se cargó de forma correcta.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                    // Volver al panel "artistaPanel" después de cerrar el mensaje
                    cardLayout.show(mainPanel, "artistaPanel");
                }else {
                    JOptionPane.showMessageDialog(frame, "El archivo no existe.", "Error", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });
        JButton volverButton = new JButton("Volver");

        enviarButton.setPreferredSize(new Dimension(140, 30));
        volverButton.setPreferredSize(new Dimension(140, 30));

        // Agregar los botones al panel de botones
        buttonPanel.add(enviarButton);
        buttonPanel.add(volverButton);

        // Usar GridBagConstraints para centrar verticalmente
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 20, 0); // Margen entre el campo de texto y los botones

        // Agregar el campo de texto centrado verticalmente
        cargaPanel.add(inputPanel, gbc);

        // Ajustar gbc para los botones y agregarlos también centrados
        gbc.gridy = 1;
        cargaPanel.add(buttonPanel, gbc);
    }
}

