package charon.aoc.problems21To25;

import charon.aoc.FileUtils;
import charon.aoc.LongPoint3;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Problem24 {

	public static final long MIN_BOUND = 200000000000000L;
	public static final long MAX_BOUND = 400000000000000L;

	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(24);

		final Pattern pattern = Pattern.compile("(-?\\d+), (-?\\d+), (-?\\d+) @ (-?\\d+), (-?\\d+), (-?\\d+)");

		final List<Hailstone> hailstones = new ArrayList<>();

		for (final String line : lines) {
			final Matcher matcher = pattern.matcher(line);
			if (!matcher.find()) throw new IllegalArgumentException("Could not parse line");
			final LongPoint3 position = new LongPoint3(
					Long.parseLong(matcher.group(1)),
					Long.parseLong(matcher.group(2)),
					Long.parseLong(matcher.group(3))
			);
			final LongPoint3 velocity = new LongPoint3(
					Long.parseLong(matcher.group(4)),
					Long.parseLong(matcher.group(5)),
					Long.parseLong(matcher.group(6))
			);

			hailstones.add(new Hailstone(position, velocity));
		}

		// Part 1
		int totalIntersections = 0;
		for (int i = 0; i < hailstones.size(); i++) {
			final Hailstone h1 = hailstones.get(i);
			for (int j = 0; j < i; j++) {
				final Hailstone h2 = hailstones.get(j);
				final double intersectionX = (h2.m_2dYIntercept - h1.m_2dYIntercept) / (h1.m_2dSlope - h2.m_2dSlope);
				final double intersectionY = h1.m_2dSlope * intersectionX + h1.m_2dYIntercept;

				if (intersectionX < MIN_BOUND || intersectionX > MAX_BOUND) continue;
				if (intersectionY < MIN_BOUND || intersectionY > MAX_BOUND) continue;
				if (h1.isInPast(intersectionX) || h2.isInPast(intersectionX)) continue;
				totalIntersections++;
			}
		}

		System.out.println(totalIntersections);
	}

	private static class Hailstone {
		private final LongPoint3 m_initialPosition;
		private final LongPoint3 m_velocity;
		private final double m_2dSlope;
		private final double m_2dYIntercept;

		private Hailstone(final LongPoint3 initialPosition, final LongPoint3 velocity) {
			m_initialPosition = initialPosition;
			m_velocity = velocity;

			m_2dSlope = (double) velocity.y() / velocity.x();
			m_2dYIntercept = initialPosition.y() - initialPosition.x() * m_2dSlope;
		}

		private boolean isInPast(final double x) {
			if (x > m_initialPosition.x()) {
				return m_velocity.x() < 0;
			} else {
				return m_velocity.x() > 0;
			}
		}


		@Override
		public String toString() {
			return "Hailstone{" +
					"m_position=" + m_initialPosition +
					", m_velocity=" + m_velocity +
					'}';
		}
	}
}
