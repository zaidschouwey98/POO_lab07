package engine;

public record Coordinates<T extends Number>(T x, T y) {
	// TODO: remove generic
	public Coordinates<Integer> move(T dx, T dy) {
		int rx = this.x().intValue() + dx.intValue();
		int ry = this.y().intValue() + dy.intValue();

		return new Coordinates<>(rx, ry);
	}
}
