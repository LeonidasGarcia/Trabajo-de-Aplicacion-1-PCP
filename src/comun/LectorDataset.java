import java.io.IOException;
import java.io.RandomAccessFile;

public class LectorDataset {

    private final RandomAccessFile archivo;

    public LectorDataset() throws IOException {
        archivo = new RandomAccessFile(GeneradorDataset.NOMBRE_ARCHIVO, "r");
    }

    public int[] leerPunto(int indice, int dimensiones, int ancho, int[] destino) throws IOException {
        archivo.seek((long) indice * ancho);
        for (int d = 0; d < dimensiones; d++) {
            destino[d] = archivo.readInt();
        }
        return destino;
    }

    public void cerrar() throws IOException {
        archivo.close();
    }
}