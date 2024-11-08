package consola;

import discografica.Discografica;
import persistencia.Serializacion;

import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

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
                        System.out.println("4. ");
                        break;
                    case 5:
                        System.out.println("5. ");
                        break;
                    case 6:
                        System.out.println("6. ");
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
}
