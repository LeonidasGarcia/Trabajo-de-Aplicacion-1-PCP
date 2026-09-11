import java.io.IOException;

public class ProcesadorParalelo {

    public ResultadoParalelo procesar(int N, int n, int H) throws IOException {
        int W = n * 4;
        int totalIndices = N - 1;
        int base = totalIndices / H;
        int resto = totalIndices % H;

        HiloParalelo[] hilos = new HiloParalelo[H];
        
        int comienzo = 0;

        // Creación de hilos
        for (int h = 0; h < H; h++) {
            // Se asigna un indice mas a los primeros resto hilos
            int tamanno = base + (h < resto ? 1 : 0);
            int fin = comienzo + tamanno;
            hilos[h] = new HiloParalelo(comienzo, fin, n, W, N);
            comienzo = fin;
        }

        long inicio = System.nanoTime();

        // Creación física y arranque del hilo
        for (HiloParalelo hilo : hilos) {
            // Ejecuta run en el hilo
            hilo.start();
        }

        // Bloquea el hilo principal (main) hasta que todos los hilos terminen
        for (HiloParalelo hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IOException("Interrumpido: " + e.getMessage());
            }
        }

        long tiempoTp = System.nanoTime() - inicio;

        int hilosActivos = 0;

        AcumuladorExtremos extremos = new AcumuladorExtremos();

        for (HiloParalelo hilo : hilos) {
            if (hilo.getError() != null) {
                throw new IOException("Error en hilo de trabajo: " + hilo.getError());
            }

            ExtremosLocales locales = hilo.getExtremosLocales();

            // Si el hilo no evaluó ningun par, se descarta del resultado (Sucede cuando H > N)
            if (!locales.tienePares()) {
                continue;
            }

            hilosActivos++;

            extremos.registrar(locales.extremos().distMin(), locales.extremos().iMin(), locales.extremos().jMin());
            extremos.registrar(locales.extremos().distMax(), locales.extremos().iMax(), locales.extremos().jMax());
        }

        return new ResultadoParalelo(extremos.toExtremos(), tiempoTp, hilosActivos);
    }
}