package charon.aoc.problems01To10;

import charon.aoc.FileUtils;
import charon.aoc.Point;

import java.util.List;

public class Problem09 {
	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(9);

		final List<Point> redTiles = lines.stream()
				.map(line -> line.split(","))
				.map(parts -> new Point(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])))
				.toList();

		// Part 1
		long largestArea = 0L;
		final int redTilesCount = redTiles.size();
		for (int i = 0; i < redTilesCount; i++) {
			final Point p1 = redTiles.get(i);
			for (int j = i + 1; j < redTilesCount; j++) {
				final Point p2 = redTiles.get(j);
				final long area = (Math.abs(p1.getX() - p2.getX()) + 1L) * (Math.abs(p1.getY() - p2.getY()) + 1L);
				largestArea = Math.max(largestArea, area);
			}
		}

		System.out.println(largestArea);
	}
}
