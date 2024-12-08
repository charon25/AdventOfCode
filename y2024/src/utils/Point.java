package utils;

import java.util.Objects;

public class Point {
	private final int m_x;
	private final int m_y;

	public Point(final int x, final int y) {
		m_x = x;
		m_y = y;
	}

	public int getX() {
		return m_x;
	}

	public int getY() {
		return m_y;
	}

	public Point copy() {
		return new Point(m_x, m_y);
	}

	public boolean isInBound(final int minX, final int minY, final int maxX, final int maxY) {
		return minX <= m_x && minY <= m_y && m_x < maxX && m_y < maxY;
	}

	public Point difference(final Point other) {
		return new Point(m_x - other.m_x, m_y - other.m_y);
	}

	public Point add(final Point other) {
		return new Point(m_x + other.m_x, m_y + other.m_y);
	}

	public Point symmetry(final Point other) {
		return other.add(other.difference(this));
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final Point point = (Point) o;
		return m_x == point.m_x && m_y == point.m_y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(m_x, m_y);
	}

	@Override
	public String toString() {
		return "(" + m_x + ", " + m_y + ')';
	}
}
