package discografica;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Clase Artista que contiene los datos, discos y recitales del mismo.
 */
public class Artista implements Comparable<Artista>, Serializable {
    private String ID;
    private String Nombre;
    private int CantIntegrantes;
    private String Genero;
    private HashSet<Disco> Discos;
    private ArrayList<Recital> Recitales;

    public Artista(String ID, String Nombre, int CantIntegrantes, String Genero) {
        this.ID = ID;
        this.Nombre = Nombre;
        this.CantIntegrantes = CantIntegrantes;
        this.Genero = Genero;
        Discos = new HashSet<>();
        Recitales = new ArrayList<>();
    }

    /**
     * Devuelve si el artista es emergente
     * @return True (Es emergente) o False(Es consagrado)
     */
    public boolean EsEmergente(){ return true; }

    @Override
    public int compareTo(Artista A) {
        return this.ID.compareTo(A.getID());
    }

    /**
     * Agrega un Objeto Disco al HashSet Discos
     * @param nuevo Disco nuevo
     */
    public void CargarDisco(Disco nuevo) {
        Discos.add(nuevo);
    }

    /**
     * Agrega un Objeto Recital al ArrayList Recitales
     * @param nuevo Recital nuevo
     */
    public void CargarRecital (Recital nuevo) {
        Recitales.add(nuevo);
    }

    /**
     * Devuelve el ID del Artista
     * @return ID del Artista
     */
    public String getID(){
        return ID;
    }

    /**
     *Devuelve el nombre del Artista
     * @return Nombre Artista
     */
    public String getNombre() {return Nombre; }

    /**
     * Devuelve el genero musical del Artista
     * @return Genero Muscial Artista
     */
    public String getGenero() {
        return Genero;
    }

    /**
     * Devuelve la cantidad de integrantes del Artista
     * @return Cantidad de Integrantes del Artista
     */
    public int getCantIntegrantes() {
        return CantIntegrantes;
    }

    /**
     * Devuelve Discos del Artista
     * @return HashSet de Discos del Artista
     */
    public HashSet<Disco> getDiscos(){ return Discos; }

    /**
     * Devuelve Recitales del Artista
     * @return ArrayList Recitales del Artista
     */
    public ArrayList<Recital> getRecitales(){ return Recitales; }

    /**
     * Calcula la liquidacion del artista teniendo en cuenta venta de discos,
     *  reproducciones de canciones y recitales en el mes.
     * @param fecha
     * @return Objeto liquidacion del Artista en la fecha especificada
     */
    public Liquidacion getLiquidacion(YearMonth fecha)
    {
        Liquidacion liquidacionGanancia = new Liquidacion();

        for(Disco disco : getDiscos())
        {
            liquidacionGanancia.addDisco(disco.GetGananciaDisco(EsEmergente()));
            liquidacionGanancia.addReproducciones(disco.GetGananciaReproducciones(EsEmergente()));
        }

        int ActMonth = fecha.getMonthValue();
        int ActYear = fecha.getYear();
        LocalDate DateRecital;

        for(Recital recital : getRecitales())
        {
            DateRecital =  recital.GetFecha();
            if(DateRecital.getMonthValue() == ActMonth && DateRecital.getYear() == ActYear)
                liquidacionGanancia.addRecitales(recital.GetGananciaRecital(EsEmergente()));
        }

        return liquidacionGanancia;
    }

}
