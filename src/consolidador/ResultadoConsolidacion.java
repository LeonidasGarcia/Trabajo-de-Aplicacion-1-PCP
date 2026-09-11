public final class ResultadoConsolidacion {

    private final ResultadoSerial serial;
    private final ResultadoParalelo paralelo;
    private final double diferenciaMin;
    private final double diferenciaMax;
    private final double umbral;
    private final boolean equivalente;
    private final Telemetria telemetria;

    public ResultadoConsolidacion(ResultadoSerial serial, ResultadoParalelo paralelo,
            double diferenciaMin, double diferenciaMax, double umbral, boolean equivalente, Telemetria telemetria) {
        this.serial = serial;
        this.paralelo = paralelo;
        this.diferenciaMin = diferenciaMin;
        this.diferenciaMax = diferenciaMax;
        this.umbral = umbral;
        this.equivalente = equivalente;
        this.telemetria = telemetria;
    }

    public ResultadoSerial getSerial() {
        return serial;
    }

    public ResultadoParalelo getParalelo() {
        return paralelo;
    }

    public double getDiferenciaMin() {
        return diferenciaMin;
    }

    public double getDiferenciaMax() {
        return diferenciaMax;
    }

    public double getUmbral() {
        return umbral;
    }

    public boolean esConsistente() {
        return equivalente;
    }

    public Telemetria getTelemetria() {
        return telemetria;
    }
}