import java.util.Iterator;
import java.util.TreeSet;

public class Discografica {
    TreeSet<Artista> Artistas;

    public Discografica () {
        Artistas = new TreeSet<>();
    }

    public void ConsultaDatos(int CantInt, String Genero) {
        Iterator<Artista> iterator = Artistas.iterator();
        boolean encontro = false;
        Artista act = null;

        while(iterator.hasNext() && encontro) {
            act = iterator.next();
            if (CantInt == act.getCantIntegrantes() && Genero.equals(act.getGenero()))
                encontro = true;
        }

        if (encontro)
            act.Mostrar();
        else
            System.out.println("No se encontro");

    }



    public int bajaArtista(String ID) {
        Iterator<Artista> iterator = Artistas.iterator();
        boolean bandera = true;
        Artista act = null;

        while (iterator.hasNext() && bandera) { //Mientras no lo encuentre y exista un siguiente
            act = iterator.next();              //Avanza el iterator
            if (ID.equals(act.getID())) {
                bandera = false;                //Encontro el ID
            }
        }
        if (bandera) { //No encontro el ID
            return 0;
        } else { // Eliminar ID
            Artistas.remove(act);
            return 1;
        }
    }
}
