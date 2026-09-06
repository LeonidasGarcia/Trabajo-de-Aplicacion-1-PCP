public class ConsolidadorMetricas {

    private static final double UMBRAL = 1e-9;
    private static final String ADVERTENCIA = "!ADVERTENCIA: Los resultados no coinciden! Posible condicion de carrera (Race Condition) detectada.";

    public ResultadoConsolidacion consolidar(ResultadoSerial serial, ResultadoParalelo paralelo, int H) {
        double diferenciaMin = Math.abs(serial.getDistMin() - paralelo.getDistMin());
        double diferenciaMax = Math.abs(serial.getDistMax() - paralelo.getDistMax());
        boolean equivalente = diferenciaMin < UMBRAL && diferenciaMax < UMBRAL;

        String estado = equivalente ? ResultadoConsolidacion.CONSISTENTE
                : ResultadoConsolidacion.DISCREPANCIA_CRITICA;
        Telemetria telemetria = equivalente ? new Telemetria(serial.getTiempoTs(), paralelo.getTiempoTp(), H) : null;

        return new ResultadoConsolidacion(estado, serial, paralelo,
                diferenciaMin, diferenciaMax, UMBRAL, equivalente, telemetria);
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
        System.out.println("Serial  | Ts = " + s.getTiempoTs() + " ns (" + (s.getTiempoTs() / 1_000_000_000.0) + " s)"
                + " | min " + s.getDistMin() + " (par i=" + s.getIndiceIMin() + ", j=" + s.getIndiceJMin() + ")"
                + " | max " + s.getDistMax() + " (par i=" + s.getIndiceIMax() + ", j=" + s.getIndiceJMax() + ")");
        System.out.println("Paralelo | Tp = " + p.getTiempoTp() + " ns (" + (p.getTiempoTp() / 1_000_000_000.0) + " s)"
                + " | min " + p.getDistMin() + " (par i=" + p.getIndiceIMin() + ", j=" + p.getIndiceJMin() + ")"
                + " | max " + p.getDistMax() + " (par i=" + p.getIndiceIMax() + ", j=" + p.getIndiceJMax() + ")");

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
        System.out.println("Tiempo serial (Ts): " + s.getTiempoTs() + " ns (" + (s.getTiempoTs() / 1_000_000_000.0) + " s)"
                + "      Tiempo paralelo (Tp): " + p.getTiempoTp() + " ns (" + (p.getTiempoTp() / 1_000_000_000.0) + " s)");
        System.out.println("Distancia minima serial: " + s.getDistMin()
                + " (par i=" + s.getIndiceIMin() + ", j=" + s.getIndiceJMin() + ")"
                + "      Distancia minima paralela: " + p.getDistMin()
                + " (par i=" + p.getIndiceIMin() + ", j=" + p.getIndiceJMin() + ")");
        System.out.println("Distancia maxima serial: " + s.getDistMax()
                + " (par i=" + s.getIndiceIMax() + ", j=" + s.getIndiceJMax() + ")"
                + "      Distancia maxima paralela: " + p.getDistMax()
                + " (par i=" + p.getIndiceIMax() + ", j=" + p.getIndiceJMax() + ")");

        System.out.println();
        System.out.println("La telemetria de rendimiento NO es valida: los resultados de las vias no coinciden.");
        System.out.println("La eficiencia y el speedup no se reportan (corrida no conformada).");
    }
}