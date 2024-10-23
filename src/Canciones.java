public class Canciones {
    private String Nombre;
    private String Duracion;
    private int CantReproducciones;

    public Canciones(String Nombre, String Duracion) {
        this.Nombre = Nombre;
        this.Duracion = Duracion;
        CantReproducciones = 0;
    }

    public void setCantReproducciones(int CantR) {
        CantReproducciones += CantR;
    }

    public int getCantReproducciones() {
        return CantReproducciones;
    }
}

