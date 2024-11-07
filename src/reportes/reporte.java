package reportes;

import discografica.Cancion;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.List;

public class reporte {

    public static String topCanciones(List<Cancion> canciones, String genero) {
        StringBuilder sb = new StringBuilder("Top 10 Canciones (" + genero + ")").append("\n");
        for (Cancion cancion : canciones) {
            sb.append("Canción: ").append(cancion.getNombre())
                    .append(" - Reproducciones: ").append(cancion.getCantReproducciones())
                    .append("\n");
        }

        File archivo = new File("Top Canciones " + genero + ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, false))) {
            writer.write(sb.toString());
        } catch (IOException e) {
            System.err.println("Error al crear o escribir en el archivo: " + e.getMessage());
        }

        //Lo devuelve para mostrar en la GUI
        return sb.toString();
    }

}