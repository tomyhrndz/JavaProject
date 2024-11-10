package discografica;

public class ObjetoLiquidacion {
    String Descripcion;
    double Monto;

    public ObjetoLiquidacion(String Descripcion, double Monto)
    {
        this.Descripcion = Descripcion;
        this.Monto = Monto;
    }

    public ObjetoLiquidacion()
    {
        this.Descripcion = "";
        this.Monto = 0;
    }
}
