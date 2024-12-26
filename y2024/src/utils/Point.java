package utils;

import java.util.Comparator;
import java.util.Objects;

public class Point {

	public static final Comparator<Point> X_COMPARATOR = Comparator.comparingInt(Point::getX);
	public static final Comparator<Point> Y_COMPARATOR = Comparator.comparingInt(Point::getY);

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

	public Point times(final int scale) {
		return new Point(m_x * scale, m_y * scale);
	}

	public Point symmetry(final Point other) {
		return other.add(other.difference(this));
	}

	public Point[] getAdjacent4() {
		return new Point[] {
				new Point(m_x, m_y - 1),
				new Point(m_x + 1, m_y),
				new Point(m_x, m_y + 1),
				new Point(m_x - 1, m_y)
		};
	}

	public Point[] getAllPointsAtDistance(final int distance) {
		if (distance == 0) return new Point[] {this};
		if (distance == 1) return getAdjacent4();

		final Point[] points = new Point[4 * distance];
		Point point = new Point(m_x, m_y - distance);
		points[0] = point;
		int index = 1;
		for (int i = 0; i < distance; i++) {
			point = point.add(new Point(1, 1));
			points[index] = point;
			index++;
		}

		for (int i = 0; i < distance; i++) {
			point = point.add(new Point(-1, 1));
			points[index] = point;
			index++;
		}

		for (int i = 0; i < distance; i++) {
			point = point.add(new Point(-1, -1));
			points[index] = point;
			index++;
		}

		for (int i = 0; i < distance - 1; i++) {
			point = point.add(new Point(1, -1));
			points[index] = point;
			index++;
		}

		return points;
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
