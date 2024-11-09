package discografica;

import exceptions.ArtistaNoEncontradoException;
import exceptions.ErroresEnArchivoException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.Serializable;

public class Discografica implements Serializable{
    TreeSet<Artista> Artistas;

    public Discografica () {
        Artistas = new TreeSet<>();
    }

    public void CargaDatos(String path) throws FileNotFoundException{
        StringBuilder InformeErrores = new StringBuilder();
        Artista NuevoArtista = null;
        Disco NuevoDisco = null;
        TreeSet<Artista> cargarArtistas = new TreeSet<>();

        try {

            File Arch = new File(path);

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

                    String ID = null;
                    String Nombre = null;
                    int CantIntegrantes = 0;
                    String Genero = null;
                    try {
                        ID = elemento.getElementsByTagName("ID").item(0).getTextContent();
                        if (ID.isEmpty())
                            throw new Exception("El tag ID esta vacio en la entrada numero: " + (i+1));

                    }catch (Exception e) {
                        InformeErrores.append(e.getMessage()).append(System.lineSeparator());
                    }

                    try {
                        Nombre = elemento.getElementsByTagName("nombre").item(0).getTextContent();
                        if (Nombre.isEmpty())
                            throw new Exception("El tag nombre esta vacio en la entrada numero: " + (i+1));

                    }catch (Exception e) {
                        InformeErrores.append(e.getMessage()).append(System.lineSeparator());
                    }

                    try {
                        String Integrantes = elemento.getElementsByTagName("cantidad_integrantes").item(0).getTextContent().trim();
                        CantIntegrantes = Integer.parseInt(Integrantes);
                        if (CantIntegrantes < 1) {
                            throw new IllegalArgumentException("La cantidad de integrantes es negativa o cero");
                        }
                        if (Integrantes.isEmpty()){
                            throw new Exception("La cantidad de integrantes esta vacio en la entrada numero: " + (i+1));
                        }
                    }catch (Exception e) {
                        InformeErrores.append(e.getMessage()).append(System.lineSeparator());
                    }

                    try {
                        Genero = elemento.getElementsByTagName("genero_musical").item(0).getTextContent();
                        if (Genero.isEmpty())
                            throw new Exception("El tag genero_musical esta vacio en la entrada numero: " + (i+1));

                    }catch (Exception e) {
                        InformeErrores.append(e.getMessage()).append(System.lineSeparator());
                    }

                    if (ID.startsWith("EMG")) {
                        NuevoArtista = new Emergentes(ID.substring(3), Nombre, CantIntegrantes, Genero);
                        cargarArtistas.add(NuevoArtista);
                    }else {
                        NuevoArtista = new Consagrados(ID.substring(3), Nombre, CantIntegrantes, Genero);
                        cargarArtistas.add(NuevoArtista);
                    }

                    //Procesar los datos del disco
                    NodeList ListaDiscos = elemento.getElementsByTagName("disco");
                    for (int j=0; j < ListaDiscos.getLength(); j ++) {
                        Element discoElemento = (Element) ListaDiscos.item(j);
                        int UnidadesVendidas = 0;

                        try {
                            String Vendidas = discoElemento.getElementsByTagName("unidades_vendidas").item(0).getTextContent().trim();
                            if (Vendidas.isEmpty())
                                throw new Exception("El tag unidades_vendidas esta vacio en la entrada numero: " + (i+1));
                            else {
                                UnidadesVendidas = Integer.parseInt(Vendidas);
                                if (UnidadesVendidas < 0) {
                                    throw new IllegalArgumentException("La cantidad de unidades vendidas es negativa o cero");
                                }
                            }
                        }catch (Exception e) {
                            InformeErrores.append(e.getMessage()).append(System.lineSeparator());
                        }


                        NuevoDisco = new Disco(UnidadesVendidas);
                        // Procesar canciones del disco actual
                        NodeList ListaCanciones = discoElemento.getElementsByTagName("cancion");
                        for (int k=0; k < ListaCanciones.getLength(); k++) {
                            //NodeList ListaCancion = elemento.getElementsByTagName("canciones");
                            Element CancionElemento = (Element) ListaCanciones.item(k);

                            String NombreCancion = null;
                            String Duracion = null;
                            int Reproducciones = 0;
                            boolean EsSencillo = false;

                            try {
                                NombreCancion = CancionElemento.getElementsByTagName("nombreCancion").item(0).getTextContent();
                                if (NombreCancion.isEmpty())
                                    throw new Exception("El tag nombreCancion esta vacio en la entrada numero: " + (i+1));

                            }catch (Exception e) {
                                InformeErrores.append(e.getMessage()).append(System.lineSeparator());
                            }

                            try {
                                Duracion = CancionElemento.getElementsByTagName("duracion").item(0).getTextContent();
                                if (Duracion.isEmpty())
                                    throw new Exception("El tag duracion esta vacio en la entrada numero: " + (i+1));

                            }catch (Exception e) {
                                InformeErrores.append(e.getMessage()).append(System.lineSeparator());
                            }
                            try {
                                String CantRepro = CancionElemento.getElementsByTagName("reproducciones").item(0).getTextContent().trim();
                                if (CantRepro.isEmpty())
                                    throw new Exception("El tag duracion esta vacio en la entrada numero: " + (i+1));
                                else {
                                    Reproducciones = Integer.parseInt(CantRepro);
                                    if (Reproducciones < 1)
                                        throw new IllegalArgumentException("Las reproducciones son menos o iguales a cero");
                                }
                            }catch (Exception e) {
                                InformeErrores.append(e.getMessage()).append(System.lineSeparator());
                            }
                            try {
                                String Sencillo = CancionElemento.getElementsByTagName("es_sencillo").item(0).getTextContent();
                                if (Sencillo.isEmpty())
                                    throw new Exception("El tag duracion esta vacio en la entrada numero: " + (i+1));
                                else
                                    EsSencillo = Boolean.parseBoolean(Sencillo);
                            }catch (Exception e) {
                                InformeErrores.append(e.getMessage()).append(System.lineSeparator());
                            }

                            NuevoDisco.AgregarCanciones(NombreCancion, Duracion, Reproducciones, EsSencillo);
                        }
                        NuevoArtista.CargarDisco(NuevoDisco);
                    }
                    //Procesar los datos de los recitales
                    NodeList ListaRecitales = elemento.getElementsByTagName("recital");
                    for (int l=0; l < ListaRecitales.getLength(); l++) {
                        Element RecitalesElemento = (Element) ListaRecitales.item(l);
                        int year = 0, mes = 0, dia = 0;
                        float Recaudacion = 0, CostoProduccion = 0;

                        try {
                            String FechaString = RecitalesElemento.getElementsByTagName("fecha").item(0).getTextContent();
                            if (FechaString.isEmpty())
                                throw new Exception("El tag fecha esta vacio en la entrada numero: " + (i+1));
                            else {
                                String[] Fecha = FechaString.split("-", 3);
                                year = Integer.parseInt(Fecha[0]);
                                mes = Integer.parseInt(Fecha[1]);
                                dia = Integer.parseInt(Fecha[2]);
                            }
                        } catch (Exception e) {
                            InformeErrores.append(e.getMessage()).append(System.lineSeparator());
                        }

                        try {
                            String MontoRecaudacion =RecitalesElemento.getElementsByTagName("recaudacion").item(0).getTextContent().trim();
                            if (MontoRecaudacion.isEmpty())
                                throw new Exception("El tag recaudacion esta vacio en la entrada numero: " + (i+1));
                            else {
                                Recaudacion = Float.parseFloat(MontoRecaudacion);
                                if (Recaudacion < 1)
                                    throw new IllegalArgumentException("El monto recaudado es menor o igual a cero");
                            }
                        } catch (Exception e) {
                            InformeErrores.append(e.getMessage()).append(System.lineSeparator());
                        }

                        try {
                            String Costo = RecitalesElemento.getElementsByTagName("costos_produccion").item(0).getTextContent().trim();
                            if (Costo.isEmpty())
                                throw new Exception("El tag costos_produccion esta vacio en la entrada numero: " + (i+1));
                            else {
                                CostoProduccion = Float.parseFloat(Costo);
                                if (CostoProduccion < 1)
                                    throw new IllegalArgumentException("El costo de la produccion menor o igual a cero");
                            }
                        } catch (Exception e) {
                            InformeErrores.append(e.getMessage()).append(System.lineSeparator());
                        }

                        Recital NuevoRecital = new Recital(year, mes, dia, Recaudacion, CostoProduccion);
                        NuevoArtista.CargarRecital(NuevoRecital);
                    }
                }
            }
        }catch (FileNotFoundException e){
            throw new FileNotFoundException("No se encontro el archivo");
        }
        catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
        catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e);
        }

        if (!InformeErrores.isEmpty()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("Informe_errores.txt"))) {
                writer.write("======[Informe de errores]======");
                writer.newLine();
                writer.write(InformeErrores.toString());
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                writer.write("    " + LocalDateTime.now().format(formato));
                throw new ErroresEnArchivoException(InformeErrores.toString());

            } catch (IOException e) {
                System.err.println("No se pudo escribir el informe de errores" + e.getMessage());
            }
        }
        else{
            Artistas.addAll(cargarArtistas);
        }

    }


    public List<Artista> consultaDatos(int CantInt) {
        List<Artista> artistasEncontrados = new ArrayList<>();

        for(Artista artista : Artistas) {
            if (CantInt == artista.getCantIntegrantes())
                artistasEncontrados.add(artista);
        }
        return artistasEncontrados;
    }

    public List<Artista> consultaDatos(String genero){
        List<Artista> artistasEncontrados = new ArrayList<>();
        for(Artista artista : Artistas) {
            if (genero.equalsIgnoreCase(artista.getGenero()))
                artistasEncontrados.add(artista);
        }
        return artistasEncontrados;
    }


    public void bajaArtista(String ID) {
        try {
            Artista eliminar = buscarArtista(ID);
            Artistas.remove(eliminar);
        } catch (ArtistaNoEncontradoException e) {
            throw e;
        }
    }

    public Artista buscarArtista(String ID) throws ArtistaNoEncontradoException {
        Iterator<Artista> iterator = Artistas.iterator();
        Artista act = null;
        boolean bandera = false;

        while (iterator.hasNext() && !bandera) { //Mientras no lo encuentre y exista un siguiente
            act = iterator.next();              //Avanza el iterator
            if (ID.equals(act.getID())) {
                bandera = true;                //Encontro el ID
            }
        }
        if(bandera){
            return act;
        }
        throw new ArtistaNoEncontradoException("El artista con ID " + ID + " no fue encontrado.");
    }

    public List<Cancion> topCancionesGenero(String genero) {
        List<Cancion> canciones = new ArrayList<>();

        List<Cancion> cancionesGenero = new ArrayList<>();

        // Crea lista con todas las canciones del Genero
        for (Artista artista : Artistas) {
            if (genero.equalsIgnoreCase(artista.getGenero())) {
                for(Disco disco : artista.getDiscos()){
                    cancionesGenero.addAll(disco.getCanciones());
                }
            }
        }

        // Ordena la lista basado en las reproducciones
        cancionesGenero.sort(Comparator.comparingInt(Cancion::getCantReproducciones).reversed());
        // Devuelve sublista de las primeras 10
        return cancionesGenero.size() > 10 ? cancionesGenero.subList(0, 10) : cancionesGenero;

    }

	public List<String> listarTodosArtistas(){
		List<String> detallesArtistas = new ArrayList<>();

		for(Artista artista : Artistas){
			detallesArtistas.add(artista.obtenerDetalles());
		}

		return detallesArtistas;
	}

    public HashSet<Disco> reporteDiscos(String ID){
        Artista artista = buscarArtista(ID);
        HashSet<Disco> discos = artista.getDiscos();
        return discos;
    }
}
