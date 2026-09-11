import java.util.Scanner;

public class AsistenteEntrada {

    private static final String MENSAJE_FINAL = "Parametros listos para Generacion y Procesamiento";

    private final Scanner entrada;

    public AsistenteEntrada(Scanner entrada) {
        this.entrada = entrada;
    }

    public ParametrosEntrada ejecutar() {
        Integer N = pedirEntero(ParametroSolicitud.Clave.N, null);
        if (N == null) {
            return null;
        }
        Integer n = pedirEntero(ParametroSolicitud.Clave.n, null);
        if (n == null) {
            return null;
        }
        Integer A = pedirEntero(ParametroSolicitud.Clave.A, null);
        if (A == null) {
            return null;
        }
        Integer B = pedirEntero(ParametroSolicitud.Clave.B, A);
        if (B == null) {
            return null;
        }
        Integer H = pedirEntero(ParametroSolicitud.Clave.H, null);
        if (H == null) {
            return null;
        }
        ParametrosEntrada parametros = new ParametrosEntrada(N, n, A, B, H);
        System.out.println(MENSAJE_FINAL);
        return parametros;
    }

    private Integer pedirEntero(ParametroSolicitud.Clave clave, Integer referencia) {
        ParametroSolicitud solicitud = ParametroSolicitud.de(clave);
        while (true) {
            System.out.println(solicitud.getEtiqueta());
            String linea = leerLinea();
            if (linea == null) {
                return null;
            }
            Validador.Resultado resultado = Validador.validar(solicitud, linea, referencia);
            if (resultado.getEstado() == Validador.Estado.VALIDO) {
                return (int) resultado.getValor();
            }
            System.err.println(solicitud.mensajeError(resultado.getEstado()));
        }
    }

    private String leerLinea() {
        return entrada.hasNextLine() ? entrada.nextLine() : null;
    }
}