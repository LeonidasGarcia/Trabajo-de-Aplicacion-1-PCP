import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class GeneradorDataset {

    public static final String NOMBRE_ARCHIVO = "dataset.dat";

    public void generar(ParametrosGeneracion parametros) throws IOException {
        int N = parametros.N();
        int n = parametros.n();
        int A = parametros.A();
        int B = parametros.B();

        try (RandomAccessFile archivo = new RandomAccessFile(NOMBRE_ARCHIVO, "rw")) {
            archivo.setLength(0);
            FileChannel canal = archivo.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1 << 16);
            for (int i = 0; i < N; i++) {
                for (int d = 0; d < n; d++) {
                    buffer.putInt(GeneradorCoordenadas.generar(A, B));
                    if (!buffer.hasRemaining()) {
                        buffer.flip();
                        canal.write(buffer);
                        buffer.clear();
                    }
                }
            }
            buffer.flip();
            while (buffer.hasRemaining()) {
                canal.write(buffer);
            }
        }
    }
}