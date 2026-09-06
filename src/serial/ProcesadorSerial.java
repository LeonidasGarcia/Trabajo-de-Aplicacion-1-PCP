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

        return new ResultadoSerial(extremos.getDistMin(), extremos.getDistMax(),
                extremos.getIndiceIMin(), extremos.getIndiceJMin(),
                extremos.getIndiceIMax(), extremos.getIndiceJMax(), tiempoTs);
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Uso: ProcesadorSerial <N> <n>");
            return;
        }
        try {
            int N = Integer.parseInt(args[0]);
            int n = Integer.parseInt(args[1]);
            ResultadoSerial resultado = new ProcesadorSerial().procesar(N, n);
            System.out.println("=== Reporte Serial ===");
            System.out.println("Distancia minima: " + resultado.getDistMin()
                    + " (par i=" + resultado.getIndiceIMin() + ", j=" + resultado.getIndiceJMin() + ")");
            System.out.println("Distancia maxima: " + resultado.getDistMax()
                    + " (par i=" + resultado.getIndiceIMax() + ", j=" + resultado.getIndiceJMax() + ")");
            System.out.println("Tiempo serial (Ts): " + resultado.getTiempoTs()
                    + " ns (" + (resultado.getTiempoTs() / 1_000_000_000.0) + " s)");
        } catch (NumberFormatException e) {
            System.err.println("Error: los argumentos deben ser enteros validos");
        } catch (IOException e) {
            System.err.println("Error al leer dataset.dat: " + e.getMessage());
        }
    }
}