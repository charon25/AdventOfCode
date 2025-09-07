package charon.aoc.problems01To10;

import charon.aoc.FileUtils;
import charon.aoc.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Problem03 {
	public static void main(String[] args) {
		final List<String> schematic = FileUtils.readProblemInput(3);;

		final Pattern pattern = Pattern.compile("(\\d+)");

		// Part 1
		int totalPartNumber = 0;
		for (int y = 0; y < schematic.size(); y++) {
			final Matcher matcher = pattern.matcher(schematic.get(y));
			while (matcher.find()) {
				final int number = Integer.parseInt(matcher.group());
				if (isAdjacentToSymbole(schematic, y, matcher.start(), matcher.end())) {
					totalPartNumber += number;
				}
			}
		}

		System.out.println(totalPartNumber);

		// Part 2
		int totalGearRatio = 0;
		for (int y = 0; y < schematic.size(); y++) {
			final String row = schematic.get(y);
			for (int x = 0; x < row.length(); x++) {
				totalGearRatio += getGearRatio(schematic, x, y);
			}
		}

		System.out.println(totalGearRatio);
	}

	/**
	 * @param xEnd exclusive
	 */
	private static boolean isAdjacentToSymbole(final List<String> schematic, final int y, final int xStart, final int xEnd) {
		for (int dy = -1; dy <= 1; dy++) {
			for (int x = xStart - 1; x <= xEnd; x++) {
				if (getCharAt(schematic, x, y + dy).map(Problem03::isSymbol).orElse(false)) {
					return true;
				}
			}
		}

		return false;
	}

	private static boolean isSymbol(final char character) {
		return character != '.' && !StringUtils.isDigit(character);
	}

	private static int getGearRatio(final List<String> schematic, final int x, final int y) {
		final char character = schematic.get(y).charAt(x);
		if (character != '*') return 0;

		final Set<PartNumber> partNumbers = new HashSet<>(9);
		for (int dy = -1; dy <= 1; dy++) {
			for (int dx = -1; dx <= 1; dx++) {
//				final Optional<Character> optNeighbor = getCharAt(schematic, x + dy, y + dy)
//						.filter(StringUtils::isDigit);
//				if (optNeighbor.isEmpty()) continue;

				if (dy == 0 && dx == 0) continue;

				getPartNumber(schematic, x + dx, y + dy).ifPresent(partNumbers::add);
			}
		}

		if (partNumbers.size() != 2) return 0;
		final Iterator<PartNumber> iterator = partNumbers.iterator();
		return iterator.next().value * iterator.next().value;
	}

	private static Optional<Character> getCharAt(final List<String> schematic, final int x, final int y) {
		if (x < 0 || y < 0 || x >= schematic.get(0).length() || y >= schematic.size()) return Optional.empty();
		return Optional.of(schematic.get(y).charAt(x));
	}

	private static Optional<PartNumber> getPartNumber(final List<String> schematic, final int x, final int y) {
		final String row = schematic.get(y);
		int xStart = x;
		while (xStart > 0 && StringUtils.isDigit(row.charAt(xStart))) {
			xStart--;
		}
		if (!StringUtils.isDigit(row.charAt(xStart))) xStart++;
		int xEnd = x;
		while (xEnd < row.length() - 1 && StringUtils.isDigit(row.charAt(xEnd))) {
			xEnd++;
		}
		if (StringUtils.isDigit(row.charAt(xEnd))) xEnd++;

		if (xStart >= xEnd) return Optional.empty();

		return Optional.of(new PartNumber(Integer.parseInt(row.substring(xStart, xEnd)), y, xStart, xEnd));
	}

	private record PartNumber(int value, int y, int xStart, int xEnd) {}
}
