package charon.aoc.problems11To20;

import charon.aoc.utils.FileUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Problem19 {
	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(19);

		final String[] availablePatterns = lines.get(0).split(", ");

		// Part 1

		final int totalPossible = lines.stream()
				.skip(2)
				.mapToInt(target -> isPossible(availablePatterns, target) ? 1 : 0)
				.sum();

		System.out.println(totalPossible);

		// Part 2

		final long totalArrangements = lines.stream()
				.skip(2)
				.mapToLong(target -> arrangementsCount(availablePatterns, target))
				.sum();

		System.out.println(totalArrangements);
	}

	private static final Map<String, Boolean> POSSIBLE_CACHE = new HashMap<>();

	private static boolean isPossible(final String[] availablePatterns, final String targetPattern) {
		if (targetPattern.isEmpty()) return true;
		if (POSSIBLE_CACHE.containsKey(targetPattern)) return POSSIBLE_CACHE.get(targetPattern);

		boolean possible = false;
		for (final String pattern : availablePatterns) {
			if (targetPattern.startsWith(pattern)) {
				possible = possible || isPossible(availablePatterns, targetPattern.substring(pattern.length()));
			}
		}
		POSSIBLE_CACHE.put(targetPattern, possible);
		return possible;
	}

	private static final Map<String, Long> COUNT_CACHE = new HashMap<>();

	private static long arrangementsCount(final String[] availablePatterns, final String targetPattern) {
		if (targetPattern.isEmpty()) return 1L;
		if (COUNT_CACHE.containsKey(targetPattern)) return COUNT_CACHE.get(targetPattern);

		long count = 0L;
		for (final String pattern : availablePatterns) {
			if (targetPattern.startsWith(pattern)) {
				count += arrangementsCount(availablePatterns, targetPattern.substring(pattern.length()));
			}
		}
		COUNT_CACHE.put(targetPattern, count);
		return count;
	}
}
