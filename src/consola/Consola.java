package consola;

import discografica.Artista;
import discografica.Cancion;
import discografica.Disco;
import discografica.Discografica;
import exceptions.ArtistaNoEncontradoException;
import persistencia.Serializacion;
import reportes.Reporte;

import java.io.FileNotFoundException;
import java.util.*;

public class Consola {
    private Discografica discografica;


    public Consola() {
        discografica = new Discografica();
        cargarPeristencia();
    }

    public void menu(){
        Scanner sc = new Scanner(System.in);
        int opcion=-1;
        System.out.println("Bienvenido!");
        do{
            System.out.println("Que desea hacer?");
            System.out.println("1. Cargar datos desde un archivo XML");
            System.out.println("2. Mostar todos los datos");
            System.out.println("3. Generar liquidacion mensual de un artista");
            System.out.println("4. Consultar datos de un artista");
            System.out.println("5. Dar de baja un artista");
            System.out.println("6. Generar reportes");
            System.out.println("0. Salir");
            try{
                opcion = sc.nextInt();
                sc.nextLine();
                switch(opcion){
                    case 1:
                        cargarArchivoXML(sc);
                        break;
                    case 2:
                        mostrarDatos(sc);
                        break;
                    case 3:
                        System.out.println("3. ");
                        break;
                    case 4:
                        consultarDatos(sc);
                        break;
                    case 5:
                        bajaArtista(sc);
                        break;
                    case 6:
                        reportes(sc);
                        break;
                    case 0:
                        Serializacion.guardarObjeto(discografica, "discografica.dat");
                        break;
                    default:
                        System.out.println("Opcion no valida (Enter para continuar)");
                        sc.nextLine();
                }
            }catch(InputMismatchException e){
                System.err.println("Ingrese una opcion valida (Enter para continuar)");
                sc.nextLine();
                sc.nextLine();
            }
        }while(opcion != 0);

    }


    public void cargarPeristencia(){
        Discografica ob = Serializacion.cargarObjeto("discografica.dat", Discografica.class);
        if(ob != null){
            discografica = ob;
        }
    }

    public void cargarArchivoXML(Scanner sc){
        String path;
        try{
            System.out.println("Ingrese el nombre del archivo XML: ");
            path = sc.nextLine();
            path = path.concat(".xml");
            discografica.CargaDatos(path);
            System.out.println("El archivo se cargo correctamente (Enter para continuar)");
            sc.nextLine();
        }
        catch (NullPointerException e) {
            System.err.println("Error: referencia nula, asegúrese de que todos los objetos estén inicializados (Enter para continuar)");
            sc.nextLine();
        }
        catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public void mostrarDatos(Scanner sc){
        List<String> artistas = discografica.listarTodosArtistas();
        for(String artista : artistas){
            System.out.println(artista);
        }
        System.out.println("Enter para continuar");
        sc.nextLine();
    }

    public void consultarDatos(Scanner sc){
        try{
            int opcion;
            List<Artista> artistas = new ArrayList<>();
            System.out.println("Consultar por:");
            System.out.println("1. Cantidad de Integrantes");
            System.out.println("2. Genero musical");
            opcion = sc.nextInt();
            sc.nextLine();
            switch(opcion){
                case 1:
                    System.out.println("Ingresar cantidad de integrantes:");
                    int integrantes = sc.nextInt();
                    sc.nextLine();
                    artistas = discografica.consultaDatos(integrantes);
                    break;
                case 2:
                    System.out.println("Ingresar el Genero musical:");
                    String genero = sc.nextLine();
                    artistas = discografica.consultaDatos(genero);
                    break;
                default:
                    System.out.println("Opcion no valida (Enter para continuar)");
                    sc.nextLine();
                    consultarDatos(sc);
                    break;
            }
            if(artistas.size() == 0){
                System.out.println("No se encontraron artistas que cumplieran las condiciones");
            }
            for(Artista artista : artistas){
                System.out.println();
                System.out.println(artista.obtenerDetalles());
                System.out.println();
            }
        }catch(InputMismatchException e){
            System.err.println("Ingrese una opcion valida (Enter para continuar)");
            sc.nextLine();
            consultarDatos(sc);
        }
    }

    public void bajaArtista(Scanner sc){
        System.out.println("Ingrese el ID del artista que desea eliminar: ");
        String id = sc.nextLine();
        try {
            discografica.bajaArtista(id);
            System.out.println("El artista se elimino con exito");
        }catch (ArtistaNoEncontradoException e) {
            System.err.println("Artista no encontrado");
        }

    }

    public void reportes(Scanner sc){
        try{
            System.out.println("Ingrese el reporte que desea: ");
            System.out.println("1. Top 10 Canciones de un Genero");
            System.out.println("2. Unidades vendidas para cada Disco de un Artista");
            int opcion = sc.nextInt();
            sc.nextLine();
            switch(opcion){
                case 1:
                    System.out.println("Ingresar el Genero Musical: ");
                    String genero = sc.nextLine();
                    List<Cancion> canciones = discografica.topCancionesGenero(genero);
                    System.out.println(Reporte.topCanciones(canciones, genero));
                    break;
                case 2:
                    System.out.println("Ingresar el ID del Artista que desea: ");
                    String artista = sc.nextLine();
                    HashSet<Disco> discos = discografica.reporteDiscos(artista);
                    System.out.println(Reporte.promedio(discos, artista));
                    break;
                default:
                    System.out.println("Opcion no valida (Enter para continuar)");
                    sc.nextLine();
                    reportes(sc);
            }
        }catch(InputMismatchException e){
            System.err.println("Ingrese una opcion valida (Enter para continuar)");
            sc.nextLine();
            reportes(sc);
        }catch (ArtistaNoEncontradoException e) {
            System.err.println("Artista no encontrado");
        }
    }
}
