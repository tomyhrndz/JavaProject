package discografica;

import java.time.LocalDate;
import java.io.Serializable;

public class Emergentes extends Artista implements Serializable {

    public Emergentes(String ID, String Nombre, int CantIntegrantes, String Genero) {
        super(ID, Nombre, CantIntegrantes, Genero);
    }

    /*
    @Override
    public Liquidacion getLiquidacion()
    {
        Liquidacion LiquidacionGanancia = new Liquidacion();

        for(Disco disco : getDiscos())
        {
            LiquidacionGanancia.LiquidacionReproducciones.addAll(disco.GetGananciaReproducciones(true));
            LiquidacionGanancia.LiquidacionDisco.add(disco.GetGananciaDisco(true));
        }

        int ActMonth = LocalDate.now().getMonthValue();
        int ActYear = LocalDate.now().getYear();
        LocalDate DateRecital;

        for(Recital recital : getRecitales())
        {
            DateRecital =  recital.GetFecha();
            if(DateRecital.getMonthValue() == ActMonth && DateRecital.getYear() == ActYear)
                LiquidacionGanancia.LiquidacionRecitales.add(recital.GetGananciaRecital(true));
        }

        return LiquidacionGanancia;
    }
     */
}

