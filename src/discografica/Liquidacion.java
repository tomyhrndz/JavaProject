package discografica;

import java.util.ArrayList;

public class Liquidacion {
    private ArrayList<ObjetoLiquidacion> LiquidacionDisco;//Nombre de cada disco con su respectiva ganancia
    private ArrayList<ObjetoLiquidacion> LiquidacionReproducciones;//Nombre de cada cancion con su respectiva ganancia
    private ArrayList<ObjetoLiquidacion> LiquidacionRecitales;//Fecha de cada recital con su respectiva ganancia

    public Liquidacion() {
        LiquidacionDisco = new ArrayList<>();
        LiquidacionReproducciones = new ArrayList<>();
        LiquidacionRecitales = new ArrayList<>();
    }

    public void addReproducciones(ArrayList<ObjetoLiquidacion> liqRep){
        LiquidacionReproducciones.addAll(liqRep);
    }

    public void addRecitales(ObjetoLiquidacion liqRec){
        LiquidacionRecitales.add(liqRec);
    }

    public void addDisco(ObjetoLiquidacion liqDisco){
        LiquidacionDisco.add(liqDisco);
    }

    public ArrayList<ObjetoLiquidacion> getLiquidacionDisco() {
        return LiquidacionDisco;
    }

    public ArrayList<ObjetoLiquidacion> getLiquidacionReproducciones() {
        return LiquidacionReproducciones;
    }

    public ArrayList<ObjetoLiquidacion> getLiquidacionRecitales() {
        return LiquidacionRecitales;
    }
}



