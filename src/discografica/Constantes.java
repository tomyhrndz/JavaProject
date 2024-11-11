package discografica;

/**
 * Clase de Constantes para la liquidacion de Artistas
 */
public class Constantes {
    //Discos
    public static final double ValorCancion = 500; //Valor de cada cancion por disco

    //Reproducciones
    public static final double GananciaxReproduccion = 6; //Valor de cada reproduccion
    public static final double PorcentajeMayor5000 = 1.02; //Porcentaje de ganancia si no supera 5000 reproducciones
    public static final double PorcentajeMenor10000 = 1.05; //Porcentaje de ganancia si supera 5000 reproducciones pero no excede las 10000
    public static final double PorcentajeMayor10000 = 1.1; //Porcentaje de ganancia si supera las 10000 reproducciones

    //Ganancias por tipo Artista
    public static final double PorcentajeArtistaEmergente = 0.15; //Porcentaje de ganancia de artista tipo Emergentes
    public static final double PorcentajeArtistaConsagrado = 0.25; //Porcentaje de ganancia de artista tipo Consagrados
}
