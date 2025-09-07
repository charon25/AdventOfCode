package charon.aoc.problems11To20;

import charon.aoc.Direction;
import charon.aoc.FileUtils;

import java.util.*;

import static charon.aoc.Direction.*;

public class Problem16 {

	public static final Map<Direction, Direction> SLASH_MIRROR_MAP = Map.of(
			UP, RIGHT,
			RIGHT, UP,
			DOWN, LEFT,
			LEFT, DOWN
	);

	public static final Map<Direction, Direction> BACKSLASH_MIRROR_MAP = Map.of(
			UP, LEFT,
			RIGHT, DOWN,
			DOWN, RIGHT,
			LEFT, UP
	);

	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(16);

		// Part 1
		final int energizeTiles = getEnergizeTiles(lines, new Ray(0, 0, RIGHT));
		System.out.println(energizeTiles);

		// Part 2
		final int height = lines.size();
		final int width = lines.get(0).length();

		int maxEnergizeTiles = 0;
		for (int y = 0; y < height; y++) {
			maxEnergizeTiles = Math.max(maxEnergizeTiles, getEnergizeTiles(lines, new Ray(0, y, RIGHT)));
			maxEnergizeTiles = Math.max(maxEnergizeTiles, getEnergizeTiles(lines, new Ray(width - 1, y, LEFT)));
		}
		for (int x = 0; x < width; x++) {
			maxEnergizeTiles = Math.max(maxEnergizeTiles, getEnergizeTiles(lines, new Ray(x, 0, Direction.DOWN)));
			maxEnergizeTiles = Math.max(maxEnergizeTiles, getEnergizeTiles(lines, new Ray(x, height - 1, UP)));
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
					final Direction nextDir = SLASH_MIRROR_MAP.get(direction);
					yield new Ray[] {ray.add(nextDir)};
				}
				case '\\' -> {
					final Direction nextDir = BACKSLASH_MIRROR_MAP.get(direction);
					yield new Ray[] {ray.add(nextDir)};
				}
				case '-' -> {
					if (direction == LEFT || direction == RIGHT) {
						yield new Ray[] {ray.add(direction)};
					}
					yield new Ray[] {ray.add(LEFT), ray.add(RIGHT)};
				}
				case '|' -> {
					if (direction == UP || direction == Direction.DOWN) {
						yield new Ray[] {ray.add(direction)};
					}
					yield new Ray[] {ray.add(UP), ray.add(Direction.DOWN)};
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

	private record Ray(int x, int y, Direction direction) {
		private boolean isInsideGrid(final int width, final int height) {
			return x >= 0 && x < width && y >= 0 && y < height;
		}

		private Ray add(final Direction newDirection) {
			return new Ray(x + newDirection.getVector().getX(), y + newDirection.getVector().getY(), newDirection);
		}
	}
}
