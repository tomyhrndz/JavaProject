package discografica;

import java.io.Serializable;
import java.util.ArrayList;

public class Disco implements Serializable {
    private String Nombre;
    private int UnidadesVendidas;
    private ArrayList<Cancion> canciones;


    public Disco(String Nombre, int UnidadesVendidas) {
        this.Nombre = Nombre;
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

    public String getNombre() {return Nombre; }

    public int getUnidadesVendidas() {
        return UnidadesVendidas;
    }

    public ArrayList<Cancion> getCanciones() {return canciones;}

    public double GetGananciaReproducciones()
    {
        double Sumador = 0;
        for (Cancion act : canciones)
        {
            double Cant = act.getCantReproducciones();
            if(Cant > 0 && Cant < 5000)
                Sumador += Constantes.GananciaxReproduccion * Cant * Constantes.PorcentajeMayor5000;
            else
                if(Cant >= 5000 && Cant < 10000)
                    Sumador += Constantes.GananciaxReproduccion * Cant * Constantes.PorcentajeMenor10000;
                else
                    if(Cant > 10000)
                        Sumador += Constantes.GananciaxReproduccion * Cant * Constantes.PorcentajeMayor10000;
        }
        return Sumador;
    }

    public double GetGananciaDisco(){ return UnidadesVendidas * canciones.size() * Constantes.ValorCancion; }
}