package discografica;

import java.io.Serializable;

/**
 * Clase Sencillo, extiende a Cancion
 */
public class Sencillo extends Cancion implements Serializable {

    public Sencillo(String Nombre, String Duracion, int Reproducciones) {
        super(Nombre, Duracion, Reproducciones);
    }

    @Override
    public boolean esSencillo(){
        return true;
    }
}
