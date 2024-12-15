package utils;

import java.util.Objects;

public class LongPoint {
	private final long m_x;
	private final long m_y;

	public LongPoint(final long x, final long y) {
		m_x = x;
		m_y = y;
	}

	public long getX() {
		return m_x;
	}

	public long getY() {
		return m_y;
	}

	public LongPoint copy() {
		return new LongPoint(m_x, m_y);
	}

	public boolean isInBound(final long minX, final long minY, final long maxX, final long maxY) {
		return minX <= m_x && minY <= m_y && m_x < maxX && m_y < maxY;
	}

	public LongPoint difference(final LongPoint other) {
		return new LongPoint(m_x - other.m_x, m_y - other.m_y);
	}

	public LongPoint add(final LongPoint other) {
		return new LongPoint(m_x + other.m_x, m_y + other.m_y);
	}

	public LongPoint times(final long scale) {
		return new LongPoint(m_x * scale, m_y * scale);
	}

	public LongPoint symmetry(final LongPoint other) {
		return other.add(other.difference(this));
	}

	public LongPoint[] getAdjacent4() {
		return new LongPoint[] {
				new LongPoint(m_x, m_y - 1),
				new LongPoint(m_x + 1, m_y),
				new LongPoint(m_x, m_y + 1),
				new LongPoint(m_x - 1, m_y)
		};
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final LongPoint longPoint = (LongPoint) o;
		return m_x == longPoint.m_x && m_y == longPoint.m_y;
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
