package charon.aoc.problems01To10;

import charon.aoc.utils.FileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Problem05 {
	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(5);
		final List<int[]> rules = new ArrayList<>();
		final List<Map<Integer, Integer>> manuals = new ArrayList<>();

		for (final String line : lines) {
			if (line.isBlank()) continue;
			if (line.contains("|")) {
				final String[] numbers = line.split("\\|");
				rules.add(new int[] {Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1])});
			} else {
				final Map<Integer, Integer> manual = new HashMap<>();
				final String[] numbers = line.split(",");
				for (int i = 0; i < numbers.length; i++) {
					final int n = Integer.parseInt(numbers[i]);
					manual.put(n, i);
				}
				manuals.add(manual);
			}
		}

		// Part 1

		int total1 = 0;
		for (final Map<Integer, Integer> manual : manuals) {
			if (isCorrect(manual, rules)) {
				total1 += getManualCenter(manual);
			}
		}

		System.out.println(total1);

		// Part 2

		int total2 = 0;
		for (final Map<Integer, Integer> manual : manuals) {
			if (!isCorrect(manual, rules)) {
				orderManual(manual, rules);
				total2 += getManualCenter(manual);
			}
		}

		System.out.println(total2);
	}

	private static boolean isCorrect(final Map<Integer, Integer> manual, final List<int[]> rules) {
		for (final int[] rule : rules) {
			if (!manual.containsKey(rule[0]) || !manual.containsKey(rule[1])) continue;
			if (manual.get(rule[0]) > manual.get(rule[1])) {
				return false;
			}
		}

		return true;
	}

	private static int getManualCenter(final Map<Integer, Integer> manual) {
		final int manualCenter = manual.size() / 2;
		for (final Map.Entry<Integer, Integer> entry : manual.entrySet()) {
			if (entry.getValue() == manualCenter) return entry.getKey();
		}

		throw new RuntimeException("Unable to find manual center: " + manual);
	}

	private static void orderManual(final Map<Integer, Integer> manual, final List<int[]> rules) {
		int i = 0;
		while (i < rules.size()) {
			final int[] rule = rules.get(i);
			final int n1 = rule[0];
			final int n2 = rule[1];
			if (manual.containsKey(n1) && manual.containsKey(n2)) {
				if (manual.get(n1) > manual.get(n2)) {
					final int temp = manual.get(n1);
					manual.put(n1, manual.get(n2));
					manual.put(n2, temp);
					i = 0;
					continue;
				}
			}

			i++;
		}
	}
}
