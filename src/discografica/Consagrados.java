package discografica;

import java.io.Serializable;

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

        for(Recital recital : getRecitales())
        {
            GananciaRecitales += recital.GetGananciaRecital();
        }

        GananciaDisco *= Constantes.PorcentajeArtistaConsagrado;
        GananciaReproducciones *= Constantes.PorcentajeArtistaConsagrado;
        GananciaRecitales *= Constantes.PorcentajeArtistaConsagrado;

        return new Liquidacion(GananciaDisco, GananciaReproducciones, GananciaRecitales);
    }
}

