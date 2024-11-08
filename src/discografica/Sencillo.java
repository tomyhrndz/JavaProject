package discografica;

import java.io.Serializable;

public class Sencillo extends Cancion implements Serializable {

    public Sencillo(String Nombre, String Duracion, int Reproducciones) {
        super(Nombre, Duracion, Reproducciones);
    }

    @Override
    public int getCantReproducciones() {
        return (int) (super.getCantReproducciones() * 1.5);
    }
}
