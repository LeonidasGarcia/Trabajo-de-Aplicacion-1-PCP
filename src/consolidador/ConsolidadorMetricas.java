public class ConsolidadorMetricas {

    private static final double UMBRAL = 1e-9;
    private static final String ADVERTENCIA = "!ADVERTENCIA: Los resultados no coinciden! Posible condicion de carrera (Race Condition) detectada.";

    public ResultadoConsolidacion consolidar(ResultadoSerial serial, ResultadoParalelo paralelo, int H) {
        double diferenciaMin = Math.abs(serial.extremos().distMin() - paralelo.extremos().distMin());
        double diferenciaMax = Math.abs(serial.extremos().distMax() - paralelo.extremos().distMax());
        boolean equivalente = diferenciaMin < UMBRAL && diferenciaMax < UMBRAL;

        Telemetria telemetria = equivalente ? new Telemetria(serial.tiempoTs(), paralelo.tiempoTp(), H) : null;

        return new ResultadoConsolidacion(serial, paralelo,
                diferenciaMin, diferenciaMax, equivalente, telemetria);
    }

    public void imprimirReporte(ResultadoConsolidacion resultado, int H) {
        if (resultado.esConsistente()) {
            imprimirReporteExito(resultado);
        } else {
            imprimirReporteDiagnostico(resultado);
        }
    }

    private void imprimirReporteExito(ResultadoConsolidacion r) {
        ResultadoSerial s = r.getSerial();
        ResultadoParalelo p = r.getParalelo();
        Telemetria t = r.getTelemetria();

        System.out.println("=== Reporte de Consolidacion ===");
        System.out.println("Estado de la consolidacion: CONSISTENTE (equivalencia exitosa)");
        System.out.println("Diferencias frente al umbral " + UMBRAL + ": minima " + r.getDiferenciaMin()
                + ", maxima " + r.getDiferenciaMax());

        System.out.println();
        System.out.println("Tabla de tiempos y extremos:");
        System.out.println("Serial  | Ts = " + (s.tiempoTs() / 1_000_000.0) + " ms"
                + " | min " + s.extremos().distMin() + " (par i=" + s.extremos().iMin() + ", j=" + s.extremos().jMin() + ")"
                + " | max " + s.extremos().distMax() + " (par i=" + s.extremos().iMax() + ", j=" + s.extremos().jMax() + ")");
        System.out.println("Paralelo | Tp = " + (p.tiempoTp() / 1_000_000.0) + " ms"
                + " | min " + p.extremos().distMin() + " (par i=" + p.extremos().iMin() + ", j=" + p.extremos().jMin() + ")"
                + " | max " + p.extremos().distMax() + " (par i=" + p.extremos().iMax() + ", j=" + p.extremos().jMax() + ")");

        System.out.println();
        System.out.println("Telemetria de rendimiento:");
        System.out.println("Speedup: " + Telemetria.formato(t.getSpeedup()));
        System.out.println("Eficiencia: " + Telemetria.formato(t.getEficiencia()) + " %");
        System.out.println("Reduccion de tiempo: " + Telemetria.formato(t.getReduccion()) + " %");

        System.out.println();
        System.out.println("Analisis de aceleracion y eficiencia:");
        if (t.getSpeedup() != null) {
            System.out.println("- Speedup = " + Telemetria.formato(t.getSpeedup())
                    + ": la via paralela fue " + (t.getSpeedup() >= 1.0 ? "mas rapida" : "mas lenta")
                    + " que la serial.");
        }
        if (t.getEficiencia() != null) {
            System.out.println("- Eficiencia = " + Telemetria.formato(t.getEficiencia())
                    + ": porcentaje de la aceleracion ideal por hilo.");
        } else {
            System.out.println("- Eficiencia: no aplicable (sin speedup calculable).");
        }
    }

    private void imprimirReporteDiagnostico(ResultadoConsolidacion r) {
        ResultadoSerial s = r.getSerial();
        ResultadoParalelo p = r.getParalelo();

        System.out.println("=== Reporte de Consolidacion ===");
        System.out.println("Estado de la consolidacion: DISCREPANCIA_CRITICA (corrida no conformada)");
        System.out.println(ADVERTENCIA);
        System.out.println("Diferencias frente al umbral " + UMBRAL + ": minima " + r.getDiferenciaMin()
                + ", maxima " + r.getDiferenciaMax());

        System.out.println();
        System.out.println("Diagnostico - Serial vs. Paralelo lado a lado:");
        System.out.println("Tiempo serial (Ts): " + (s.tiempoTs() / 1_000_000.0) + " ms"
                + "      Tiempo paralelo (Tp): " + (p.tiempoTp() / 1_000_000.0) + " ms");
        System.out.println("Distancia minima serial: " + s.extremos().distMin()
                + " (par i=" + s.extremos().iMin() + ", j=" + s.extremos().jMin() + ")"
                + "      Distancia minima paralela: " + p.extremos().distMin()
                + " (par i=" + p.extremos().iMin() + ", j=" + p.extremos().jMin() + ")");
        System.out.println("Distancia maxima serial: " + s.extremos().distMax()
                + " (par i=" + s.extremos().iMax() + ", j=" + s.extremos().jMax() + ")"
                + "      Distancia maxima paralela: " + p.extremos().distMax()
                + " (par i=" + p.extremos().iMax() + ", j=" + p.extremos().jMax() + ")");

        System.out.println();
        System.out.println("La telemetria de rendimiento NO es valida: los resultados de las vias no coinciden.");
        System.out.println("La eficiencia y el speedup no se reportan (corrida no conformada).");
    }
}