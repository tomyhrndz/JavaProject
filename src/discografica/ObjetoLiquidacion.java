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

    /**
     * Establecer Descripcion de la Liquidacion
     * @param Descripcion descripcion de la liquidacion
     */
    public void setDescripcion(String Descripcion){
        this.Descripcion = Descripcion;
    }

    /**
     * Establecer Monto de la Liquidacion
     * @param Monto Monto de la liquidacion
     */
    public void setMonto(double Monto){
        this.Monto = Monto;
    }

    /**
     * Devuelve el monto de la Liquidacion
     * @return Monto de la Liquidacion
     */
    public double getMonto() {
        return Monto;
    }

    /**
     * Devuelve la descripcion de la Liquidacion
     * @return Descripcion de la Liquidacion
     */
    public String getDescripcion() {
        return Descripcion;
    }
}
