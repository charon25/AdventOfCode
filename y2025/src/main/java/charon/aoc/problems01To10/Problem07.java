package charon.aoc.problems01To10;

import charon.aoc.FileUtils;
import charon.aoc.Point;

import java.util.*;

public class Problem07 {
	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(7);

		final int height = lines.size();
		final int width = lines.get(0).length();
		final CellType[][] manifold = new CellType[height][width];
		Point source = null;
		for (int y = 0; y < height; y++) {
			final String line = lines.get(y);
			for (int x = 0; x < width; x++) {
				manifold[y][x] = CellType.parse(line.charAt(x));
				if (manifold[y][x] == CellType.SOURCE) {
					source = new Point(x, y);
				}
			}
		}

		if (source == null) throw new IllegalArgumentException("Could not find source");

		// Part 1
		int splitCount = 0;
		Set<Integer> beams = Set.of(source.getX());
		for (int y = source.getY(); y < height; y++) {
			final Set<Integer> newBeams = new HashSet<>(2 * beams.size());
			final CellType[] row = manifold[y];
			for (final int beam : beams) {
				if (row[beam] == CellType.SPLITTER) {
					if (beam > 0) newBeams.add(beam - 1);
					if (beam < width - 1) newBeams.add(beam + 1);
					splitCount++;
				} else {
					newBeams.add(beam);
				}
			}
			beams = newBeams;
		}

		System.out.println(splitCount);

		// Part 2
		System.out.println(computeTimelines(manifold, source.getX(), source.getY(), new HashMap<>()));
	}

	private static long computeTimelines(final CellType[][] manifold, final int x, final int y, final Map<Point, Long> cache) {
		if (y >= manifold.length - 1) return 1;
		if (x < 0 || x >= manifold[0].length) return 0;

		final Point point = new Point(x, y);
		final Long cachedResult = cache.get(point);
		if (cachedResult != null) return cachedResult;

		final long result;
		if (manifold[y][x] == CellType.SPLITTER) {
			result = computeTimelines(manifold, x - 1, y + 1, cache) + computeTimelines(manifold, x + 1, y + 1, cache);
		} else {
			result = computeTimelines(manifold, x, y + 1, cache);
		}

		cache.put(point, result);
		return result;
	}

	private enum CellType {
		SOURCE, EMPTY, SPLITTER;

		private static CellType parse(final char c) {
			return switch (c) {
				case 'S' -> SOURCE;
				case '.' -> EMPTY;
				case '^' -> SPLITTER;
				default -> throw new IllegalArgumentException("Unknown cell type: " + c);
			};
		}
	}
}
