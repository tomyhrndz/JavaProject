import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

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



    public String getID(){
        return ID;
    }

    public String getGenero() {
        return Genero;
    }

    public int getCantIntegrantes() {
        return CantIntegrantes;
    }

    public void Mostrar() {
        System.out.println("ID: " + ID);
        System.out.println("Nombre: " + Nombre);
        System.out.println("Cantidad de integrantes: " + CantIntegrantes);
        System.out.println("Genero musical: " + Genero);
    }


}

