package charon.aoc;

import java.util.Objects;

public class Pair<F, S> {
	private final F m_first;
	private final S m_second;

	public Pair(final F first, final S second) {
		m_first = first;
		m_second = second;
	}

	public F getFirst() {
		return m_first;
	}

	public S getSecond() {
		return m_second;
	}

	public Pair<F, S> copy() {
		return new Pair<>(m_first, m_second);
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final Pair<?, ?> pair = (Pair<?, ?>) o;
		return Objects.equals(m_first, pair.m_first) && Objects.equals(m_second, pair.m_second);
	}

	@Override
	public int hashCode() {
		return Objects.hash(m_first, m_second);
	}

	@Override
	public String toString() {
		return "(" + m_first + ", " + m_second + ')';
	}
}
