package charon.aoc.problems11To20;

import charon.aoc.FileUtils;
import charon.aoc.LongPoint;
import charon.aoc.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Problem11 {

	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(11);

		final Set<Integer> rowsWithoutGalaxies = IntStream.range(0, lines.size()).boxed().collect(Collectors.toSet());;
		final Set<Integer> columnsWithoutGalaxies = IntStream.range(0, lines.get(0).length()).boxed().collect(Collectors.toSet());;
		final List<LongPoint> galaxies = new ArrayList<>();
		StringUtils.forEachChar(lines, (x, y, c) -> {
			if (c == '#') {
				galaxies.add(new LongPoint(x, y));
				rowsWithoutGalaxies.remove(y);
				columnsWithoutGalaxies.remove(x);
			}
		});

		// Part 1
		System.out.println(computeTotalMinDistances(expandUniverse(2, galaxies, rowsWithoutGalaxies, columnsWithoutGalaxies)));

		// Part 2
		System.out.println(computeTotalMinDistances(expandUniverse(1_000_000, galaxies, rowsWithoutGalaxies, columnsWithoutGalaxies)));
	}

	private static long computeTotalMinDistances(final List<LongPoint> expandedGalaxies) {
		long totalDistance = 0;
		for (int i = 0; i < expandedGalaxies.size(); i++) {
			final LongPoint galaxy1 = expandedGalaxies.get(i);
			for (int j = 0; j < i; j++) {
				final LongPoint galaxy2 = expandedGalaxies.get(j);
				totalDistance += galaxy1.manhattanDistance(galaxy2);
			}
		}
		return totalDistance;
	}

	private static List<LongPoint> expandUniverse(final int expansion, final List<LongPoint> galaxies, final Set<Integer> rowsWithoutGalaxies, final Set<Integer> columnsWithoutGalaxies) {
		final List<LongPoint> expandedGalaxies = new ArrayList<>(galaxies.size());

		for (final LongPoint galaxy : galaxies) {
			final long diffY = (expansion - 1) * rowsWithoutGalaxies.stream()
					.filter(y -> galaxy.getY() > y)
					.count();
			final long diffX = (expansion - 1) * columnsWithoutGalaxies.stream()
					.filter(x -> galaxy.getX() > x)
					.count();
			expandedGalaxies.add(galaxy.add(new LongPoint(diffX, diffY)));
		}
		return expandedGalaxies;
	}
}
