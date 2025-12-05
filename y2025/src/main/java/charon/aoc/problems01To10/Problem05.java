package charon.aoc.problems01To10;

import charon.aoc.FileUtils;
import charon.aoc.InclusiveRange;

import java.util.ArrayList;
import java.util.List;

public class Problem05 {
	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(5);

		final List<InclusiveRange> freshRanges = new ArrayList<>();
		final List<Long> ingredients = new ArrayList<>();

		boolean readingRanges = true;
		for (final String line : lines) {
			if (line.isBlank()) {
				readingRanges = false;
				continue;
			}

			if (readingRanges) {
				freshRanges.add(InclusiveRange.parse(line));
			} else {
				ingredients.add(Long.parseLong(line));
			}
		}

		// Part 1
		int rangeCount = 0;
		for (final Long ingredient : ingredients) {
			if (freshRanges.stream().anyMatch(range -> range.contains(ingredient))) {
				rangeCount++;
			}
		}

		System.out.println(rangeCount);

		// Part 2
		final List<InclusiveRange> allRanges = new ArrayList<>(freshRanges.size());
		allRanges.add(freshRanges.getFirst());
		for (int i = 1; i < freshRanges.size(); i++) {
			final InclusiveRange range = freshRanges.get(i);

			List<InclusiveRange> differences = new ArrayList<>();
			differences.add(range);
			for (final InclusiveRange otherRange : allRanges) {
				final List<InclusiveRange> newDifferences = new ArrayList<>();
				for (final InclusiveRange difference : differences) {
					newDifferences.addAll(difference.difference(otherRange));
				}
				differences = newDifferences;
			}
			allRanges.addAll(differences);
		}

		long ingredientsCount = 0;
		for (final InclusiveRange range : allRanges) {
			ingredientsCount += range.size();
		}

		System.out.println(ingredientsCount);
	}
}
