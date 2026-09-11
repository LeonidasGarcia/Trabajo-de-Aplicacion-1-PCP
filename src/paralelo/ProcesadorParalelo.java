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

        int hilosActivos = 0;
        AcumuladorExtremos extremos = new AcumuladorExtremos();

        for (HiloParalelo hilo : hilos) {
            if (hilo.getError() != null) {
                throw new IOException("Error en hilo de trabajo: " + hilo.getError());
            }
            ExtremosLocales locales = hilo.getExtremosLocales();
            if (!locales.tienePares()) {
                continue;
            }
            hilosActivos++;
            extremos.registrar(locales.extremos().distMin(), locales.extremos().iMin(), locales.extremos().jMin());
            extremos.registrar(locales.extremos().distMax(), locales.extremos().iMax(), locales.extremos().jMax());
        }

        long tiempoTp = System.nanoTime() - inicio;

        return new ResultadoParalelo(extremos.toExtremos(), tiempoTp, hilosActivos);
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
            resultado.imprimirReporte();
        } catch (NumberFormatException e) {
            System.err.println("Error: los argumentos deben ser enteros validos");
        } catch (IOException e) {
            System.err.println("Error al leer dataset.dat: " + e.getMessage());
        }
    }
}