import java.io.IOException;
import java.io.RandomAccessFile;

public class GeneradorDataset {

    public static final String NOMBRE_ARCHIVO = "dataset.dat";

    public void generar(ParametrosGeneracion parametros) throws IOException {
        int N = parametros.N();
        int n = parametros.n();
        int A = parametros.A();
        int B = parametros.B();

        try (RandomAccessFile archivo = new RandomAccessFile(NOMBRE_ARCHIVO, "rw")) {
            archivo.setLength(0);
            for (int i = 0; i < N; i++) {
                for (int d = 0; d < n; d++) {
                    archivo.writeInt(GeneradorCoordenadas.generar(A, B));
                }
            }
        }
    }
}