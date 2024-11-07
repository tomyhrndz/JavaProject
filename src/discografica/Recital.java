package discografica;

import java.time.LocalDate;

public class Recital {
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

    public float GetGananciaRecital(){ return Recaudacion - CostoProduccion; }
}
