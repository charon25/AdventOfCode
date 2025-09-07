package charon.aoc.problems01To10;

import charon.aoc.FileUtils;
import charon.aoc.MathUtils;
import charon.aoc.StringUtils;

import java.util.List;

public class Problem06 {
	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(6);
		final List<Integer> times = StringUtils.parseIntList(lines.get(0).split(":")[1].replaceAll("\\s+", " "));
		final List<Integer> distances = StringUtils.parseIntList(lines.get(1).split(":")[1].replaceAll("\\s+", " "));

		// Part 1
		long total = 1;
		for (int i = 0; i < times.size(); i++) {
			final int time = times.get(i);
			final int distance = distances.get(i);
			total *= getNumberOfWaysToWin(time, distance);
		}

		System.out.println(total);

		// Part 2
		System.out.println(getNumberOfWaysToWin(concatenateNumbers(times), concatenateNumbers(distances)));
	}

	private static int getNumberOfWaysToWin(final long time, final long distance) {
		final long delta = time * time - 4 * distance;
		final int offset;
		if (MathUtils.isPerfectSquare((int) delta) && (time + delta) % 2 == 0) {
			offset = 1;
		} else {
			offset = 0;
		}
		final double deltaSqrt = Math.sqrt(delta);
		final int minTime = (int) Math.ceil((time - deltaSqrt) / 2 + offset);
		final int maxTime = (int) Math.floor((time + deltaSqrt) / 2 - offset);
		return maxTime - minTime + 1;
	}

	private static long concatenateNumbers(final List<Integer> numbers) {
		final StringBuilder sb = new StringBuilder();
		numbers.forEach(sb::append);
		return Long.parseLong(sb.toString());
	}
}
