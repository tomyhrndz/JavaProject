package discografica;

public class ObjetoLiquidacion {
    private String Descripcion;
    private double Monto;

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

    public void setDescripcion(String Descripcion){
        this.Descripcion = Descripcion;
    }
    public void setMonto(double Monto){
        this.Monto = Monto;
    }

    public double getMonto() {
        return Monto;
    }

    public String getDescripcion() {
        return Descripcion;
    }
}
