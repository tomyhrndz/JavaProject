package discografica;

import java.io.Serializable;

public class Emergentes extends Artista implements Serializable {

    public Emergentes(String ID, String Nombre, int CantIntegrantes, String Genero) {
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

        GananciaDisco *= Constantes.PorcentajeArtistaEmergente;
        GananciaReproducciones *= Constantes.PorcentajeArtistaEmergente;
        GananciaRecitales *= Constantes.PorcentajeArtistaEmergente;

        return new Liquidacion(GananciaDisco, GananciaReproducciones, GananciaRecitales);
    }
}

