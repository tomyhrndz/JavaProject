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



    public ArrayList<ObjetoLiquidacion> GetGananciaReproducciones(boolean EsEmergente)
    {
        ArrayList<ObjetoLiquidacion> ganancias = new ArrayList<>();
        ObjetoLiquidacion Aux = new ObjetoLiquidacion();
        for (Cancion act : canciones)
        {
            Aux = new ObjetoLiquidacion();
            double Cant = act.getCantReproducciones();
            if(Cant > 0 && Cant < 5000)
                Aux.setMonto( Constantes.GananciaxReproduccion * Cant * Constantes.PorcentajeMayor5000);
            else
                if(Cant >= 5000 && Cant < 10000)
                    Aux.setMonto(Constantes.GananciaxReproduccion * Cant * Constantes.PorcentajeMenor10000);
                else
                    if(Cant > 10000)
                        Aux.setMonto(Constantes.GananciaxReproduccion * Cant * Constantes.PorcentajeMayor10000);

            Aux.setDescripcion(act.getNombre());

            if(EsEmergente)
                Aux.setMonto(Aux.getMonto()*Constantes.PorcentajeArtistaEmergente);
            else
                Aux.setMonto(Aux.getMonto()*Constantes.PorcentajeArtistaConsagrado);

            ganancias.add(Aux);
        }
        return ganancias;
    }

    public ObjetoLiquidacion GetGananciaDisco(boolean EsEmergente)
    {
        if(EsEmergente)
            return new ObjetoLiquidacion(Nombre, (UnidadesVendidas * canciones.size() * Constantes.ValorCancion) * Constantes.PorcentajeArtistaEmergente);
        else
            return new ObjetoLiquidacion(Nombre, (UnidadesVendidas * canciones.size() * Constantes.ValorCancion) * Constantes.PorcentajeArtistaConsagrado);
    }
}