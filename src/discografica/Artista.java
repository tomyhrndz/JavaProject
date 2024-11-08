package discografica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class Artista implements Comparable<Artista>, Serializable {
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

    public Liquidacion getLiquidacion() { return null; }

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

    public ArrayList<Recital> getRecitales(){ return Recitales; }

	public String obtenerDetalles() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(ID).append("\n");
        sb.append("Nombre: ").append(Nombre).append("\n");
        sb.append("Cantidad de integrantes: ").append(CantIntegrantes).append("\n");
        sb.append("Género musical: ").append(Genero).append("\n");

        sb.append("Discos:\n");
        int i=0;
        for (Disco disco : Discos) {
            i++;
            sb.append(i).append("- ")
              .append(" (Unidades vendidas: ").append(disco.getUnidadesVendidas()).append(")\n")
			  .append("Canciones:").append("\n");
			for(Cancion cancion: disco.getCanciones()){
				sb.append("-Nombre: ").append(cancion.getNombre()).append("\n-Duracion:")
					.append(cancion.getDuracion()).append("\n-Reproducciones: ")
					.append(cancion.getCantReproducciones()).append("\n");
			}
        }

        sb.append("Recitales:\n");
        for (Recital recital : Recitales) {
            sb.append("  - Fecha: ").append(recital.GetFecha())
              .append(", Recaudación: $").append(recital.GetRecaudacion())
              .append(", Costos: $").append(recital.GetCostoProduccion()).append("\n");
        }

        return sb.toString();
    }

}
