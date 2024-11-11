package discografica;

import java.io.Serializable;

public class Cancion implements Serializable {
    private String Nombre;
    private String Duracion;
    private int CantReproducciones;

    public Cancion(String Nombre, String Duracion, int CantReproducciones) {
        this.Nombre = Nombre;
        this.Duracion = Duracion;
        this.CantReproducciones = CantReproducciones;
    }

    /**
     * Devuelve la cantidad de reproducciones de la cancion
     * @return Cantidad Reproducciones
     */
    public int getCantReproducciones() {
        return CantReproducciones;
    }

    public int getReproduccionesLiquidacion() {
        return (esSencillo() ? ((int)(CantReproducciones* 1.5) ): CantReproducciones);
    }

    /**
     * Comprueba si la Cancion es sencillo
     * @return True (Es Sencillo) o False (No es Sencillo)
     */
    public boolean esSencillo() {
        return false;
    }

    /**
     * Devuelve el nombre de la Cancion
      * @return Nombre de la Cancion
     */
    public String getNombre() {
        return Nombre;
    }

    /**
     * Devuelve la duracion de la Cancion
     * @return Duracion de la Cancion
     */
	public String getDuracion(){return Duracion;}

}

