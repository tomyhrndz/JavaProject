package persistencia;

import java.io.*;

public class Serializacion {

    //Guarda Serializacion Clasica
    public static void guardarObjeto(Object objeto, String archivo) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivo))) {
            out.writeObject(objeto);
        } catch (IOException e) {
            System.err.println("Error al guardar los datos: " + e.getMessage());
        }
    }

    // Carga Serializacion Clasica
    public static <T> T cargarObjeto(String archivo, Class<T> tipo) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
            return tipo.cast(in.readObject());
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar los datos: " + e.getMessage());
            return null;
        }
    }
}
