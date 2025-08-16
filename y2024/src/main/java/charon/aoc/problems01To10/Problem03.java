package charon.aoc.problems01To10;

import charon.aoc.FileUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Problem03 {
	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(3);

		final Pattern pattern1 = Pattern.compile("mul\\((\\d+),(\\d+)\\)");

		// Part 1

		int total1 = 0;
		for (final String line : lines) {
			final Matcher matcher = pattern1.matcher(line);
			while (matcher.find()) {
				total1 += (Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2)));
			}
		}

		System.out.println(total1);

		// Part 2

		final Pattern pattern2 = Pattern.compile("mul\\((\\d+),(\\d+)\\)|don't|do");

		int total2 = 0;
		boolean enabled = true;
		for (final String line : lines) {
			final Matcher matcher = pattern2.matcher(line);
			while (matcher.find()) {
				if ("don't".equals(matcher.group(0))) {
					enabled = false;
				} else if ("do".equals(matcher.group(0))) {
					enabled = true;
				} else if (enabled) {
					total2 += (Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2)));
				}
			}
		}

		System.out.println(total2);
	}
}
