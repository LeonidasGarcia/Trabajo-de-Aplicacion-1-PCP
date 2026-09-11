public final class GeneradorCoordenadas {
    public static int generar(int limiteInferior, int limiteSuperior) {
        long rango = (long) limiteSuperior - limiteInferior + 1;
        long desvio = (long) (Math.random() * rango);
        return (int) (limiteInferior + desvio);
    }
}