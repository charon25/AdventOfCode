package charon.aoc.problems01To10;

import charon.aoc.utils.FileUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Problem02 {
	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(2);

		// Part 1

		int total = 0;
		for (final String line : lines) {
			final List<Integer> levels = Arrays.stream(line.split(" ")).map(Integer::parseInt).toList();
			if (areLevelsSafe(levels)) {
				total++;
			}
		}

		System.out.println(total);

		// Part 2
		int total2 = 0;
		for (final String line : lines) {
			final List<Integer> levels = Arrays.stream(line.split(" ")).map(Integer::parseInt).toList();
			if (areLevelsSafeWithOneRemoval(levels)) {
				total2++;
			}
		}

		System.out.println(total2);
	}

	private static boolean areLevelsSafe(final List<Integer> levels) {
		Boolean increasing = null;
		for (int i = 0; i < levels.size() - 1; i++) {
			// Non stricly monotonic
			if (levels.get(i + 1) == levels.get(i)) return false;

			if (increasing == null) {
				increasing = levels.get(i + 1) > levels.get(i);
			}

			// Non monotonic
			if (increasing != levels.get(i + 1) > levels.get(i)) return false;

			// Difference too large
			if (Math.abs(levels.get(i + 1) - levels.get(i)) > 3) return false;
		}

		return true;
	}

	private static boolean areLevelsSafeWithOneRemoval(final List<Integer> levels) {
		for (int i = 0; i < levels.size(); i++) {
			final ArrayList<Integer> levelsCopy = new ArrayList<>(levels);
			levelsCopy.remove(i);
			if (areLevelsSafe(levelsCopy)) return true;
		}

		return false;
	}
}
