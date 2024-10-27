import java.util.ArrayList;

public class Disco {
    private int UnidadesVendidas;
    private ArrayList<Cancion> canciones;


    public Disco() {
        UnidadesVendidas = 0;
        canciones = new ArrayList<>();
    }

    public int getUnidadesVendidas() {
        return UnidadesVendidas;
    }
}

