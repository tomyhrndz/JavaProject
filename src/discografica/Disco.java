package discografica;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase Disco que contiene nombre, unidades vendidas y lista de Canciones
 */
public class Disco implements Serializable {
    private String Nombre;
    private int UnidadesVendidas;
    private ArrayList<Cancion> canciones;


    public Disco(String Nombre, int UnidadesVendidas) {
        this.Nombre = Nombre;
        this.UnidadesVendidas = UnidadesVendidas;
        canciones = new ArrayList<>();
    }

    /**
     * Agrega objeto cancion en la lista de Canciones del Disco
     * @param Nombre nombre de la Cancion
     * @param Duracion duracion de la Cancion
     * @param Reproducciones reproducciones de la Cancion
     * @param Sencillo boolean es Sencillo
     */
    public void AgregarCanciones(String Nombre, String Duracion, int Reproducciones, Boolean Sencillo) {
        if (Sencillo) {
            Sencillo nuevo = new Sencillo(Nombre, Duracion, Reproducciones);
            canciones.add(nuevo);
        }else {
            Cancion nuevo = new Cancion(Nombre, Duracion, Reproducciones);
            canciones.add(nuevo);
        }
    }

    /**
     * Devuelve el nombre del Disco
     * @return Nombre del Disco
     */
    public String getNombre() {return Nombre; }

    /**
     * Devuelve la cantidad de unidades vendidas del Disco
     * @return Cantidad de Unidades Vendidas del Disco
     */
    public int getUnidadesVendidas() {
        return UnidadesVendidas;
    }

    /**
     * Devuelve la lista de Canciones del Disco
     * @return ArrayList de Canciones del Disco
     */
    public ArrayList<Cancion> getCanciones() {return canciones;}


    /**
     * Genera la liquidacion de las Canciones del Disco
     * @param EsEmergente Boolean para Distinguir los porcentajes a usar
     * @return ArrayList de ObjetoLiquidacion perteneciente a las Reproducciones de las Canciones
     */
    public ArrayList<ObjetoLiquidacion> GetGananciaReproducciones(boolean EsEmergente)
    {
        ArrayList<ObjetoLiquidacion> ganancias = new ArrayList<>();
        ObjetoLiquidacion Aux = new ObjetoLiquidacion();
        for (Cancion act : canciones)
        {
            Aux = new ObjetoLiquidacion();
            double Cant = act.getReproduccionesLiquidacion();
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

    /**
     * Genera la liquidacion del Disco
     * @param EsEmergente Boolean para Distinguir los porcentajes a usar
     * @return ObjetoLiquidacion perteneciente a las unidades vendidas del Disco
     */
    public ObjetoLiquidacion GetGananciaDisco(boolean EsEmergente)
    {
        if(EsEmergente)
            return new ObjetoLiquidacion(Nombre, (UnidadesVendidas * canciones.size() * Constantes.ValorCancion) * Constantes.PorcentajeArtistaEmergente);
        else
            return new ObjetoLiquidacion(Nombre, (UnidadesVendidas * canciones.size() * Constantes.ValorCancion) * Constantes.PorcentajeArtistaConsagrado);
    }
}