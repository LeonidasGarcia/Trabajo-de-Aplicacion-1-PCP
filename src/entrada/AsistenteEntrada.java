import java.util.Scanner;
import java.util.function.IntPredicate;

public class AsistenteEntrada {

    private final Scanner entrada;

    public AsistenteEntrada(Scanner entrada) {
        this.entrada = entrada;
    }

    public ParametrosEntrada ejecutar() {
        Integer N = pedirEntero("N", "Ingrese cantidad de observaciones (N)",
                v -> v > 1, "Error: Se necesitan al menos 2 puntos para comparar");
        if (N == null) {
            return null;
        }
        Integer n = pedirEntero("n", "Ingrese cantidad de dimensiones (n)",
                v -> v >= 1, "Error: La dimension minima es 1");
        if (n == null) {
            return null;
        }
        Integer A = pedirEntero("A", "Ingrese limite inferior aleatorio (A)",
                v -> true, null);
        if (A == null) {
            return null;
        }
        Integer B = pedirEntero("B", "Ingrese limite superior aleatorio (B)",
                v -> v > A, "Error: El limite superior B debe ser estrictamente mayor que el inferior A");
        if (B == null) {
            return null;
        }
        Integer H = pedirEntero("H", "Ingrese cantidad de hilos concurrentes (H)",
                v -> v >= 1, "Error: Debe instanciarse al menos 1 hilo de trabajo");
        if (H == null) {
            return null;
        }
        System.out.println("Parametros listos para Generacion y Procesamiento");
        return new ParametrosEntrada(N, n, A, B, H);
    }

    private Integer pedirEntero(String clave, String etiqueta, IntPredicate regla, String mensajeRegla) {
        while (true) {
            System.out.println(etiqueta);
            if (!entrada.hasNextLine()) {
                return null;
            }
            String linea = entrada.nextLine();
            int valor;
            try {
                valor = Integer.parseInt(linea.trim());
            } catch (NumberFormatException e) {
                System.err.println("Error: Valor no valido para " + clave);
                continue;
            }
            if (regla.test(valor)) {
                return valor;
            }
            System.err.println(mensajeRegla == null ? "" : mensajeRegla);
        }
    }
}