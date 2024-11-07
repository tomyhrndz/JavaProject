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

    public double GetGananciaReproducciones()
    {
        double Sumador = 0;
        for (Cancion act : canciones)
        {
            double Cant = act.getCantReproducciones();
            if(Cant > 0 && Cant < 5000)
                Sumador += 6 * Cant * 1.02;
            else
                if(Cant >= 5000 && Cant < 10000)
                    Sumador += 6 * Cant * 1.05;
                else
                    if(Cant > 10000)
                        Sumador += 6 * Cant * 1.1;
        }
        return Sumador;
    }

    public double GetGananciaDisco(){ return UnidadesVendidas * canciones.size() * 500; }
}