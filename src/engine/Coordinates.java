package engine;

public record Coordinates(int x, int y) {
	public Coordinates move(int dx, int dy) {
		int rx = this.x() + dx;
		int ry = this.y() + dy;

		return new Coordinates(rx, ry);
	}

	@Override
	public String toString() {
		return String.format("(%s, %s)", x(), y());
	}
}
