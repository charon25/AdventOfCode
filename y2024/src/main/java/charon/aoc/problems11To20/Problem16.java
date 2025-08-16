package charon.aoc.problems11To20;

import charon.aoc.FileUtils;
import charon.aoc.Pair;
import charon.aoc.Point;
import charon.aoc.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class Problem16 {
	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(16);

		final boolean[][] emptyTerrain = new boolean[lines.size()][lines.get(0).length()];
		final AtomicReference<Point> startRef = new AtomicReference<>();
		final AtomicReference<Point> endRef = new AtomicReference<>();
		final List<Position> allPositions = new ArrayList<>();
		StringUtils.forEachChar(lines, ((x, y, c) -> {
			if (c == '#') {
				emptyTerrain[y][x] = false;
			} else {
				for (final Direction direction : Direction.ALL) {
					allPositions.add(new Position(new Point(x, y), direction));
				}
				emptyTerrain[y][x] = true;
				if (c == 'S') {
					startRef.set(new Point(x, y));
				} else if (c == 'E') {
					endRef.set(new Point(x, y));
				}
			}
		}));

		if (startRef.get() == null) throw new IllegalArgumentException("No start point found!");
		if (endRef.get() == null) throw new IllegalArgumentException("No end point found!");

		final Map<Position, Integer> queue = new HashMap<>();
		allPositions.forEach(position -> queue.put(position.copy(), Integer.MAX_VALUE));
		queue.put(new Position(startRef.get().copy(), Direction.RIGHT), 0);

		final Map<Position, Integer> finalCost = new HashMap<>();

		final Map<Position, Set<Position>> previousPosition = new HashMap<>();
		while (!queue.isEmpty()) {
			final Pair<Position, Integer> positionAndCost = getMin(queue);
			final Position position = positionAndCost.getFirst();
			queue.remove(position);
			finalCost.put(position, positionAndCost.getSecond());

			for (final Position neighbor : getNeighbors(emptyTerrain, queue, position)) {
				if (!queue.containsKey(neighbor)) continue;
				final int directCost = neighbor.getFirst().equals(position.getFirst()) ? 1000 : 1;
				final int totalCost = directCost + positionAndCost.getSecond();
				if (totalCost < queue.get(neighbor)) {
					queue.put(neighbor, totalCost);
					final HashSet<Position> prev = new HashSet<>();
					prev.add(position);
					previousPosition.put(neighbor, prev);
				} else if (totalCost == queue.get(neighbor)) {
					previousPosition.computeIfAbsent(neighbor, p -> new HashSet<>()).add(position);
				}
			}
		}

		// Part 1

		final Optional<Position> target = Arrays.stream(Direction.ALL)
				.map(direction -> new Position(endRef.get(), direction))
				.min(Comparator.comparingInt(finalCost::get));

		if (target.isEmpty()) throw new IllegalArgumentException("No target after algorithm!");

		final int minPathCost = finalCost.get(target.get());
		System.out.println(minPathCost);

		// Part 2

		List<List<Position>> paths = Arrays.stream(Direction.ALL)
				.map(direction -> List.of(new Position(endRef.get(), direction)))
				.toList();
		final List<List<Position>> allPaths = new ArrayList<>();

		while (!paths.isEmpty()) {
			final List<List<Position>> newPaths = new ArrayList<>();

			for (final List<Position> path : paths) {
				final Position last = path.get(path.size() - 1);
				if (!previousPosition.containsKey(last)) {
					allPaths.add(path);
					continue;
				}
				for (final Position position : previousPosition.get(last)) {
					final ArrayList<Position> newPath = new ArrayList<>(path);
					newPath.add(position);
					newPaths.add(newPath);
				}
			}

			paths = newPaths;
		}

		final Set<Point> pointsOnShortestPaths = new HashSet<>();
		for (final List<Position> path : allPaths) {
			int cost = 0;
			for (int i = 1; i < path.size(); i++) {
				if (path.get(i).getFirst().equals(path.get(i - 1).getFirst())) {
					cost += 1000;
				} else {
					cost++;
				}
			}
			if (cost == minPathCost) {
				for (final Position position : path) {
					pointsOnShortestPaths.add(position.getFirst());
				}
			}
		}

		System.out.println(pointsOnShortestPaths.size());
	}

	private static Pair<Position, Integer> getMin(final Map<Position, Integer> queue) {
		Position minPosition = null;
		int minCost = Integer.MAX_VALUE;
		for (final Map.Entry<Position, Integer> entry : queue.entrySet()) {
			if (entry.getValue() < minCost) {
				minPosition = entry.getKey();
				minCost = entry.getValue();
			}
		}

		if (minPosition == null) throw new IllegalArgumentException("No min position found!");
		return new Pair<>(minPosition, minCost);
	}

	private static List<Position> getNeighbors(final boolean[][] emptyTerrain, final Map<Position, Integer> queue, final Position position) {
		final List<Position> neighbors = new ArrayList<>();
		final Point forward = position.getFirst().add(position.getSecond().m_vector);
		if (emptyTerrain[forward.getY()][forward.getX()]) {
			neighbors.add(new Position(forward, position.getSecond()));
		}
		for (final Direction direction : Direction.ALL) {
			if (direction == position.getSecond()) continue;
			neighbors.add(new Position(position.getFirst(), direction));
		}
		return neighbors;
	}

	private enum Direction {
		TOP(new Point(0, -1)),
		RIGHT(new Point(1, 0)),
		DOWN(new Point(0, 1)),
		LEFT(new Point(-1, 0))
		;

		private static final Direction[] ALL = values();

		private final Point m_vector;

		Direction(final Point vector) {
			m_vector = vector;
		}
	}

	private static class Position extends Pair<Point, Direction> {
		private Position(final Point first, final Direction second) {
			super(first, second);
		}

		@Override
		public Position copy() {
			return new Position(getFirst(), getSecond());
		}
	}
}
