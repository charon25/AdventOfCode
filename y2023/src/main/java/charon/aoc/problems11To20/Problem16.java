package charon.aoc.problems11To20;

import charon.aoc.FileUtils;
import charon.aoc.Point;

import java.util.*;

public class Problem16 {
	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(16);

		// Part 1
		final int energizeTiles = getEnergizeTiles(lines, new Ray(0, 0, Direction.RIGHT));
		System.out.println(energizeTiles);

		// Part 2
		final int height = lines.size();
		final int width = lines.get(0).length();

		int maxEnergizeTiles = 0;
		for (int y = 0; y < height; y++) {
			maxEnergizeTiles = Math.max(maxEnergizeTiles, getEnergizeTiles(lines, new Ray(0, y, Direction.RIGHT)));
			maxEnergizeTiles = Math.max(maxEnergizeTiles, getEnergizeTiles(lines, new Ray(width - 1, y, Direction.LEFT)));
		}
		for (int x = 0; x < width; x++) {
			maxEnergizeTiles = Math.max(maxEnergizeTiles, getEnergizeTiles(lines, new Ray(x, 0, Direction.DOWN)));
			maxEnergizeTiles = Math.max(maxEnergizeTiles, getEnergizeTiles(lines, new Ray(x, height - 1, Direction.UP)));
		}

		System.out.println(maxEnergizeTiles);

	}

	private static int getEnergizeTiles(final List<String> lines, final Ray startingRay) {
		final int height = lines.size();
		final int width = lines.get(0).length();

		final Deque<Ray> queue = new ArrayDeque<>(width * height);
		final Set<Ray> visited = new HashSet<>(width * height);

		queue.add(startingRay);
		visited.add(startingRay);

		while (!queue.isEmpty()) {
			final Ray ray = queue.pop();
			final Direction direction = ray.direction;
			final Ray[] neighbors = switch (lines.get(ray.y).charAt(ray.x)) {
				case '.' -> new Ray[] {ray.add(direction)};
				case '/' -> {
					final Direction nextDir = Direction.SLASH_MIRROR_MAP.get(direction);
					yield new Ray[] {ray.add(nextDir)};
				}
				case '\\' -> {
					final Direction nextDir = Direction.BACKSLASH_MIRROR_MAP.get(direction);
					yield new Ray[] {ray.add(nextDir)};
				}
				case '-' -> {
					if (direction == Direction.LEFT || direction == Direction.RIGHT) {
						yield new Ray[] {ray.add(direction)};
					}
					yield new Ray[] {ray.add(Direction.LEFT), ray.add(Direction.RIGHT)};
				}
				case '|' -> {
					if (direction == Direction.UP || direction == Direction.DOWN) {
						yield new Ray[] {ray.add(direction)};
					}
					yield new Ray[] {ray.add(Direction.UP), ray.add(Direction.DOWN)};
				}
				default -> throw new IllegalArgumentException("Unknown tile: " + lines.get(ray.y).charAt(ray.x));
			};

			for (final Ray neighbor : neighbors) {
				if (neighbor.isInsideGrid(width, height) && !visited.contains(neighbor)) {
					queue.add(neighbor);
					visited.add(neighbor);
				}
			}
		}

		return (int) visited.stream()
				.mapToInt(ray -> ray.y * width + ray.x)
				.distinct()
				.count();
	}

	private enum Direction {
		UP(Point.UP),
		RIGHT(Point.RIGHT),
		DOWN(Point.DOWN),
		LEFT(Point.LEFT),
		;

		private static final Map<Direction, Direction> SLASH_MIRROR_MAP = Map.of(
				UP, RIGHT,
				RIGHT, UP,
				DOWN, LEFT,
				LEFT, DOWN
		);

		private static final Map<Direction, Direction> BACKSLASH_MIRROR_MAP = Map.of(
				UP, LEFT,
				RIGHT, DOWN,
				DOWN, RIGHT,
				LEFT, UP
		);

		private final Point m_vector;

		Direction(final Point vector) {
			m_vector = vector;
		}
	}

	private record Ray(int x, int y, Direction direction) {
		private boolean isInsideGrid(final int width, final int height) {
			return x >= 0 && x < width && y >= 0 && y < height;
		}

		private Ray add(final Direction newDirection) {
			return new Ray(x + newDirection.m_vector.getX(), y + newDirection.m_vector.getY(), newDirection);
		}
	}
}
