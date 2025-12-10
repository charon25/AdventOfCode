package charon.aoc.problems01To10;

import charon.aoc.FileUtils;
import charon.aoc.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
				largestArea = Math.max(largestArea, getArea(p1, p2));
			}
		}

		System.out.println(largestArea);

		// Part 2
		final List<Edge> edges = new ArrayList<>(redTilesCount);
		for (int i = 0; i < redTilesCount - 1; i++) {
			final Point p1 = redTiles.get(i);
			final Edge edge = new Edge(p1, redTiles.get(i + 1));
			edges.add(edge);
		}
		edges.add(new Edge(redTiles.getLast(), redTiles.getFirst()));

		final Polygon polygon = new Polygon(edges, new HashMap<>());

		long largestArea2 = 0L;
		for (int i = 0; i < redTilesCount; i++) {
			final Point p1 = redTiles.get(i);
			for (int j = i + 1; j < redTilesCount; j++) {
				final Point p2 = redTiles.get(j);
				if (polygon.containsRectangle(p1, p2)) {
					largestArea2 = Math.max(largestArea2, getArea(p1, p2));
				}
			}
		}

		System.out.println(largestArea2);
	}

	private static long getArea(final Point p1, final Point p2) {
		return (Math.abs(p1.getX() - p2.getX()) + 1L) * (Math.abs(p1.getY() - p2.getY()) + 1L);
	}

	private record Polygon(List<Edge> edges, Map<Point, Boolean> containsCache) {
		private boolean containsRectangle(final Point p1, final Point p2) {
			final int minX = Math.min(p1.getX(), p2.getX());
			final int minY = Math.min(p1.getY(), p2.getY());
			final int maxX = Math.max(p1.getX(), p2.getX());
			final int maxY = Math.max(p1.getY(), p2.getY());

			// Top
			for (int x = minX; x < maxX; x++) {
				if (!contains(new Point(x, minY))) return false;
			}

			// Right
			for (int y = minY; y < maxY; y++) {
				if (!contains(new Point(maxX, y))) return false;
			}

			// Bottom
			for (int x = maxX; x > minX; x--) {
				if (!contains(new Point(x, maxY))) return false;
			}

			// Left
			for (int y = maxY; y > minY; y--) {
				if (!contains(new Point(minX, y))) return false;
			}

			return true;
		}

		private boolean contains(final Point point) {
			final Boolean cachedValue = containsCache.get(point);
			if (Boolean.TRUE.equals(cachedValue)) {
				return cachedValue;
			}

			int intersection = 0;
			final int x = point.getX();
			final int y = point.getY();
			for (final Edge edge : edges) {
				if (edge.contains(point)) {
					containsCache.put(point, Boolean.TRUE);
					return true;
				}

				if (!edge.isVertical()) continue;
				if (edge.p1.getX() <= x) continue;
				final int y1 = edge.p1.getY();
				final int y2 = edge.p2.getY();
				if (Math.min(y1, y2) <= y && y < Math.max(y1, y2)) {
					intersection++;
				}
			}

			final boolean contains = intersection % 2 == 1;
			containsCache.put(point, contains);
			return contains;
		}
	}

	private record Edge(Point p1, Point p2) {
		private boolean isVertical() {
			return p1.getX() == p2.getX();
		}

		private boolean contains(final Point point) {
			return point.isInBound(
					getMinX(),
					getMinY(),
					1 + getMaxX(),
					1 + getMaxY()
			);
		}

		private int getMinX() {
			return Math.min(p1.getX(), p2.getX());
		}

		private int getMinY() {
			return Math.min(p1.getY(), p2.getY());
		}

		private int getMaxX() {
			return Math.max(p1.getX(), p2.getX());
		}

		private int getMaxY() {
			return Math.max(p1.getY(), p2.getY());
		}
	}
}
