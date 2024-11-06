package discografica;

public class Sencillo extends Canciones{

    public Sencillo(String Nombre, String Duracion) {
        super(Nombre, Duracion);
    }

    @Override
    public int getCantReproducciones() {
        return (int) (super.getCantReproducciones() * 1.5);
    }
}
