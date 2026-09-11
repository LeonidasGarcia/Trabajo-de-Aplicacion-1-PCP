public final class Distancia {
    public static double euclidiana(int[] puntoI, int[] puntoJ) {
        double suma = 0.0;
        for (int k = 0; k < puntoI.length; k++) {
            double diferencia = puntoI[k] - puntoJ[k];
            suma += diferencia * diferencia;
        }
        return Math.sqrt(suma);
    }
}