package discografica;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Recital implements Serializable {
    private LocalDate Fecha;
    private float Recaudacion;
    private float CostoProduccion;

    public Recital(int year, int mes, int dia, float Recaudacion, float CostoProduccion) {
        this.Fecha = LocalDate.of(year, mes, dia);
        this.Recaudacion = Recaudacion;
        this.CostoProduccion = CostoProduccion;
    };

    public void setCostoProduccion(float costoProduccion) {
        CostoProduccion = costoProduccion;
    }

    public void setRecaudacion(float recaudacion) {
        Recaudacion = recaudacion;
    }

    public ObjetoLiquidacion GetGananciaRecital(boolean EsEmergente)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = Fecha.format(formatter);

        double GanaciaRecital = Recaudacion - CostoProduccion;

        if(EsEmergente)
            GanaciaRecital *= Constantes.PorcentajeArtistaEmergente;
        else
            GanaciaRecital *= Constantes.PorcentajeArtistaConsagrado;

        ObjetoLiquidacion ObjetoRecitalLiquidacion = new ObjetoLiquidacion(formattedDate, GanaciaRecital);

        return ObjetoRecitalLiquidacion;
    }

    public LocalDate GetFecha() { return Fecha; }
    public float GetRecaudacion() { return Recaudacion; }
    public float GetCostoProduccion() { return CostoProduccion; }
}
