public record ResultadoParalelo(Extremos extremos, long tiempoTp, int hilosActivos) {

    public void imprimirReporte() {
        System.out.println("=== Reporte Paralelo ===");
        System.out.println("Distancia minima: " + extremos.distMin()
                + " (par i=" + extremos.iMin() + ", j=" + extremos.jMin() + ")");
        System.out.println("Distancia maxima: " + extremos.distMax()
                + " (par i=" + extremos.iMax() + ", j=" + extremos.jMax() + ")");
        System.out.println("Hilos activos (con pares evaluados): " + hilosActivos);
        System.out.println("Tiempo paralelo (Tp): " + tiempoTp
                + " ns (" + (tiempoTp / 1_000_000_000.0) + " s)");
    }
}