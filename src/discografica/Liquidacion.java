package discografica;

import java.util.ArrayList;

public class Liquidacion {
    private ArrayList<ObjetoLiquidacion> LiquidacionDisco;//Nombre de cada disco con su respectiva ganancia
    private ArrayList<ObjetoLiquidacion> LiquidacionReproducciones;//Nombre de cada cancion con su respectiva ganancia
    private ArrayList<ObjetoLiquidacion> LiquidacionRecitales;//Fecha de cada recital con su respectiva ganancia

    public void addReproducciones(ArrayList<ObjetoLiquidacion> liqRep){
        LiquidacionReproducciones.addAll(liqRep);
    }

    public void addRecitales(ObjetoLiquidacion liqRec){
        LiquidacionReproducciones.add(liqRec);
    }

    public void addDisco(ObjetoLiquidacion liqDisco){
        LiquidacionDisco.add(liqDisco);
    }
}



