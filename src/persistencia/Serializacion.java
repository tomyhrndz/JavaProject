package persistencia;

import java.io.*;

/**
 * Clase Maneja la persistencia de Discografica
 * Permite el guardado y carga de Datos
 */
public class Serializacion {

    /**
     * Recibe un Objeto y lo guarda en un archivo binario determinado por @archivo
     * @param objeto Objeto Discografica
     * @param archivo Nombre del archivo de persistencia
     */
    public static void guardarObjeto(Object objeto, String archivo) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivo))) {
            out.writeObject(objeto);
        } catch (IOException e) {
            System.err.println("Error al guardar los datos: " + e.getMessage());
        }
    }

    /**
     * Lee un archivo binario y carga el objeto que contenga
     * @param archivo archivo bianrio
     * @param tipo Tipo del Objeto que se busca cargar
     * @param <T> Tipo del Objeto que se busca cargar
     * @return Objeto contenido en el archivo
     */
    public static <T> T cargarObjeto(String archivo, Class<T> tipo) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
            return tipo.cast(in.readObject());
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar los datos: " + e.getMessage());
            return null;
        }
    }
}
