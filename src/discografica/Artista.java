package discografica;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

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

    public boolean EsEmergente(){ return true; }

    @Override
    public int compareTo(Artista A) {
        return this.ID.compareTo(A.getID());
    }

    public void CargarDisco(Disco nuevo) {
        Discos.add(nuevo);
    }

    public void CargarRecital (Recital nuevo) {
        Recitales.add(nuevo);
    }

    public String getID(){
        return ID;
    }

    public String getNombre() {return Nombre; }

    public String getGenero() {
        return Genero;
    }

    public int getCantIntegrantes() {
        return CantIntegrantes;
    }

    public HashSet<Disco> getDiscos(){ return Discos; }

    public ArrayList<Recital> getRecitales(){ return Recitales; }

    public Liquidacion getLiquidacion(LocalDate fecha)
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
