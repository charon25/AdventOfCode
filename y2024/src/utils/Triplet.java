package utils;

import java.util.Objects;

public class Triplet<F, S, T> {
	private final F m_first;
	private final S m_second;
	private final T m_third;

	public Triplet(final F first, final S second, final T third) {
		m_first = first;
		m_second = second;
		m_third = third;
	}

	public F getFirst() {
		return m_first;
	}

	public S getSecond() {
		return m_second;
	}

	public T getThird() {
		return m_third;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final Triplet<?, ?, ?> triplet = (Triplet<?, ?, ?>) o;
		return Objects.equals(m_first, triplet.m_first) && Objects.equals(m_second, triplet.m_second) && Objects.equals(m_third, triplet.m_third);
	}

	@Override
	public int hashCode() {
		return Objects.hash(m_first, m_second, m_third);
	}

	@Override
	public String toString() {
		return "(" + m_first + ", " + m_second + ", " + m_third + ')';
	}
}
