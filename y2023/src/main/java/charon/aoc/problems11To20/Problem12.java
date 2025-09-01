package charon.aoc.problems11To20;

import charon.aoc.FileUtils;
import charon.aoc.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Problem12 {
	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(12);

		final List<Row> rows = new ArrayList<>(lines.size());
		for (final String line : lines) {
			final String[] parts = line.split(" ");
			final List<Integer> unknownSprings = new ArrayList<>();
			for (int i = 0; i < parts[0].length(); i++) {
				if (parts[0].charAt(i) == '?') unknownSprings.add(i);
			}
			final Row row = new Row(parts[0].toCharArray(), unknownSprings, StringUtils.parseIntList(parts[1], ","));
			rows.add(row);
		}

		// Part 1
		int total = 0;
		for (final Row row : rows) {
			total += getArrangementCount(row);
		}

		System.out.println(total);
	}

	private static int getArrangementCount(final Row row) {
		return func(row, 0);
	}

	private static int func(final Row row, final int springIndex) {
		if (springIndex >= row.unknownSprings.size()) return row.isValid() ? 1 : 0;

		final int recordIndex = row.unknownSprings.get(springIndex);

		int arrangements = 0;
		row.conditionRecord[recordIndex] = '.';
		arrangements += func(row, springIndex + 1);
		row.conditionRecord[recordIndex] = '#';
		arrangements += func(row, springIndex + 1);
		row.conditionRecord[recordIndex] = '?';
		return arrangements;
	}

	private record Row(char[] conditionRecord, List<Integer> unknownSprings, List<Integer> damagedGroups) {
		private boolean isValid() {
			final List<Integer> groups = new ArrayList<>(conditionRecord.length);
			boolean inGroup = false;
			int groupLength = 0;
			for (final char c : conditionRecord) {
				if (c == '.') {
					if (inGroup) {
						groups.add(groupLength);
					}
					inGroup = false;
					groupLength = 0;
				} else if (c == '#') {
					if (inGroup) {
						groupLength++;
					} else {
						inGroup = true;
						groupLength = 1;
					}
				}
			}
			if (inGroup) {
				groups.add(groupLength);
			}

			return groups.equals(damagedGroups);
		}
	}
}
