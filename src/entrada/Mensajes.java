public final class Mensajes {

    public static final String ETIQUETA_N = "Ingrese cantidad de observaciones (N)";
    public static final String ETIQUETA_n = "Ingrese cantidad de dimensiones (n)";
    public static final String ETIQUETA_A = "Ingrese limite inferior aleatorio (A)";
    public static final String ETIQUETA_B = "Ingrese limite superior aleatorio (B)";
    public static final String ETIQUETA_H = "Ingrese cantidad de hilos concurrentes (H)";

    public static final String ERROR_N = "Error: Se necesitan al menos 2 puntos para comparar";
    public static final String ERROR_n = "Error: La dimension minima es 1";
    public static final String ERROR_B = "Error: El limite superior B debe ser estrictamente mayor que el inferior A";
    public static final String ERROR_H = "Error: Debe instanciarse al menos 1 hilo de trabajo";
    public static final String ERROR_PARSEO = "Error: Valor no valido para {parametro}";

    public static final String MENSAJE_FINAL = "Parametros listos para Generacion y Procesamiento";

    public static String mensajeError(ParametroSolicitud.Clave clave, Validador.Estado estado) {
        if (estado == Validador.Estado.FALLO_PARSEO) {
            return ERROR_PARSEO.replace("{parametro}", clave.name());
        }
        switch (clave) {
            case N:
                return ERROR_N;
            case n:
                return ERROR_n;
            case B:
                return ERROR_B;
            case H:
                return ERROR_H;
            default:
                return "";
        }
    }

    private Mensajes() {
    }
}