public record ExtremosLocales(Extremos extremos, long paresEvaluados) {

    public boolean tienePares() {
        return paresEvaluados > 0;
    }
}