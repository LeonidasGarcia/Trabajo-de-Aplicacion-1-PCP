public class Validador {

    public enum Estado {
        VALIDO,
        FALLO_PARSEO,
        FALLO_REGLA
    }

    public static final class Resultado {
        private final Estado estado;
        private final double valor;

        private Resultado(Estado estado, double valor) {
            this.estado = estado;
            this.valor = valor;
        }

        public static Resultado ok(double valor) {
            return new Resultado(Estado.VALIDO, valor);
        }

        public static Resultado falloParseo() {
            return new Resultado(Estado.FALLO_PARSEO, 0.0);
        }

        public static Resultado falloRegla() {
            return new Resultado(Estado.FALLO_REGLA, 0.0);
        }

        public Estado getEstado() {
            return estado;
        }

        public double getValor() {
            return valor;
        }
    }

    public static Integer comprobarEnteroEnRango(String raw) {
        if (raw == null) {
            return null;
        }
        try {
            return Integer.parseInt(raw.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Resultado validar(ParametroSolicitud solicitud, String raw, Integer valorA) {
        Integer valor = comprobarEnteroEnRango(raw);
        if (valor == null) {
            return Resultado.falloParseo();
        }
        if (solicitud.getDependeDe() != null) {
            if (valorA != null && valor <= valorA) {
                return Resultado.falloRegla();
            }
            return Resultado.ok(valor);
        }
        if (valor < solicitud.getMinimo()) {
            return Resultado.falloRegla();
        }
        return Resultado.ok(valor);
    }
}