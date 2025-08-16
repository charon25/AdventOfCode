package charon.aoc.problems1To10;

import charon.aoc.FileUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Problem01 {
	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(1);

		// Part 1
		int total = 0;
		for (final String line : lines) {
			final int tensDigit = line.chars()
					.filter(n -> '0' <= n && n <= '9')
					.map(n -> n - '0')
					.limit(1)
					.sum();
			final int onesDigit = line.chars()
					.filter(n -> '0' <= n && n <= '9')
					.map(n -> n - '0')
					.reduce((a, b) -> b).getAsInt();
			total += (10 * tensDigit + onesDigit);
		}

		System.out.println(total);

		// Part 2
		final List<String> validDigits = List.of(
				"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
				"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"
		);

		int total2 = 0;
		for (final String line : lines) {
			final int tensDigit = validDigits.stream()
					.min(Comparator.comparingInt(digit -> {
						final int index = line.indexOf(digit);
						return index >= 0 ? index : Integer.MAX_VALUE;
					}))
					.map(validDigits::indexOf)
					.map(index -> index < 10 ? index : index - 9)
					.get();
			final int onesDigit = validDigits.stream()
					.max(Comparator.comparingInt(digit -> {
						final int index = line.lastIndexOf(digit);
						return index >= 0 ? index : Integer.MIN_VALUE;
					}))
					.map(validDigits::indexOf)
					.map(index -> index < 10 ? index : index - 9)
					.get();
			total2 += (10 * tensDigit + onesDigit);
		}

		System.out.println(total2);
	}
}
