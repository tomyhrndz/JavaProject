package discografica;

import java.io.Serializable;

public class Sencillo extends Cancion implements Serializable {

    public Sencillo(String Nombre, String Duracion, int Reproducciones) {
        super(Nombre, Duracion, Reproducciones);
    }

    @Override
    public boolean esSencillo(){
        return true;
    }
}
