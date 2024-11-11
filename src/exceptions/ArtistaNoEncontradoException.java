package exceptions;

/**
 * Excepcion Artista no Encotrado.
 */
public class ArtistaNoEncontradoException extends RuntimeException {
    public ArtistaNoEncontradoException(String message) {
        super(message);
    }
}
