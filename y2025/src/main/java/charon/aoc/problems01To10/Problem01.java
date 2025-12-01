package charon.aoc.problems01To10;

import charon.aoc.FileUtils;

import java.util.List;

import static charon.aoc.MathUtils.mod;

public class Problem01 {

	private static final int STARTING_DIAL_POS = 50;

	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(1);

		final List<Movement> movements = lines.stream()
				.map(line -> switch (line) {
					case final String s when s.startsWith("L") -> new Movement(-1, Integer.parseInt(line.substring(1)));
					case final String s when s.startsWith("R") -> new Movement(+1, Integer.parseInt(line.substring(1)));
					default -> throw new IllegalArgumentException("Invalid line: " + line);
				})
				.toList();

		// Part 1
		int dial = STARTING_DIAL_POS;
		int zeroCount = 0;
		for (final Movement movement : movements) {
			final int delta = movement.direction * movement.amplitude;
			dial = mod(dial + delta, 100);
			if (dial == 0) zeroCount++;
		}

		System.out.println(zeroCount);

		// Part 2
		dial = STARTING_DIAL_POS;
		zeroCount = 0;
		for (final Movement movement : movements) {
			final int delta = movement.direction * movement.amplitude;
			final boolean wasZero = dial == 0;
			dial += delta;

			if (dial > 0) {
				zeroCount += dial / 100;
			} else if (dial <= 0) {
				zeroCount += (wasZero ? 0 : 1) - dial / 100;
			}

			dial = mod(dial, 100);
		}

		System.out.println(zeroCount);
	}

	private record Movement(int direction, int amplitude) {}
}
