import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        AsistenteEntrada asistente = new AsistenteEntrada(entrada);
        ParametrosEntrada parametros = asistente.ejecutar();
        
        if (parametros == null) {
            return;
        }

        System.out.println();

        try {
            ParametrosGeneracion generacion = new ParametrosGeneracion(
                    parametros.N(), parametros.n(), parametros.A(), parametros.B());

            new GeneradorDataset().generar(generacion);

            System.out.println("dataset.dat generado");
            System.out.println();

            // Invocacion del procesamiento serial
            ResultadoSerial serial = new ProcesadorSerial().procesar(parametros.N(), parametros.n());
            serial.imprimirReporte();

            System.out.println();

            // Invocacion del procesamiento paralelo
            ResultadoParalelo paralelo = new ProcesadorParalelo().procesar(
                    parametros.N(), parametros.n(), parametros.H());
            paralelo.imprimirReporte();

            System.out.println();

            ConsolidadorMetricas consolidador = new ConsolidadorMetricas();
            ResultadoConsolidacion consolidacion = consolidador.consolidar(serial, paralelo, parametros.H());
            consolidador.imprimirReporte(consolidacion, parametros.H());

            System.out.println();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}