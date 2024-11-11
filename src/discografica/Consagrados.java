package discografica;

import java.io.Serializable;

/**
 * Clase Consagrado, extiende a Artista
 */
public class Consagrados extends Artista implements Serializable {

    public Consagrados(String ID, String Nombre, int CantIntegrantes, String Genero) {
        super(ID, Nombre, CantIntegrantes, Genero);
    }

    @Override
    public boolean EsEmergente(){ return false; }
}

