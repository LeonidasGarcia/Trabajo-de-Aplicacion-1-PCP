public record ResultadoSerial(Extremos extremos, long tiempoTs) {

    public void imprimirReporte() {
        System.out.println("=== Reporte Serial ===");
        System.out.println("Distancia minima: " + extremos.distMin()
                + " (par i=" + extremos.iMin() + ", j=" + extremos.jMin() + ")");
        System.out.println("Distancia maxima: " + extremos.distMax()
                + " (par i=" + extremos.iMax() + ", j=" + extremos.jMax() + ")");
        System.out.println("Tiempo serial (Ts): " + (tiempoTs / 1_000_000.0) + " ms");
    }
}