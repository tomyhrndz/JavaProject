package reportes;

import discografica.Cancion;
import discografica.Disco;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.HashSet;
import java.util.List;

public class Reporte {

    public static void topCanciones(List<Cancion> canciones, String genero) {
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

    }

    public static String promedio(HashSet<Disco>discos, String artista){
        float sum = 0;
        int cantDiscos= 0;
        float promedio = 0;

        StringBuilder sb = new StringBuilder("Unidades vendidas por Disco:\n");
        for (Disco disco : discos) {
            cantDiscos++;
            sum += disco.getUnidadesVendidas();
            sb.append("Disco ").append(cantDiscos).append(": ").append(disco.getUnidadesVendidas()).append(" unidades\n");
        }
        promedio = sum/cantDiscos;
        sb.append("Promedio por Disco: ").append(promedio).append(" unidades\n");

        File archivo = new File("UnidadesVendidas("+ artista +").txt");
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, false))){
            writer.write(sb.toString());
        }catch (Exception e){
            System.err.println("Error al crear o escribir el archivo: " + e.getMessage());
        }
        return sb.toString();
    }
}