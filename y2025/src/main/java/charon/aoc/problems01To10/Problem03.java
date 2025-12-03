package charon.aoc.problems01To10;

import charon.aoc.FileUtils;

import java.util.Arrays;
import java.util.List;

public class Problem03 {

	private static final int PART_2_DIGIT_COUNT = 12;

	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(3);

		final int bankCount = lines.size();
		final int bankWidth = lines.get(0).length();
		final int[][] banks = new int[bankCount][bankWidth];
		for (int i = 0; i < lines.size(); i++) {
			banks[i] = lines.get(i).chars().map(c -> c - '0').toArray();
		}

		// Part 1
		int total = 0;
		for (final int[] bank : banks) {
			final int firstDigitIndex = getMaxIndex(bank, 0, bankWidth - 1);
			final int secondDigitIndex = getMaxIndex(bank, firstDigitIndex + 1, bankWidth);
			final int joltage = Integer.parseInt(String.valueOf(bank[firstDigitIndex]) + bank[secondDigitIndex]);

			total += joltage;
		}

		System.out.println(total);

		// Part 2
		long total2 = 0;
		for (final int[] bank : banks) {
			final StringBuilder sb = new StringBuilder(PART_2_DIGIT_COUNT);
			int previousDigitIndex = -1;
			for (int i = PART_2_DIGIT_COUNT - 1; i >= 0; i--) {
				final int maxIndex = getMaxIndex(bank, previousDigitIndex + 1, bankWidth - i);
				previousDigitIndex = maxIndex;
				sb.append(bank[maxIndex]);
			}
			total2 += Long.parseLong(sb.toString());
		}

		System.out.println(total2);
	}

	private static int getMaxIndex(final int[] array, final int start, final int end) {
		int max = 0;
		int maxIndex = -1;
		for (int i = start; i < end; i++) {
			if (array[i] > max) {
				max = array[i];
				maxIndex = i;
			}
		}

		if (maxIndex < 0) throw new IllegalArgumentException("Could not find non zero value in " + Arrays.toString(array));
		return maxIndex;
	}
}
