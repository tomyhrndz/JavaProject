import java.time.LocalDate;

public class Recital {
    private LocalDate Fecha;
    private float Recaudacion;
    private float CostoProduccion;

    public Recital(int year, int mes, int dia) {
        this.Fecha = LocalDate.of(year, mes, dia);
        this.Recaudacion = 0;
        this.CostoProduccion = 0;
    };

    public void setCostoProduccion(float costoProduccion) {
        CostoProduccion = costoProduccion;
    }

    public void setRecaudacion(float recaudacion) {
        Recaudacion = recaudacion;
    }
}
