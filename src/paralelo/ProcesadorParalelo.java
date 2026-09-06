import java.io.IOException;

public class ProcesadorParalelo {

    public ResultadoParalelo procesar(int N, int n, int H) throws IOException {
        long inicio = System.nanoTime();

        int W = n * 4;
        int totalIndices = N - 1;
        int base = totalIndices / H;
        int resto = totalIndices % H;

        HiloParalelo[] hilos = new HiloParalelo[H];
        int comienzo = 0;
        for (int h = 0; h < H; h++) {
            int tamanno = base + (h < resto ? 1 : 0);
            int fin = comienzo + tamanno;
            hilos[h] = new HiloParalelo(comienzo, fin, n, W, N);
            comienzo = fin;
        }

        for (HiloParalelo hilo : hilos) {
            hilo.start();
        }
        for (HiloParalelo hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IOException("Interrumpido: " + e.getMessage());
            }
        }

        double distMin = Double.MAX_VALUE;
        double distMax = -1.0;
        int iMin = -1;
        int jMin = -1;
        int iMax = -1;
        int jMax = -1;
        int hilosActivos = 0;

        for (HiloParalelo hilo : hilos) {
            if (hilo.getError() != null) {
                throw new IOException("Error en hilo de trabajo: " + hilo.getError());
            }
            ExtremosLocales locales = hilo.getExtremosLocales();
            if (!locales.tienePares()) {
                continue;
            }
            hilosActivos++;
            if (locales.getDistMin() < distMin) {
                distMin = locales.getDistMin();
                iMin = locales.getIndiceIMin();
                jMin = locales.getIndiceJMin();
            }
            if (locales.getDistMax() > distMax) {
                distMax = locales.getDistMax();
                iMax = locales.getIndiceIMax();
                jMax = locales.getIndiceJMax();
            }
        }

        long tiempoTp = System.nanoTime() - inicio;

        return new ResultadoParalelo(distMin, distMax, iMin, jMin, iMax, jMax, tiempoTp, hilosActivos);
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Uso: ProcesadorParalelo <N> <n> <H>");
            return;
        }
        try {
            int N = Integer.parseInt(args[0]);
            int n = Integer.parseInt(args[1]);
            int H = Integer.parseInt(args[2]);
            ResultadoParalelo resultado = new ProcesadorParalelo().procesar(N, n, H);
            System.out.println("=== Reporte Paralelo ===");
            System.out.println("Distancia minima: " + resultado.getDistMin()
                    + " (par i=" + resultado.getIndiceIMin() + ", j=" + resultado.getIndiceJMin() + ")");
            System.out.println("Distancia maxima: " + resultado.getDistMax()
                    + " (par i=" + resultado.getIndiceIMax() + ", j=" + resultado.getIndiceJMax() + ")");
            System.out.println("Hilos activos (con pares evaluados): " + resultado.getHilosActivos());
            System.out.println("Tiempo paralelo (Tp): " + resultado.getTiempoTp()
                    + " ns (" + (resultado.getTiempoTp() / 1_000_000_000.0) + " s)");
        } catch (NumberFormatException e) {
            System.err.println("Error: los argumentos deben ser enteros validos");
        } catch (IOException e) {
            System.err.println("Error al leer dataset.dat: " + e.getMessage());
        }
    }
}