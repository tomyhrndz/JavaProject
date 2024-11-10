package discografica;

import java.io.Serializable;
import java.time.LocalDate;

public class Consagrados extends Artista implements Serializable {

    public Consagrados(String ID, String Nombre, int CantIntegrantes, String Genero) {
        super(ID, Nombre, CantIntegrantes, Genero);
    }

    @Override
    public boolean EsEmergente(){ return false; }

    /*
    @Override
    public Liquidacion getLiquidacion()
    {
        Liquidacion LiquidacionGanancia = new Liquidacion();

        for(Disco disco : getDiscos())
        {
            LiquidacionGanancia.LiquidacionReproducciones.addAll(disco.GetGananciaReproducciones(false));
            LiquidacionGanancia.LiquidacionDisco.add(disco.GetGananciaDisco(false));
        }

        int ActMonth = LocalDate.now().getMonthValue();
        int ActYear = LocalDate.now().getYear();
        LocalDate DateRecital;

        for(Recital recital : getRecitales())
        {
            DateRecital =  recital.GetFecha();
            if(DateRecital.getMonthValue() == ActMonth && DateRecital.getYear() == ActYear)
                LiquidacionGanancia.LiquidacionRecitales.add(recital.GetGananciaRecital(false));
        }

        return LiquidacionGanancia;
    }
     */
}

