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

    public static boolean esEnteroValido(String raw) {
        if (raw == null) {
            return false;
        }
        String s = raw.trim();
        return !s.isEmpty() && s.matches("[+-]?\\d+");
    }

    public static Integer comprobarEnteroEnRango(String raw) {
        if (!esEnteroValido(raw)) {
            return null;
        }
        try {
            return Integer.valueOf(raw.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Resultado validar(ParametroSolicitud.Clave clave, String raw, Integer valorA) {
        switch (clave) {
            case N: {
                Integer valor = comprobarEnteroEnRango(raw);
                if (valor == null) {
                    return Resultado.falloParseo();
                }
                if (valor <= 1) {
                    return Resultado.falloRegla();
                }
                return Resultado.ok(valor);
            }
            case n: {
                Integer valor = comprobarEnteroEnRango(raw);
                if (valor == null) {
                    return Resultado.falloParseo();
                }
                if (valor < 1) {
                    return Resultado.falloRegla();
                }
                return Resultado.ok(valor);
            }
            case A: {
                Integer valor = comprobarEnteroEnRango(raw);
                if (valor == null) {
                    return Resultado.falloParseo();
                }
                return Resultado.ok(valor);
            }
            case B: {
                Integer valor = comprobarEnteroEnRango(raw);
                if (valor == null) {
                    return Resultado.falloParseo();
                }
                if (valorA != null && valor <= valorA) {
                    return Resultado.falloRegla();
                }
                return Resultado.ok(valor);
            }
            case H: {
                Integer valor = comprobarEnteroEnRango(raw);
                if (valor == null) {
                    return Resultado.falloParseo();
                }
                if (valor < 1) {
                    return Resultado.falloRegla();
                }
                return Resultado.ok(valor);
            }
            default:
                throw new IllegalArgumentException("Clave no contemplada: " + clave);
        }
    }
}