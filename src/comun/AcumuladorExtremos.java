public final class AcumuladorExtremos {

    private double distMin = Double.MAX_VALUE;
    private double distMax = -1.0;
    private int iMin = -1;
    private int jMin = -1;
    private int iMax = -1;
    private int jMax = -1;

    public void registrar(double dist, int i, int j) {
        if (dist < distMin) {
            distMin = dist;
            iMin = i;
            jMin = j;
        }
        if (dist > distMax) {
            distMax = dist;
            iMax = i;
            jMax = j;
        }
    }

    public Extremos toExtremos() {
        return new Extremos(distMin, iMin, jMin, distMax, iMax, jMax);
    }
}