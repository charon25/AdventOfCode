package charon.aoc.problems11To20;

import charon.aoc.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Problem13 {
	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(13);

		final List<List<String>> patterns = getPatterns(lines);

		// Part 1
		int total = 0;
		for (final List<String> pattern : patterns) {
			total += getVerticalReflectionIndex(pattern)
					.or(() -> getHorizontalReflectionIndex(pattern).map(n -> 100 * n))
					.orElseThrow();
		}

		System.out.println(total);

		// Part 2
		int total2 = 0;
		for (final List<String> pattern : patterns) {
			total2 += getVerticalReflectionWithOneError(pattern)
					.or(() -> getHorizontalReflectionWithOneError(pattern).map(n -> 100 * n))
					.orElseThrow();
		}

		System.out.println(total2);
	}

	private static List<List<String>> getPatterns(final List<String> lines) {
		final List<List<String>> patterns = new ArrayList<>(100);
		List<String> pattern = new ArrayList<>();
		for (final String line : lines) {
			if (line.isBlank()) {
				patterns.add(pattern);
				pattern = new ArrayList<>();
			} else {
				pattern.add(line);
			}
		}

		if (!pattern.isEmpty()) patterns.add(pattern);
		return patterns;
	}

	private static Optional<Integer> getVerticalReflectionIndex(final List<String> pattern) {
		final int width = pattern.get(0).length();
		for (int x = 1; x < width; x++) {
			if (isVerticalReflection(pattern, width, x)) return Optional.of(x);
		}

		return Optional.empty();
	}

	private static Optional<Integer> getVerticalReflectionWithOneError(final List<String> pattern) {
		final int width = pattern.get(0).length();
		for (int x = 1; x < width; x++) {
			if (getVerticalReflectionErrors(pattern, width, x) == 1) return Optional.of(x);
		}

		return Optional.empty();
	}

	private static boolean isVerticalReflection(final List<String> pattern, final int width, final int x) {
		return getVerticalReflectionErrors(pattern, width, x) == 0;
	}

	private static int getVerticalReflectionErrors(final List<String> pattern, final int width, final int x) {
		int errors = 0;
		for (int d = 1; d < width; d++) {
			if (x - d < 0 || x + d - 1 >= width) continue;
			for (final String row : pattern) {
				if (row.charAt(x - d) != row.charAt(x + d - 1)) errors++;
			}
		}

		return errors;
	}

	private static Optional<Integer> getHorizontalReflectionIndex(final List<String> pattern) {
		final int height = pattern.size();
		for (int y = 1; y < height; y++) {
			if (isHorizontalReflection(pattern, height, y)) return Optional.of(y);
		}

		return Optional.empty();
	}

	private static Optional<Integer> getHorizontalReflectionWithOneError(final List<String> pattern) {
		final int height = pattern.size();
		for (int y = 1; y < height; y++) {
			if (getHorizontalReflectionErrors(pattern, height, y) == 1) return Optional.of(y);
		}

		return Optional.empty();
	}

	private static boolean isHorizontalReflection(final List<String> pattern, final int height, final int y) {
		return getHorizontalReflectionErrors(pattern, height, y) == 0;
	}

	private static int getHorizontalReflectionErrors(final List<String> pattern, final int height, final int y) {
		int errors = 0;
		final int width = pattern.get(0).length();
		for (int d = 1; d < height; d++) {
			if (y - d < 0 || y + d - 1 >= height) continue;
			for (int x = 0; x < width; x++) {
				if (pattern.get(y - d).charAt(x) != pattern.get(y + d - 1).charAt(x)) errors++;
			}
		}

		return errors;
	}
}
