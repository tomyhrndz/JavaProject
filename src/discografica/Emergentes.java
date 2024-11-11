package discografica;

import java.time.LocalDate;
import java.io.Serializable;

/**
 * Clase Emergentes, extiende a Artista.
 */
public class Emergentes extends Artista implements Serializable {

    public Emergentes(String ID, String Nombre, int CantIntegrantes, String Genero) {
        super(ID, Nombre, CantIntegrantes, Genero);
    }
}

