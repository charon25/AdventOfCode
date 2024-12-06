package problems01To10;

import utils.FileUtils;
import utils.Pair;
import utils.Point;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Problem06 {
	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(6);
		final int height = lines.size();
		final int width = lines.get(0).length();
		Point initialGuard = null;
		final Set<Point> obstacles = new HashSet<>();

		for (int y = 0; y < height; y++) {
			final String line = lines.get(y);
			for (int x = 0; x < width; x++) {
				final char c = line.charAt(x);
				if (c == '^') {
					initialGuard = new Point(x, y);
				} else if (c == '#') {
					obstacles.add(new Point(x, y));
				}
			}
		}

		if (initialGuard == null) {
			throw new IllegalArgumentException("initial guard position not found");
		}

		final Set<Point> visited = new HashSet<>();

		// Part 1

		Direction guardDirection = Direction.UP;
		Point guard = initialGuard.copy();
		while (true) {
			visited.add(guard);
			final Point nextGuard = getNextPoint(guard, guardDirection);
			if (!nextGuard.isInBound(0, 0, width, height)) {
				break;
			}

			if (obstacles.contains(nextGuard)) {
				guardDirection = guardDirection.getNext();
				continue;
			}

			guard = nextGuard;
		}

		System.out.println(visited.size());

		// Part 2

		int total = 0;
		for (final Point potentialObstacle : visited) {
			if (potentialObstacle.equals(initialGuard)) continue;

			if (endsInALoop(width, height, obstacles, potentialObstacle, initialGuard)) {
				total++;
			}
		}

		System.out.println(total);
	}

	private static Point getNextPoint(final Point guard, final Direction guardDirection) {
		switch (guardDirection) {
			case UP:
				return new Point(guard.getX(), guard.getY() - 1);
			case RIGHT:
				return new Point(guard.getX() + 1, guard.getY());
			case DOWN:
				return new Point(guard.getX(), guard.getY() + 1);
			case LEFT:
				return new Point(guard.getX() - 1, guard.getY());
		}

		throw new IllegalArgumentException("unknown guard direction " + guardDirection);
	}

	private static boolean endsInALoop(final int width, final int height, final Set<Point> obstacles, final Point newObstacle, final Point initialGuard) {
		final Set<Pair<Point, Direction>> visited = new HashSet<>();
		Point guard = initialGuard;
		Direction guardDirection = Direction.UP;
		while (true) {
			final Pair<Point, Direction> pair = new Pair<>(guard, guardDirection);
			if (visited.contains(pair)) {
				return true;
			}

			visited.add(pair);
			final Point nextGuard = getNextPoint(guard, guardDirection);
			if (!nextGuard.isInBound(0, 0, width, height)) {
				return false;
			}

			if (obstacles.contains(nextGuard) || nextGuard.equals(newObstacle)) {
				guardDirection = guardDirection.getNext();
				continue;
			}

			guard = nextGuard;
		}
	}

	private enum Direction {
		UP, RIGHT, DOWN, LEFT;

		private Direction getNext() {
			switch (this) {
				case UP:
					return RIGHT;
				case RIGHT:
					return DOWN;
				case DOWN:
					return LEFT;
				case LEFT:
					return UP;
			}

			throw new IllegalArgumentException("unknown direction " + this);
		}
	}
}
