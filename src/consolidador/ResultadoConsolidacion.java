public final class ResultadoConsolidacion {

    public static final String CONSISTENTE = "CONSISTENTE";
    public static final String DISCREPANCIA_CRITICA = "DISCREPANCIA_CRITICA";

    private final String estado;
    private final ResultadoSerial serial;
    private final ResultadoParalelo paralelo;
    private final double diferenciaMin;
    private final double diferenciaMax;
    private final double umbral;
    private final boolean equivalente;
    private final Telemetria telemetria;

    public ResultadoConsolidacion(String estado, ResultadoSerial serial, ResultadoParalelo paralelo,
            double diferenciaMin, double diferenciaMax, double umbral, boolean equivalente, Telemetria telemetria) {
        this.estado = estado;
        this.serial = serial;
        this.paralelo = paralelo;
        this.diferenciaMin = diferenciaMin;
        this.diferenciaMax = diferenciaMax;
        this.umbral = umbral;
        this.equivalente = equivalente;
        this.telemetria = telemetria;
    }

    public String getEstado() {
        return estado;
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

    public boolean esEquivalente() {
        return equivalente;
    }

    public boolean esConsistente() {
        return CONSISTENTE.equals(estado);
    }

    public Telemetria getTelemetria() {
        return telemetria;
    }
}