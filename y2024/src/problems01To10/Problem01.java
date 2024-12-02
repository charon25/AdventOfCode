package problems01To10;

import utils.FileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Problem01 {
	public static void main(String[] args) {
		final List<Integer> firstCol = new ArrayList<>();
		final List<Integer> secondCol = new ArrayList<>();
		for (final String line : FileUtils.readProblemInput(1)) {
			final String[] cols = line.split("   ");
			firstCol.add(Integer.parseInt(cols[0]));
			secondCol.add(Integer.parseInt(cols[1]));
		}

		firstCol.sort(Integer::compareTo);
		secondCol.sort(Integer::compareTo);

		// Part 1

		int totalDifference = 0;
		for (int i = 0; i < firstCol.size(); i++) {
			totalDifference += Math.abs(firstCol.get(i) - secondCol.get(i));
		}

		System.out.println(totalDifference);

		// Part 2

		final Map<Integer, Integer> counts = new HashMap<>();
		secondCol.forEach(value -> counts.merge(value, 1, Integer::sum));

		int totalSimilarity = 0;
		for (final int value : firstCol) {
			totalSimilarity += value * counts.getOrDefault(value, 0);
		}

		System.out.println(totalSimilarity);
	}
}
