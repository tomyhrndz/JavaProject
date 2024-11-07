package discografica;

import java.util.ArrayList;
import java.util.HashSet;

public class Artista implements Comparable<Artista>{
    private String ID;
    private String Nombre;
    private int CantIntegrantes;
    private String Genero;
    private HashSet<Disco> Discos;
    private ArrayList<Recital> Recitales;

    public Artista(String ID, String Nombre, int CantIntegrantes, String Genero) {
        this.ID = ID;
        this.Nombre = Nombre;
        this.CantIntegrantes = CantIntegrantes;
        this.Genero = Genero;
        Discos = new HashSet<>();
        Recitales = new ArrayList<>();
    }

    @Override
    public int compareTo(Artista A) {
        return this.ID.compareTo(A.getID());
    }

    public void CargarDisco(Disco nuevo) {
        Discos.add(nuevo);
    }

    public void CargarRecital (Recital nuevo) {
        Recitales.add(nuevo);
    }

    public String getID(){
        return ID;
    }

    public String getGenero() {
        return Genero;
    }

    public int getCantIntegrantes() {
        return CantIntegrantes;
    }

    public HashSet<Disco> getDiscos(){ return Discos; }

    /*
    public void Mostrar() {
        System.out.println("ID: " + ID);

        System.out.println("Nombre: " + Nombre);
        System.out.println("Cantidad de integrantes: " + CantIntegrantes);
        System.out.println("Genero musical: " + Genero);

        for (discografica.Disco discos: Discos) {
            discos.Mostrar();
        }

        for (Recital recitales: Recitales) {
            recitales.Mostrar();
        }

    }*/
	public String obtenerDetalles() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(ID).append("\n");
        sb.append("Nombre: ").append(nombre).append("\n");
        sb.append("Cantidad de integrantes: ").append(cantIntegrantes).append("\n");
        sb.append("Género musical: ").append(genero).append("\n");

        sb.append("Discos:\n");
        for (Disco disco : discos) {
            sb.append("  - ").append(disco.getNombre())
              .append(" (Unidades vendidas: ").append(disco.getUnidadesVendidas()).append(")\n");
        }

        sb.append("Recitales:\n");
        for (Recital recital : recitales) {
            sb.append("  - Fecha: ").append(recital.getFecha())
              .append(", Recaudación: $").append(recital.getRecaudacion())
              .append(", Costos: $").append(recital.getCostos()).append("\n");
        }

        return sb.toString();
    }


}

