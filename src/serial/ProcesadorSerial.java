import java.io.IOException;

public class ProcesadorSerial {

    public ResultadoSerial procesar(int N, int n) throws IOException {
        AcumuladorExtremos extremos = new AcumuladorExtremos();

        int W = n * 4;
        long inicio = 0L;
        LectorDataset lector = new LectorDataset();
        try {
            int[] puntoI = new int[n];
            int[] puntoJ = new int[n];
            inicio = System.nanoTime();
            for (int i = 0; i < N - 1; i++) {
                lector.leerPunto(i, n, W, puntoI);
                for (int j = i + 1; j < N; j++) {
                    lector.leerPunto(j, n, W, puntoJ);
                    extremos.registrar(Distancia.euclidiana(puntoI, puntoJ), i, j);
                }
            }
        } finally {
            lector.cerrar();
        }
        long tiempoTs = System.nanoTime() - inicio;

        return new ResultadoSerial(extremos.toExtremos(), tiempoTs);
    }
}