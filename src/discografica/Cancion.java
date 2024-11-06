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

    public void Mostrar() {
        System.out.println("========Canciones========");
        System.out.println("      Nombre de : " + Nombre);
        System.out.println("      Duracion " + Duracion);
        System.out.println("      Cantidad de reproducciones: " + CantReproducciones);
    }
}

