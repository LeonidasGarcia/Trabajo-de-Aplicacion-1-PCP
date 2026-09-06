public final class Telemetria {

    private final Double speedup;
    private final Double eficiencia;
    private final Double reduccion;

    public Telemetria(long tiempoTs, long tiempoTp, int H) {
        this.speedup = tiempoTp > 0 ? (double) tiempoTs / tiempoTp : null;
        this.eficiencia = (speedup != null && H > 0) ? (speedup / H) * 100.0 : null;
        this.reduccion = tiempoTs > 0 ? ((double) (tiempoTs - tiempoTp) / tiempoTs) * 100.0 : null;
    }

    public Double getSpeedup() {
        return speedup;
    }

    public Double getEficiencia() {
        return eficiencia;
    }

    public Double getReduccion() {
        return reduccion;
    }

    public static String formato(Double valor) {
        if (valor == null) {
            return "no aplicable";
        }
        return String.format("%.4f", valor);
    }
}