import java.io.IOException;

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
            LectorDataset lector = new LectorDataset();
            try {
                int[] puntoI = new int[n];
                int[] puntoJ = new int[n];
                for (int i = comienzo; i < fin; i++) {
                    lector.leerPunto(i, n, W, puntoI);
                    for (int j = i + 1; j < N; j++) {
                        lector.leerPunto(j, n, W, puntoJ);
                        acumulador.registrar(Distancia.euclidiana(puntoI, puntoJ), i, j);
                        paresEvaluados++;
                    }
                }
            } finally {
                lector.cerrar();
            }
        } catch (IOException e) {
            error = "Error de lectura (" + e.getClass().getSimpleName() + "): " + e.getMessage();
        }
        extremosLocales = new ExtremosLocales(acumulador.toExtremos(), paresEvaluados);
    }

    public ExtremosLocales getExtremosLocales() {
        return extremosLocales;
    }

    public String getError() {
        return error;
    }
}