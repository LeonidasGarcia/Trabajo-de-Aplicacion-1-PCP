import java.io.IOException;
import java.io.RandomAccessFile;

public class HiloParalelo extends Thread {

    private final int comienzo;
    private final int fin;
    private final int n;
    private final int W;
    private final int N;

    private ExtremosLocales extremosLocales;
    private String error;

    public HiloParalelo(int comienzo, int fin, int n, int W, int N) {
        this.comienzo = comienzo;
        this.fin = fin;
        this.n = n;
        this.W = W;
        this.N = N;
    }

    @Override
    public void run() {
        AcumuladorExtremos acumulador = new AcumuladorExtremos();
        long paresEvaluados = 0L;
        try {
            RandomAccessFile archivo = new RandomAccessFile(GeneradorDataset.NOMBRE_ARCHIVO, "r");
            try {
                int[] puntoI = new int[n];
                int[] puntoJ = new int[n];
                for (int i = comienzo; i < fin; i++) {
                    leerPunto(archivo, i, puntoI);
                    for (int j = i + 1; j < N; j++) {
                        leerPunto(archivo, j, puntoJ);
                        acumulador.registrar(Distancia.euclidiana(puntoI, puntoJ), i, j);
                        paresEvaluados++;
                    }
                }
            } finally {
                archivo.close();
            }
        } catch (IOException e) {
            error = "Error de lectura (" + e.getClass().getSimpleName() + "): " + e.getMessage();
        }
        extremosLocales = new ExtremosLocales(acumulador.getDistMin(), acumulador.getDistMax(),
                acumulador.getIndiceIMin(), acumulador.getIndiceJMin(),
                acumulador.getIndiceIMax(), acumulador.getIndiceJMax(),
                paresEvaluados);
    }

    private void leerPunto(RandomAccessFile archivo, int indice, int[] destino) throws IOException {
        archivo.seek((long) indice * W);
        for (int d = 0; d < n; d++) {
            destino[d] = archivo.readInt();
        }
    }

    public ExtremosLocales getExtremosLocales() {
        return extremosLocales;
    }

    public String getError() {
        return error;
    }
}