package discografica;

import java.io.Serializable;
import java.time.LocalDate;

public class Consagrados extends Artista implements Serializable {

    public Consagrados(String ID, String Nombre, int CantIntegrantes, String Genero) {
        super(ID, Nombre, CantIntegrantes, Genero);
    }

    @Override
    public boolean EsEmergente(){ return false; }
}

