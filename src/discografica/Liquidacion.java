package discografica;

import java.util.ArrayList;

/**
 * Clase Dedicada la Liquidacion de Artistas
 */
public class Liquidacion {
    private ArrayList<ObjetoLiquidacion> LiquidacionDisco;//Nombre de cada disco con su respectiva ganancia
    private ArrayList<ObjetoLiquidacion> LiquidacionReproducciones;//Nombre de cada cancion con su respectiva ganancia
    private ArrayList<ObjetoLiquidacion> LiquidacionRecitales;//Fecha de cada recital con su respectiva ganancia

    public Liquidacion() {
        LiquidacionDisco = new ArrayList<>();
        LiquidacionReproducciones = new ArrayList<>();
        LiquidacionRecitales = new ArrayList<>();
    }

    /**
     *Agrega una liquidacion de Canciones al ArrayList Liquidacion Reproduccionoes
     * @param liqRep liquidacion de Reproducciones
     */
    public void addReproducciones(ArrayList<ObjetoLiquidacion> liqRep){
        LiquidacionReproducciones.addAll(liqRep);
    }
    /**
     *Agrega una liquidacion de Recitales al ArrayList Liquidacion Recitales
     * @param liqRec liquidacion de Reproducciones
     */
    public void addRecitales(ObjetoLiquidacion liqRec){
        LiquidacionRecitales.add(liqRec);
    }

    /**
     *Agrega una liquidacion de Disco al ArrayList Liquidacion Disco
     * @param liqDisco liquidacion de Disco
     */
    public void addDisco(ObjetoLiquidacion liqDisco){
        LiquidacionDisco.add(liqDisco);
    }

    /**
     * Devuelve las Liquidacion de Disco
     * @return ArrayList ObjetoLiquidacion de Disco
     */
    public ArrayList<ObjetoLiquidacion> getLiquidacionDisco() {
        return LiquidacionDisco;
    }

    /**
     * Devuelve las Liquidacion de Reproducciones de Canciones
     * @return ArrayList ObjetoLiquidacion de Cancciones
     */
    public ArrayList<ObjetoLiquidacion> getLiquidacionReproducciones() {
        return LiquidacionReproducciones;
    }

    /**
     * Devuelve las Liquidacion de Recitales
     * @return ArrayList ObjetoLiquidacion de Racitales
     */
    public ArrayList<ObjetoLiquidacion> getLiquidacionRecitales() {
        return LiquidacionRecitales;
    }
}



