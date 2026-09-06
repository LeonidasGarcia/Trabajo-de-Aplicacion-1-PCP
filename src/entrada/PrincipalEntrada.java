import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class PrincipalEntrada {

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));
        Scanner entrada = new Scanner(System.in, StandardCharsets.UTF_8);
        AsistenteEntrada asistente = new AsistenteEntrada(entrada);
        asistente.ejecutar();
    }
}