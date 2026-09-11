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
                    parametros.getN(), parametros.getn(), parametros.getA(), parametros.getB());
            new GeneradorDataset().generar(generacion);
            System.out.println("dataset.dat generado");

            System.out.println();

            ResultadoSerial resultado = new ProcesadorSerial().procesar(parametros.getN(), parametros.getn());
            resultado.imprimirReporte();

            System.out.println();

            ResultadoParalelo paralelo = new ProcesadorParalelo().procesar(
                    parametros.getN(), parametros.getn(), parametros.getH());
            paralelo.imprimirReporte();

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