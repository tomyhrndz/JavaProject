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

    /**
     * Genera un archivo con el reporte de las 10 canciones con mas reproducciones de un genero musical
     * @param canciones lista de canciones con mas reproducciones del genero musical
     * @param genero genero musical
     */
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

    /**
     * Genera un archivo con detalle de unidades vendidas para cada disco para un artista recibido como parámetro e
     * informar la cantidad de unidades promedio por disco
     * @param discos HashSet Discos del Artista
     * @param artista Nombre del Artista
     * @return Promedio de unidades vendidas por Disco
     */
    public static float promedio(HashSet<Disco>discos, String artista){
        float sum = 0;
        int cantDiscos= 0;
        float prom = 0;

        StringBuilder sb = new StringBuilder("Unidades vendidas por Disco:\n");
        for (Disco disco : discos) {
            cantDiscos++;
            sum += disco.getUnidadesVendidas();
            sb.append("Disco ").append(disco.getNombre()).append(": ").append(disco.getUnidadesVendidas()).append(" unidades\n");
        }
        prom = sum/cantDiscos;
        sb.append("Promedio por Disco: ").append(prom).append(" unidades\n");

        File archivo = new File("UnidadesVendidas("+ artista +").txt");
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, false))){
            writer.write(sb.toString());
        }catch (Exception e){
            System.err.println("Error al crear o escribir el archivo: " + e.getMessage());
        }

        return prom;
    }
}