import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));

        Scanner entrada = new Scanner(System.in, StandardCharsets.UTF_8);
        AsistenteEntrada asistente = new AsistenteEntrada(entrada);
        ParametrosEntrada parametros = asistente.ejecutar();
        if (parametros == null) {
            return;
        }

        System.out.println();

        try {
            ParametrosGeneracion generacion = new ParametrosGeneracion(
                    parametros.getN(), parametros.getn(), parametros.getA(), parametros.getB());
            new GeneradorDataset().generar(generacion);
            System.out.println("dataset.dat generado");

            System.out.println();

            ResultadoSerial resultado = new ProcesadorSerial().procesar(parametros.getN(), parametros.getn());
            System.out.println("=== Reporte Serial ===");
            System.out.println("Distancia minima: " + resultado.getDistMin()
                    + " (par i=" + resultado.getIndiceIMin() + ", j=" + resultado.getIndiceJMin() + ")");
            System.out.println("Distancia maxima: " + resultado.getDistMax()
                    + " (par i=" + resultado.getIndiceIMax() + ", j=" + resultado.getIndiceJMax() + ")");
            System.out.println("Tiempo serial (Ts): " + resultado.getTiempoTs()
                    + " ns (" + (resultado.getTiempoTs() / 1_000_000_000.0) + " s)");

            System.out.println();

            ResultadoParalelo paralelo = new ProcesadorParalelo().procesar(
                    parametros.getN(), parametros.getn(), parametros.getH());
            System.out.println("=== Reporte Paralelo ===");
            System.out.println("Distancia minima: " + paralelo.getDistMin()
                    + " (par i=" + paralelo.getIndiceIMin() + ", j=" + paralelo.getIndiceJMin() + ")");
            System.out.println("Distancia maxima: " + paralelo.getDistMax()
                    + " (par i=" + paralelo.getIndiceIMax() + ", j=" + paralelo.getIndiceJMax() + ")");
            System.out.println("Hilos activos (con pares evaluados): " + paralelo.getHilosActivos());
            System.out.println("Tiempo paralelo (Tp): " + paralelo.getTiempoTp()
                    + " ns (" + (paralelo.getTiempoTp() / 1_000_000_000.0) + " s)");

            System.out.println();

            ConsolidadorMetricas consolidador = new ConsolidadorMetricas();
            ResultadoConsolidacion consolidacion = consolidador.consolidar(resultado, paralelo, parametros.getH());
            consolidador.imprimirReporte(consolidacion, parametros.getH());

            System.out.println();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}