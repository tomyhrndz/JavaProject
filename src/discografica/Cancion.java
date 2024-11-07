package discografica;

public class Cancion {
    private String Nombre;
    private String Duracion;
    private int CantReproducciones;

    public Cancion(String Nombre, String Duracion, int CantReproducciones) {
        this.Nombre = Nombre;
        this.Duracion = Duracion;
        this.CantReproducciones = CantReproducciones;
    }

    public void setCantReproducciones(int CantR) {
        CantReproducciones = CantR;
    }

    public int getCantReproducciones() {
        return CantReproducciones;
    }

    public String getNombre() {
        return Nombre;
    }
}

