package charon.aoc.problems11To20;

import charon.aoc.utils.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class Problem20 {
	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(20);

		// Setup

		final int height = lines.size();
		final int width = lines.get(0).length();
		final boolean[][] terrain = new boolean[height][width];
		final AtomicReference<Point> startRef = new AtomicReference<>();
		final AtomicReference<Point> endRef = new AtomicReference<>();
		StringUtils.forEachChar(lines, (x, y, c) -> {
			if (c == '#') {
				terrain[y][x] = false;
			} else {
				terrain[y][x] = true;
				if (c == 'S') {
					startRef.set(new Point(x, y));
				} else if (c == 'E') {
					endRef.set(new Point(x, y));
				}
			}
		});

		if (startRef.get() == null || endRef.get() == null)
			throw new IllegalArgumentException("No start or end found!");

		final Point start = startRef.get();
		final Point end = endRef.get();
		final List<Point> basePath = getPath(terrain, start, end);

		// Part 1

		System.out.println(savingMoreThan100Picoseconds(terrain, basePath, end, 2));

		// Part 2

		System.out.println(savingMoreThan100Picoseconds(terrain, basePath, end, 20));
	}

	private static int savingMoreThan100Picoseconds(final boolean[][] terrain, final List<Point> basePath, final Point end, final int maxCheat) {
		final int height = terrain.length;
		final int width = terrain[0].length;
		final int baseLength = basePath.size();

		final Map<Point, Integer> remainingPath = new HashMap<>();
		for (int i = 0; i < baseLength; i++) {
			remainingPath.put(basePath.get(i), baseLength - i);
		}

		int count = 0;
		int hit = 0;
		for (int i = 0; i < baseLength; i++) {
			final Point cheatingStart = basePath.get(i);
			for (int cheat = 2; cheat <= maxCheat; cheat++) {
				for (final Point cheatingEnd : cheatingStart.getAllPointsAtDistance(cheat)) {
					if (!cheatingEnd.isInBound(0, 0, width, height)) continue;
					if (!getTerrain(terrain, cheatingEnd)) continue;

					final int newLength;
					if (remainingPath.containsKey(cheatingEnd)) {
						hit++;
						newLength = remainingPath.get(cheatingEnd) + i + cheat;
					} else {
						newLength = getPathLength(terrain, cheatingEnd, end) + i + cheat;
					}

					if (baseLength - newLength >= 100) {
						count++;
					}
				}
			}
		}

		return count;
	}

	private static boolean getTerrain(final boolean[][] terrain, final Point point) {
		return terrain[point.getY()][point.getX()];
	}

	private static List<Point> getPath(final boolean[][] terrain, final Point start, final Point end) {
		final Deque<List<Point>> queue = new ArrayDeque<>();
		queue.add(new ArrayList<>(List.of(start)));
		final Set<Point> visited = new HashSet<>();
		visited.add(start);

		while (!queue.isEmpty()) {
			final List<Point> path = queue.removeFirst();
			final Point point = IterUtils.getLast(path);
			if (point.equals(end)) return path;

			for (final Point neighbor : point.getAdjacent4()) {
				if (!visited.contains(neighbor) && getTerrain(terrain, neighbor)) {
					visited.add(neighbor);
					final ArrayList<Point> newPath = new ArrayList<>(path);
					newPath.add(neighbor);
					queue.addLast(newPath);
				}
			}
		}

		throw new IllegalArgumentException("No path found!");
	}

	private static int getPathLength(final boolean[][] terrain, final Point start, final Point end) {
		final Deque<Pair<Point, Integer>> queue = new ArrayDeque<>();
		queue.add(new Pair<>(start, 0));
		final Set<Point> visited = new HashSet<>();
		visited.add(start);

		while (!queue.isEmpty()) {
			final Pair<Point, Integer> pointAndDistance = queue.removeFirst();
			final Point point = pointAndDistance.getFirst();
			if (point.equals(end)) return pointAndDistance.getSecond();

			for (final Point neighbor : point.getAdjacent4()) {
				if (!visited.contains(neighbor) && getTerrain(terrain, neighbor)) {
					visited.add(neighbor);
					queue.addLast(new Pair<>(neighbor, pointAndDistance.getSecond() + 1));
				}
			}
		}

		throw new IllegalArgumentException("No path found!");
	}
}
