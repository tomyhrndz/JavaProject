package discografica;

import java.io.Serializable;
import java.time.LocalDate;

public class Consagrados extends Artista implements Serializable {

    public Consagrados(String ID, String Nombre, int CantIntegrantes, String Genero) {
        super(ID, Nombre, CantIntegrantes, Genero);
    }

    @Override
    public Liquidacion getLiquidacion()
    {
        double GananciaDisco = 0;
        double GananciaReproducciones = 0;
        double GananciaRecitales = 0;
        for(Disco disco : getDiscos())
        {
            GananciaReproducciones += disco.GetGananciaReproducciones();
            GananciaDisco += disco.GetGananciaDisco();
        }

        int ActMonth = LocalDate.now().getMonthValue();
        int ActYear = LocalDate.now().getYear();

        GananciaRecitales = getRecitales().stream()
                .filter(item -> item.GetFecha().getMonthValue() == ActMonth && item.GetFecha().getYear() == ActYear)
                .mapToDouble(Recital :: GetGananciaRecital)
                .sum();

        GananciaDisco *= Constantes.PorcentajeArtistaConsagrado;
        GananciaReproducciones *= Constantes.PorcentajeArtistaConsagrado;
        GananciaRecitales *= Constantes.PorcentajeArtistaConsagrado;

        return new Liquidacion(GananciaDisco, GananciaReproducciones, GananciaRecitales);
    }
}

