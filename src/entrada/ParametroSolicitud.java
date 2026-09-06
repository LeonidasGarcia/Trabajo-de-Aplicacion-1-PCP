import java.util.Collections;
import java.util.List;

public class ParametroSolicitud {

    public enum Tipo {
        ENTERO,
        REAL
    }

    public enum Clave {
        N,
        n,
        A,
        B,
        H
    }

    private static final List<ParametroSolicitud> CATALOGO = List.of(
            new ParametroSolicitud(Clave.N, Tipo.ENTERO, Mensajes.ETIQUETA_N, Mensajes.ERROR_N, null),
            new ParametroSolicitud(Clave.n, Tipo.ENTERO, Mensajes.ETIQUETA_n, Mensajes.ERROR_n, null),
            new ParametroSolicitud(Clave.A, Tipo.ENTERO, Mensajes.ETIQUETA_A, null, null),
            new ParametroSolicitud(Clave.B, Tipo.ENTERO, Mensajes.ETIQUETA_B, Mensajes.ERROR_B, Clave.A),
            new ParametroSolicitud(Clave.H, Tipo.ENTERO, Mensajes.ETIQUETA_H, Mensajes.ERROR_H, null));

    private final Clave clave;
    private final Tipo tipo;
    private final String etiqueta;
    private final String mensajeErrorRegla;
    private final Clave dependeDe;

    public ParametroSolicitud(Clave clave, Tipo tipo, String etiqueta, String mensajeErrorRegla, Clave dependeDe) {
        this.clave = clave;
        this.tipo = tipo;
        this.etiqueta = etiqueta;
        this.mensajeErrorRegla = mensajeErrorRegla;
        this.dependeDe = dependeDe;
    }

    public static ParametroSolicitud de(Clave clave) {
        for (ParametroSolicitud solicitud : CATALOGO) {
            if (solicitud.clave == clave) {
                return solicitud;
            }
        }
        throw new IllegalArgumentException("Solicitud no registrada: " + clave);
    }

    public static List<ParametroSolicitud> catalogoOrdenado() {
        return Collections.unmodifiableList(CATALOGO);
    }

    public Clave getClave() {
        return clave;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public String getMensajeErrorRegla() {
        return mensajeErrorRegla;
    }

    public Clave getDependeDe() {
        return dependeDe;
    }
}