import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class GeneradorDataset {

    public static final String NOMBRE_ARCHIVO = "dataset.dat";

    public void generar(ParametrosGeneracion parametros) throws IOException {
        int N = parametros.getN();
        int n = parametros.getn();
        int A = parametros.getA();
        int B = parametros.getB();

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

    public static void main(String[] args) {
        if (args.length != 4) {
            System.err.println("Uso: GeneradorDataset <N> <n> <A> <B>");
            return;
        }
        try {
            int N = Integer.parseInt(args[0]);
            int n = Integer.parseInt(args[1]);
            int A = Integer.parseInt(args[2]);
            int B = Integer.parseInt(args[3]);
            ParametrosGeneracion parametros = new ParametrosGeneracion(N, n, A, B);
            new GeneradorDataset().generar(parametros);
        } catch (NumberFormatException e) {
            System.err.println("Error: los argumentos deben ser enteros validos");
            return;
        } catch (IOException e) {
            System.err.println("Error al generar dataset.dat: " + e.getMessage());
            return;
        }
        System.out.println("dataset.dat generado");
    }
}