package charon.aoc;

import java.util.Map;

public enum Direction {
	UP(Point.UP),
	RIGHT(Point.RIGHT),
	DOWN(Point.DOWN),
	LEFT(Point.LEFT),
	;

	public static final Map<Direction, Direction> CLOCKWISE = Map.of(
			UP, RIGHT,
			RIGHT, DOWN,
			DOWN, LEFT,
			LEFT, UP
	);

	public static final Map<Direction, Direction> COUNTER_CLOCKWISE = Map.of(
			UP, LEFT,
			LEFT, DOWN,
			DOWN, RIGHT,
			RIGHT, UP
	);

	private final Point m_vector;

	Direction(final Point vector) {
		m_vector = vector;
	}

	public Point getVector() {
		return m_vector;
	}
}
