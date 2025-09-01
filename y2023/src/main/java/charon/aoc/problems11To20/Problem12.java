package charon.aoc.problems11To20;

import charon.aoc.FileUtils;
import charon.aoc.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Problem12 {
	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(12);

		final List<Row> rows = getRows(lines);

		// Part 1
		long total = 0;
		for (final Row row : rows) {
			total += getArrangementCount(row);
		}

		System.out.println(total);

		// Part 2
		final List<String> unfoldedLines = unfoldLines(lines, 5);
		final List<Row> unfoldedRows = getRows(unfoldedLines);

		long total2 = 0;
		for (final Row row : unfoldedRows) {
			total2 += getArrangementCount(row);
		}

		System.out.println(total2);
	}

	private static List<String> unfoldLines(final List<String> lines, final int repeat) {
		final List<String> unfoldedLines = new ArrayList<>(lines.size());
		for (final String line : lines) {
			final String[] parts = line.split(" ");
			final StringBuilder sb = new StringBuilder();
			for (int i = 0; i < repeat; i++) {
				sb.append(parts[0]);
				if (i < repeat - 1) sb.append('?');
			}

			sb.append(' ');
			for (int i = 0; i < repeat; i++) {
				sb.append(parts[1]);
				if (i < repeat - 1) sb.append(',');
			}

			unfoldedLines.add(sb.toString());
		}
		return unfoldedLines;
	}

	private static List<Row> getRows(final List<String> lines) {
		final List<Row> rows = new ArrayList<>(lines.size());
		for (final String line : lines) {
			final String[] parts = line.split(" ");
			final Row row = new Row(parts[0], StringUtils.parseIntList(parts[1], ","));
			rows.add(row);
		}
		return rows;
	}

	private static long getArrangementCount(final Row row) {
		return getArrangementCount(row, 0, 0, 0, null, new HashMap<>());
	}

	private static long getArrangementCount(final Row row, final int springIndex, final int groupIndex,
										   final int groupLength, final Character currentSpring, final Map<Long, Long> cache) {
		if (springIndex >= row.springs.length()) {
			if (groupLength == 0) {
				return groupIndex >= row.damagedGroups.size() ? 1 : 0;
			}
			if (groupIndex != row.damagedGroups.size() - 1) return 0;
			if (groupLength != row.damagedGroups.getLast()) return 0;
			return 1;
		}

		final long key = getKey(springIndex, groupIndex, groupLength, currentSpring);
		final Long cachedResult = cache.get(key);
		if (cachedResult != null) return cachedResult;

		final char spring = currentSpring != null ? currentSpring : row.springs.charAt(springIndex);
		final long result = switch (spring) {
			case '.' -> {
				if (groupLength == 0) {
					yield getArrangementCount(row, springIndex + 1, groupIndex, 0, null, cache);
				}
				if (groupIndex >= row.damagedGroups.size()) yield 0;
				if (groupLength != row.damagedGroups.get(groupIndex)) yield 0;
				yield getArrangementCount(row, springIndex + 1, groupIndex + 1, 0, null, cache);
			}
			case '#' -> {
				if (groupIndex >= row.damagedGroups.size()) yield 0;
				if (groupLength > row.damagedGroups.get(groupIndex)) yield 0;
				yield getArrangementCount(row, springIndex + 1, groupIndex, groupLength + 1, null, cache);
			}
			case '?' -> getArrangementCount(
					row, springIndex, groupIndex, groupLength, '.', cache
			) + getArrangementCount(
					row, springIndex, groupIndex, groupLength, '#', cache
			);
			default -> throw new IllegalArgumentException("Invalid character: " + spring);
		};

		cache.put(key, result);
		return result;
	}

	private static long getKey(final int springIndex, final int groupIndex, final int groupLength, final Character currentSpring) {
		return 1_000_000_000L * springIndex + 1_000_000L * groupIndex + 1_000L * groupLength + (currentSpring == null ? 0 : currentSpring);
	}

	private record Row(String springs, List<Integer> damagedGroups) {}
}
