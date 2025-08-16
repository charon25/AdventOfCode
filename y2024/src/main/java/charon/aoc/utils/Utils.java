package charon.aoc.utils;

import java.util.ArrayList;
import java.util.List;

public final class Utils {
	private Utils() {}

	/**
	 * @param lines all lines must have the same length
	 */
	public static List<String> transpose(final List<String> lines) {
		final StringBuilder[] columnBuilders = new StringBuilder[lines.get(0).length()];
		for (final String line : lines) {
			for (int i = 0; i < line.length(); i++) {
				if (columnBuilders[i] == null) columnBuilders[i] = new StringBuilder();
				columnBuilders[i].append(line.charAt(i));
			}
		}

		final List<String> columns = new ArrayList<>(columnBuilders.length);
		for (final StringBuilder stringBuilder : columnBuilders) {
			columns.add(stringBuilder.toString());
		}

		return columns;
	}
}
