import java.util.List;

public class ParametroSolicitud {

    public enum Clave {
        N,
        n,
        A,
        B,
        H
    }

    private static final String ERROR_PARSEO = "Error: Valor no valido para {parametro}";
    private static final int SIN_MINIMO = Integer.MIN_VALUE;

    private static final List<ParametroSolicitud> CATALOGO = List.of(
            new ParametroSolicitud(Clave.N, "Ingrese cantidad de observaciones (N)",
                    "Error: Se necesitan al menos 2 puntos para comparar", null, 2),
            new ParametroSolicitud(Clave.n, "Ingrese cantidad de dimensiones (n)",
                    "Error: La dimension minima es 1", null, 1),
            new ParametroSolicitud(Clave.A, "Ingrese limite inferior aleatorio (A)",
                    null, null, SIN_MINIMO),
            new ParametroSolicitud(Clave.B, "Ingrese limite superior aleatorio (B)",
                    "Error: El limite superior B debe ser estrictamente mayor que el inferior A", Clave.A, SIN_MINIMO),
            new ParametroSolicitud(Clave.H, "Ingrese cantidad de hilos concurrentes (H)",
                    "Error: Debe instanciarse al menos 1 hilo de trabajo", null, 1));

    private final Clave clave;
    private final String etiqueta;
    private final String mensajeRegla;
    private final Clave dependeDe;
    private final int minimo;

    public ParametroSolicitud(Clave clave, String etiqueta, String mensajeRegla, Clave dependeDe, int minimo) {
        this.clave = clave;
        this.etiqueta = etiqueta;
        this.mensajeRegla = mensajeRegla;
        this.dependeDe = dependeDe;
        this.minimo = minimo;
    }

    public static ParametroSolicitud de(Clave clave) {
        for (ParametroSolicitud solicitud : CATALOGO) {
            if (solicitud.clave == clave) {
                return solicitud;
            }
        }
        throw new IllegalArgumentException("Solicitud no registrada: " + clave);
    }

    public String mensajeError(Validador.Estado estado) {
        if (estado == Validador.Estado.FALLO_PARSEO) {
            return ERROR_PARSEO.replace("{parametro}", clave.name());
        }
        return mensajeRegla == null ? "" : mensajeRegla;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public Clave getDependeDe() {
        return dependeDe;
    }

    public int getMinimo() {
        return minimo;
    }
}