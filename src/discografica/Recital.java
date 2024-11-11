package discografica;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Recital implements Serializable {
    private LocalDate Fecha;
    private float Recaudacion;
    private float CostoProduccion;

    public Recital(int year, int mes, int dia, float Recaudacion, float CostoProduccion) {
        this.Fecha = LocalDate.of(year, mes, dia);
        this.Recaudacion = Recaudacion;
        this.CostoProduccion = CostoProduccion;
    }

    /**
     * Genera Liquidacion del Recital
     * @param EsEmergente Boolean para definir porcentajes
     * @return Objeto Liquidacion del Recital
     */
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

    /**
     * Devuelve la fecha del recital
     * @return fecha del recital
     */
    public LocalDate GetFecha() { return Fecha; }

    /**
     * Devuelve la recaudacion del recital
     * @return ecaudacion del recital
     */
    public float GetRecaudacion() { return Recaudacion; }

    /**
     * Devuelve el costo de produccion del recital
     * @return costo del recital
     */
    public float GetCostoProduccion() { return CostoProduccion; }
}
