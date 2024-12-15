package problems11To20;

import utils.*;

import java.util.*;

public class Problem12 {
	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(12);
		final int height = lines.size();
		final int width = lines.get(0).length();

		final List<Point> toCheck = new ArrayList<>();
		StringUtils.forEachChar(lines, (x, y, c) -> {
			toCheck.add(new Point(x, y));
		});

		final List<List<Point>> regions = new ArrayList<>();
		while (!toCheck.isEmpty()) {
			final Point point = toCheck.remove(toCheck.size() - 1);
			final Deque<Point> queue = new ArrayDeque<>(toCheck.size());
			queue.add(point);
			final Set<Point> visited = new HashSet<>(toCheck.size());
			visited.add(point);
			while (!queue.isEmpty()) {
				final Point u = queue.pollFirst();
				toCheck.remove(u);
				final char uPlant = getChar(lines, u);
				for (final Point v : u.getAdjacent4()) {
					if (v.isInBound(0, 0, width, height) && getChar(lines, v) == uPlant && !visited.contains(v)) {
						queue.add(v);
						visited.add(v);
					}
				}
			}
			regions.add(new ArrayList<>(visited));
		}

		// Part 1

		long totalPrice1 = 0L;
		for (final List<Point> region : regions) {
			int perimeter = 0;
			final char plant = getChar(lines, region.get(0));
			for (final Point point : region) {
				for (final Point neighbor : point.getAdjacent4()) {
					if (!neighbor.isInBound(0, 0, width, height) || getChar(lines, neighbor) != plant) {
						perimeter++;
					}
				}
			}

			totalPrice1 += (long) perimeter * region.size();
		}

		System.out.println(totalPrice1);

		// Part 2

		long totalPrice2 = 0L;
		for (final List<Point> region : regions) {
			final Map<Pair<Direction, Integer>, List<Integer>> sides = new HashMap<>();

			final char plant = getChar(lines, region.get(0));
			for (final Point point : region) {
				for (final Point neighbor : point.getAdjacent4()) {
					final Point diff = neighbor.difference(point);
					if (!neighbor.isInBound(0, 0, width, height) || getChar(lines, neighbor) != plant) {
						if (diff.getX() == 0) {
							if (diff.getY() > 0) {
								IterUtils.add(sides, new Pair<>(Direction.BOTTOM, point.getY()), point.getX());
							} else {
								IterUtils.add(sides, new Pair<>(Direction.TOP, point.getY()), point.getX());
							}
						} else {
							if (diff.getX() > 0) {
								IterUtils.add(sides, new Pair<>(Direction.RIGHT, point.getX()), point.getY());
							} else {
								IterUtils.add(sides, new Pair<>(Direction.LEFT, point.getX()), point.getY());
							}
						}
					}
				}
			}

			sides.values().forEach(list -> list.sort(Integer::compare));

			int sidesCount = 0;
			for (final List<Integer> list : sides.values()) {
				int previous = Integer.MIN_VALUE;
				for (final Integer n : list) {
					if (n != previous + 1) sidesCount++;
					previous = n;
				}
			}

			totalPrice2 += (long) sidesCount * region.size();
		}

		System.out.println(totalPrice2);

	}

	private static char getChar(final List<String> lines, final Point point) {
		return lines.get(point.getY()).charAt(point.getX());
	}

	private enum Direction {
		TOP, BOTTOM, RIGHT, LEFT
	}
}
