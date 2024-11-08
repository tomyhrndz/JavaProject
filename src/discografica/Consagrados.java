package discografica;

import java.io.Serializable;

public class Consagrados extends Artista implements Serializable {

    public Consagrados(String ID, String Nombre, int CantIntegrantes, String Genero) {
        super(ID, Nombre, CantIntegrantes, Genero);
    }
}

