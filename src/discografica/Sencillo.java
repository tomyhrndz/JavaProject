package discografica;

public class Sencillo extends Cancion{

    public Sencillo(String Nombre, String Duracion, int Reproducciones) {
        super(Nombre, Duracion, Reproducciones);
    }

    @Override
    public int getCantReproducciones() {
        return (int) (super.getCantReproducciones() * 1.5);
    }
}
