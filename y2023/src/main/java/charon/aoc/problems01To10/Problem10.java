package charon.aoc.problems01To10;

import charon.aoc.FileUtils;
import charon.aoc.Point;
import charon.aoc.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class Problem10 {

	private static final Point[] NO_PIPE = new Point[0];

	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(10);

		final int height = lines.size();
		final int width = lines.get(0).length();

		final Map<Point, List<Point>> map = new HashMap<>(height * width);
		final AtomicReference<Point> startingPoint = new AtomicReference<>(null);
		StringUtils.forEachChar(lines, (x, y, c) -> {
			final Point source = new Point(x, y);

			final Point[] destinations = switch (c) {
				case '|' -> new Point[] {new Point(x, y - 1), new Point(x, y + 1)};
				case '-' -> new Point[] {new Point(x - 1, y), new Point(x + 1, y)};
				case 'L' -> new Point[] {new Point(x, y - 1), new Point(x + 1, y)};
				case 'J' -> new Point[] {new Point(x, y - 1), new Point(x - 1, y)};
				case '7' -> new Point[] {new Point(x - 1, y), new Point(x, y + 1)};
				case 'F' -> new Point[] {new Point(x + 1, y), new Point(x, y + 1)};
				case '.' -> NO_PIPE;
				case 'S' -> {
					startingPoint.set(source);
					yield NO_PIPE;
				}
				default -> throw new IllegalArgumentException("Invalid character: " + c);
			};

			final List<Point> filteredDestinations = Arrays.stream(destinations)
					.filter(point -> point.isInBound(0, 0, width, height))
					.toList();

			map.put(source, filteredDestinations);
		});

		final Point start = startingPoint.get();
		final List<Point> startingPointNeighbors = map.entrySet().stream()
				.filter(e -> e.getValue().contains(start))
				.map(Map.Entry::getKey)
				.toList();

		map.put(start, startingPointNeighbors);

		final Set<Point> visited = new HashSet<>();
		Point current = start;
		int cycleLength = 0;
		final List<Point> cycle = new ArrayList<>();
		while (cycleLength == 0 || !current.equals(start)) {
			visited.add(current);
			cycle.add(current);
			boolean found = false;
			for (final Point point : map.get(current)) {
				if (!visited.contains(point)) {
					current = point;
					cycleLength++;
					found = true;
					break;
				}
			}
			if (!found) break;
		}

		// Part 1
		System.out.println((cycleLength + 1) / 2);

		// Part 2 (Pick's theorem)
		int area = cycle.getLast().getX() * cycle.getFirst().getY() - cycle.getLast().getY() * cycle.getFirst().getX();
		for (int i = 0; i < cycle.size() - 1; i++) {
			final Point p1 = cycle.get(i);
			final Point p2 = cycle.get(i + 1);
			area += (p1.getX() * p2.getY() - p1.getY() * p2.getX());
		}
		area = Math.abs(area) / 2;
		System.out.println(area - cycleLength / 2);
	}
}
