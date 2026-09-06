public final class ExtremosLocales {

    private final double distMin;
    private final double distMax;
    private final int iMin;
    private final int jMin;
    private final int iMax;
    private final int jMax;
    private final long paresEvaluados;

    public ExtremosLocales(double distMin, double distMax, int iMin, int jMin, int iMax, int jMax, long paresEvaluados) {
        this.distMin = distMin;
        this.distMax = distMax;
        this.iMin = iMin;
        this.jMin = jMin;
        this.iMax = iMax;
        this.jMax = jMax;
        this.paresEvaluados = paresEvaluados;
    }

    public double getDistMin() {
        return distMin;
    }

    public double getDistMax() {
        return distMax;
    }

    public int getIndiceIMin() {
        return iMin;
    }

    public int getIndiceJMin() {
        return jMin;
    }

    public int getIndiceIMax() {
        return iMax;
    }

    public int getIndiceJMax() {
        return jMax;
    }

    public long getParesEvaluados() {
        return paresEvaluados;
    }

    public boolean tienePares() {
        return paresEvaluados > 0;
    }
}