package discografica;

import java.util.ArrayList;

public class Disco {
    private int UnidadesVendidas;
    private ArrayList<Cancion> canciones;


    public Disco(int UnidadesVendidas) {
        this.UnidadesVendidas = UnidadesVendidas;
        canciones = new ArrayList<>();
    }

    public void AgregarCanciones(String Nombre, String Duracion, int Reproducciones, Boolean Sencillo) {
        if (Sencillo) {
            Sencillo nuevo = new Sencillo(Nombre, Duracion, Reproducciones);
            canciones.add(nuevo);
        }else {
            Cancion nuevo = new Cancion(Nombre, Duracion, Reproducciones);
            canciones.add(nuevo);
        }
    }

    public int getUnidadesVendidas() {
        return UnidadesVendidas;
    }

    public ArrayList<Cancion> getCanciones() {return canciones;}
    
}