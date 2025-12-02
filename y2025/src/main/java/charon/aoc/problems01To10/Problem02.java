package charon.aoc.problems01To10;

import charon.aoc.FileUtils;
import charon.aoc.MathUtils;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Problem02 {

	private static final int MAX_DIGITS = 10;

	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(2);

		final List<Range> ranges = Arrays.stream(lines.get(0).split(","))
				.map(line -> line.split("-"))
				.map(parts -> new Range(Long.parseLong(parts[0]), Long.parseLong(parts[1])))
				.toList();

		// Part 1
		long total = 0L;
		for (final Range range : ranges) {
			final int startLength = String.valueOf(range.start()).length();
			final int endLength = String.valueOf(range.end()).length();
			if (startLength == endLength && startLength % 2 == 1) continue;

			final int length;
			if (startLength == endLength) {
				length = startLength;
			} else {
				if (startLength % 2 == 1) {
					length = endLength;
				} else {
					length = startLength;
				}
			}

			final long searchStart = MathUtils.pow(10, length / 2 - 1);
			final long searchEnd = MathUtils.pow(10, length / 2) - 1;
			for (long n = searchStart; n <= searchEnd; n++) {
				final long value = Long.parseLong(String.valueOf(n).repeat(2));
				if (range.contains(value)) {
					total += value;
				}
			}
		}

		System.out.println(total);

		// Part 2
		final Set<Long> repeatingNumbers = new HashSet<>();
		for (int n = 2; n <= MAX_DIGITS; n++) {
			for (int k = 1; k <= n / 2; k++) {
				if (n % k != 0) continue;

				final int repeat = n / k;
				final long start = MathUtils.pow(10, k - 1);
				final long end = MathUtils.pow(10, k) - 1;
				LongStream.range(start, end + 1)
						.mapToObj(value -> String.valueOf(value).repeat(repeat))
						.map(Long::parseLong)
						.forEach(repeatingNumbers::add);
			}
		}

		final ArrayList<Long> repeatingNumbersList = new ArrayList<>(repeatingNumbers);

		long total2 = 0L;
		for (final Range range : ranges) {
			for (final long number : repeatingNumbersList) {
				if (range.contains(number)) {
					total2 += number;
				}
			}
		}

		System.out.println(total2);
	}

	// Both inclusive
	private record Range(long start, long end) {
		private boolean contains(final long value) {
			return start <= value && value <= end;
		}
	}
}
