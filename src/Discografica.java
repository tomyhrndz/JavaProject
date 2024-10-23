import java.util.TreeSet;
import java.util.Iterator;

public class Discografica {
    TreeSet<Artista> Artistas;

    public Discografica () {
        Artistas = new TreeSet<>();
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
