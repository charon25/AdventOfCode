package charon.aoc.problems01To10;

import charon.aoc.utils.FileUtils;
import charon.aoc.utils.IterUtils;
import charon.aoc.utils.Point;
import charon.aoc.utils.StringUtils;

import java.util.*;

public class Problem08 {
	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(8);
		final int height = lines.size();
		final int width = lines.get(0).length();

		final Map<Character, List<Point>> allAntennas = new HashMap<>();

		StringUtils.forEachChar(lines, (x, y, c) -> {
			if (c == '.') return;

			allAntennas.computeIfAbsent(c, ignored -> new ArrayList<>()).add(new Point(x, y));
		});

		// Part 1

		final Set<Point> antinodePositions1 = new HashSet<>();
		for (final List<Point> antennas : allAntennas.values()) {
			IterUtils.forEachUnorderedPair(antennas, (antenna1, antenna2) -> {
				final Point antinode1 = antenna1.symmetry(antenna2);
				if (antinode1.isInBound(0, 0, width, height)) {
					antinodePositions1.add(antinode1);
				}

				final Point antinode2 = antenna2.symmetry(antenna1);
				if (antinode2.isInBound(0, 0, width, height)) {
					antinodePositions1.add(antinode2);
				}
			});
		}

		System.out.println(antinodePositions1.size());

		// Part 2

		final Set<Point> antinodePositions2 = new HashSet<>();
		for (final List<Point> antennas : allAntennas.values()) {
			IterUtils.forEachUnorderedPair(antennas, (antenna1, antenna2) -> {
				antinodePositions2.addAll(getAntinodesWithHarmonics(antenna1, antenna2, width, height));
				antinodePositions2.addAll(getAntinodesWithHarmonics(antenna2, antenna1, width, height));
			});
		}

		System.out.println(antinodePositions2.size());
	}

	private static Set<Point> getAntinodesWithHarmonics(final Point antenna1, final Point antenna2, final int width, final int height) {
		final Set<Point> antinodes = new HashSet<>();
		final Point vector = antenna2.difference(antenna1);

		Point point = antenna1;
		while (point.isInBound(0, 0, width, height)) {
			antinodes.add(point);
			point = point.add(vector);
		}

		return antinodes;
	}
}
