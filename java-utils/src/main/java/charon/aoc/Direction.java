package charon.aoc;

public enum Direction {
	UP(Point.UP),
	RIGHT(Point.RIGHT),
	DOWN(Point.DOWN),
	LEFT(Point.LEFT),
	;

	private final Point m_vector;

	Direction(final Point vector) {
		m_vector = vector;
	}

	public Point getVector() {
		return m_vector;
	}
}
