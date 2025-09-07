package charon.aoc.problems11To20;

import charon.aoc.Direction;
import charon.aoc.FileUtils;
import charon.aoc.Point;

import java.util.*;

public class Problem17 {
	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(17);

		final int height = lines.size();
		final int width = lines.get(0).length();

		final int[][] heatLosses = new int[height][width];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				heatLosses[y][x] = Character.getNumericValue(lines.get(y).charAt(x));
			}
		}

		// Part 1
		System.out.println(getMinHeatLoss(heatLosses, 1, 3));

		// Part 2
		System.out.println(getMinHeatLoss(heatLosses, 4, 10));
	}

	private static int getMinHeatLoss(final int[][] heatLosses, final int minStraightDistance, final int maxStraightDistance) {
		final int height = heatLosses.length;
		final int width = heatLosses[0].length;

		final Point start = new Point(0, 0);
		final Point target = new Point(width - 1, height - 1);

		final PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingInt(State::distance));

		queue.add(new State(start, 0, Direction.DOWN, 0));
		queue.add(new State(start, 0, Direction.RIGHT, 0));

		final Set<Position> visited = new HashSet<>(width * height);

		while (!queue.isEmpty()) {
			final State u = queue.poll();

			if (u.point.equals(target) && u.stepsCount >= minStraightDistance) {
				return u.distance;
			}

			final Position position = u.getPosition();
			if (visited.contains(position)) continue;
			visited.add(position);

			if (u.stepsCount >= minStraightDistance) {
				final Direction clockwise = Direction.CLOCKWISE.get(u.direction);
				final Point clockwisePoint = u.point.add(clockwise.getVector());
				if (clockwisePoint.isInBound(0, 0, width, height)) {
					final int heatLoss = heatLosses[clockwisePoint.getY()][clockwisePoint.getX()];
					queue.add(new State(clockwisePoint, u.distance + heatLoss, clockwise, 1));
				}

				final Direction counterClockwise = Direction.COUNTER_CLOCKWISE.get(u.direction);
				final Point counterClockwisePoint = u.point.add(counterClockwise.getVector());
				if (counterClockwisePoint.isInBound(0, 0, width, height)) {
					final int heatLoss = heatLosses[counterClockwisePoint.getY()][counterClockwisePoint.getX()];
					queue.add(new State(counterClockwisePoint, u.distance + heatLoss, counterClockwise, 1));
				}
			}


			if (u.stepsCount < maxStraightDistance) {
				final Point forward = u.point.add(u.direction.getVector());
				if (forward.isInBound(0, 0, width, height)) {
					final int heatLoss = heatLosses[forward.getY()][forward.getX()];
					queue.add(new State(forward, u.distance + heatLoss, u.direction, u.stepsCount + 1));
				}
			}
		}

		throw new IllegalArgumentException("Could not reach the end with parameters " + minStraightDistance + ", " + maxStraightDistance);
	}

	private record State(Point point, int distance, Direction direction, int stepsCount) {
		private Position getPosition() {
			return new Position(point, direction, stepsCount);
		}

		@Override
		public boolean equals(final Object object) {
			if (this == object) return true;
			if (object == null || getClass() != object.getClass()) return false;
			final State state = (State) object;
			return Objects.equals(point, state.point);
		}

		@Override
		public int hashCode() {
			return Objects.hash(point);
		}
	}

	private record Position(Point point, Direction direction, int stepsCount) {}
}
