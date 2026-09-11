public final class GeneradorCoordenadas {
    public static int generar(int limiteInferior, int limiteSuperior) {
        // Casteo a long para evitar overflow silencioso
        long rango = (long) limiteSuperior - limiteInferior + 1;
        long desvio = (long) (Math.random() * rango);

        // el desvio esta acotado entre B - A o menor a este valor entonces el valor retornado a lo mas es B
        return (int) (limiteInferior + desvio);
    }
}