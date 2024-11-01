import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;
import java.nio.Buffer;
import java.nio.channels.FileLock;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.SimpleTimeZone;
import java.util.TreeSet;
import java.util.spi.CalendarNameProvider;
import java.util.stream.StreamSupport;


public class Discografica {
    TreeSet<Artista> Artistas;

    public Discografica () {
        Artistas = new TreeSet<>();
    }

    public void CargaDatos() {
        StringBuilder InformeErrores = new StringBuilder();
        Artista NuevoArtista = null;
        Disco NuevoDisco = null;

        try {
            File Arch = new File("Artistas.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newDefaultInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(Arch);
            doc.getDocumentElement().normalize();

            NodeList ListaArtistas = doc.getElementsByTagName("artista");
            for (int i=0; i < ListaArtistas.getLength(); i++) {
                Node NodArtista = ListaArtistas.item(i);

                if (NodArtista.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) NodArtista;

                    //Lee los datos para los atributos de las clases artistas
                    String ID = elemento.getElementsByTagName("ID").item(0).getTextContent();
                    String Nombre = elemento.getElementsByTagName("nombre").item(0).getTextContent();
                    String Integrantes = elemento.getElementsByTagName("cantidad_integrantes").item(0).getTextContent().trim();
                    int CantIntegrantes= Integer.parseInt(Integrantes);
                    if (CantIntegrantes < 1) {
                        throw new IllegalArgumentException(": La cantidad de integrantes es negativa o cero");
                    }
                    String Genero = elemento.getElementsByTagName("genero_musical").item(0).getTextContent();

                    if (ID.startsWith("EMG")) {
                        NuevoArtista = new Emergentes(ID.substring(3), Nombre, CantIntegrantes, Genero);
                        Artistas.add(NuevoArtista);
                    }else {
                        NuevoArtista = new Consagrados(ID.substring(3), Nombre, CantIntegrantes, Genero);
                        Artistas.add(NuevoArtista);
                    }

                    //Procesar los datos del disco
                    NodeList ListaDiscos = elemento.getElementsByTagName("disco");
                    for (int j=0; j < ListaDiscos.getLength(); j ++) {
                        Element discoElemento = (Element) ListaDiscos.item(j);
                        int UnidadesVendidas = Integer.parseInt(discoElemento.getElementsByTagName("unidades_vendidas").item(0).getTextContent());

                        NuevoDisco = new Disco(UnidadesVendidas);
                        // Procesar canciones del disco actual
                        NodeList ListaCanciones = discoElemento.getElementsByTagName("cancion");
                        for (int k=0; k < ListaCanciones.getLength(); k++) {
                            //NodeList ListaCancion = elemento.getElementsByTagName("canciones");
                            Element CancionElemento = (Element) ListaCanciones.item(k);

                            String NombreCancion = CancionElemento.getElementsByTagName("nombreCancion").item(0).getTextContent();
                            String Duracion = CancionElemento.getElementsByTagName("duracion").item(0).getTextContent();
                            String CantRepro = CancionElemento.getElementsByTagName("reproducciones").item(0).getTextContent().trim();
                            int Reproducciones = Integer.parseInt(CantRepro);
                            if (Reproducciones < 1)
                                throw new IllegalArgumentException(": Las reproducciones son menos o iguales a cero");
                            boolean EsSencillo = Boolean.parseBoolean(CancionElemento.getElementsByTagName("es_sencillo").item(0).getTextContent());

                            NuevoDisco.AgregarCanciones(NombreCancion, Duracion, Reproducciones, EsSencillo);
                        }
                        NuevoArtista.CargarDisco(NuevoDisco);
                    }
                    //Procesar los datos de los recitales
                    NodeList ListaRecitales = elemento.getElementsByTagName("recital");
                    for (int l=0; l < ListaRecitales.getLength(); l++) {
                        Element RecitalesElemento = (Element) ListaRecitales.item(l);

                        String FechaString = RecitalesElemento.getElementsByTagName("fecha").item(0).getTextContent();
                        String[] Fecha = FechaString.split("-", 3);
                        int year = Integer.parseInt(Fecha[0]);
                        int mes = Integer.parseInt(Fecha[1]);
                        int dia = Integer.parseInt(Fecha[2]);
                        String MontoRecaudacion =RecitalesElemento.getElementsByTagName("recaudacion").item(0).getTextContent().trim();
                        Float Recaudacion = Float.parseFloat(MontoRecaudacion);
                        if (Recaudacion < 1)
                            throw new IllegalArgumentException(": El costo es menor o igual a cero");
                        String Costo = RecitalesElemento.getElementsByTagName("costos_produccion").item(0).getTextContent().trim();
                        Float CostoProduccion = Float.parseFloat(Costo);
                        if (CostoProduccion < 1)
                            throw new IllegalArgumentException(": El costo de la produccion es menor o igual a cero");

                        Recital NuevoRecital = new Recital(year, mes, dia, Recaudacion, CostoProduccion);
                        NuevoArtista.CargarRecital(NuevoRecital);
                    }
                }
            }
        }catch (FileNotFoundException e) {
            String mensaje = "El archivo no fue encontrado: " + e.getMessage();
            System.err.println(mensaje);
            InformeErrores.append(mensaje).append(System.lineSeparator());
        } catch (NumberFormatException e) {
            String mensaje = "Error en el formato de los números: " + e.getMessage();
            System.err.println(mensaje);
            InformeErrores.append(mensaje).append(System.lineSeparator());
        } catch (NullPointerException e) {
            String mensaje = "Falta rellenar el campo alguno de los datos: " + e.getMessage();
            System.err.println(mensaje);
            InformeErrores.append(mensaje).append(System.lineSeparator());
        } catch (ParserConfigurationException | IOException | SAXException e) {
            String mensaje = "Error durante el analisis: " + e.getMessage();
            System.err.println(mensaje);
            InformeErrores.append(mensaje).append(System.lineSeparator());
        } catch (IllegalArgumentException e) {
            String mensaje = "Error" + e.getMessage();
            System.err.println(mensaje);
            InformeErrores.append(mensaje).append(System.lineSeparator());
        }

        if (InformeErrores.length() > 1) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("Informe_errores.txt"))) {
                writer.write("======[Informe de errores]======");
                writer.newLine();
                writer.write(InformeErrores.toString());
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                writer.write("    " + LocalDateTime.now().format(formato));

            } catch (IOException e) {
                System.err.println("No se pudo escribir el informe de errores" + e.getMessage());
            }
        }

    }

    public void ConsultaDatos(int CantInt, String Genero) {
        Iterator<Artista> iterator = Artistas.iterator();
        boolean encontro = false;
        Artista act = null;

        while(iterator.hasNext() && !encontro) {
            act = iterator.next();
            if (CantInt == act.getCantIntegrantes() && Genero.equals(act.getGenero()))
                encontro = true;
        }

        if (encontro)
            act.Mostrar(); // Reformar para mostrar por GUI
        else
            System.out.println("No se encontro");

    }

    public int bajaArtista(String ID) {
        Iterator<Artista> iterator = Artistas.iterator();
        boolean bandera = true;
        Artista act = null;

        while (iterator.hasNext() && bandera) { //Mientras no lo encuentre y exista un siguiente
            act = iterator.next();              //Avanza el iterator
            if (ID.equals(act.getID())) {
                bandera = false;                //Encontro el ID
            }
        }
        if (bandera) { //No encontro el ID
            return 0;
        } else { // Eliminar ID
            Artistas.remove(act);
            return 1;
        }
    }

    public void MostrarArtista() {

        for (Artista act : Artistas) {
            System.out.println("");
            act.Mostrar();
        }
    }
}
