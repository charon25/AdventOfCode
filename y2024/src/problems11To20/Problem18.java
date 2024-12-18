package problems11To20;

import utils.FileUtils;
import utils.Pair;
import utils.Point;

import java.util.*;

public class Problem18 {

	private static final int HEIGHT = 71;
	private static final int WIDTH = 71;

	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(18);

		// Part 1

		final Set<Point> corruptedBytes = new HashSet<>();
		for (int i = 0; i < 1024; i++) {
			corruptedBytes.add(getCorruptedByte(lines.get(i)));
		}

		System.out.println(getPathLength(corruptedBytes).get());

		// Part 2

		Point blockingByte = null;
		for (int i = 1024; i < lines.size(); i++) {
			final Point corruptedByte = getCorruptedByte(lines.get(i));
			corruptedBytes.add(corruptedByte);
			if (getPathLength(corruptedBytes).isEmpty()) {
				blockingByte = corruptedByte;
				break;
			}
		}

		if (blockingByte == null) {
			throw new IllegalArgumentException("The path is never fully blocked!");
		}

		System.out.printf("%d,%d%n", blockingByte.getX(), blockingByte.getY());
	}

	private static Point getCorruptedByte(final String line) {
		final String[] corruptedByte = line.split(",");
		return new Point(Integer.parseInt(corruptedByte[0]), Integer.parseInt(corruptedByte[1]));
	}

	private static Optional<Integer> getPathLength(final Set<Point> corruptedBytes) {
		final Point start = new Point(0, 0);
		final Point target = new Point(HEIGHT - 1, WIDTH - 1);
		final List<Pair<Point, Integer>> queue = new ArrayList<>();
		queue.add(new Pair<>(start, 0));
		final Set<Point> visited = new HashSet<>();
		visited.add(start);
		while (!queue.isEmpty()) {
			final Pair<Point, Integer> pointAndDistance = queue.remove(0);
			final Point point = pointAndDistance.getFirst();
			if (point.equals(target)) {
				return Optional.of(pointAndDistance.getSecond());
			}

			for (final Point neighbor : point.getAdjacent4()) {
				if (neighbor.isInBound(0, 0, WIDTH, HEIGHT) && !corruptedBytes.contains(neighbor) && !visited.contains(neighbor)) {
					visited.add(neighbor);
					queue.add(new Pair<>(neighbor, 1 + pointAndDistance.getSecond()));
				}
			}
		}

		return Optional.empty();
	}
}
