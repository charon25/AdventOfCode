package problems01To10;

import utils.FileUtils;
import utils.Pair;
import utils.Point;
import utils.StringUtils;

import java.util.*;

public class Problem10 {
	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(10);
		final int height = lines.size();
		final int width = lines.get(0).length();
		final int[][] terrain = new int[width][height];

		final List<Point> trailheads = new ArrayList<>();
		StringUtils.forEachChar(lines, (x, y, c) -> {
			final int elevation = c - '0';
			terrain[x][y] = elevation;
			if (elevation == 0) {
				trailheads.add(new Point(x, y));
			}
		});

		// Part 1

		System.out.println(trailheads.stream().mapToInt(trailhead -> countPaths(terrain, trailhead).getFirst()).sum());

		// Part 2

		System.out.println(trailheads.stream().mapToInt(trailhead -> countPaths(terrain, trailhead).getSecond()).sum());
	}

	private static Pair<Integer, Integer> countPaths(final int[][] terrain, final Point start) {
		final Deque<Point> queue = new ArrayDeque<>();
		final Set<Point> visited = new HashSet<>();
		queue.addLast(start);
		visited.add(start);

		final Set<Point> pathEnds = new HashSet<>();
		int completePaths = 0;
		while (!queue.isEmpty()) {
			final Point u = queue.removeLast();
			final int currentElevation = terrain[u.getX()][u.getY()];
			if (currentElevation == 9) {
				pathEnds.add(u);
				completePaths++;
				continue;
			}

			for (final Point v : u.getAdjacent4()) {
				if (visited.contains(v)) continue;
				final int nextElevation = get(terrain, v);
				if (nextElevation == Integer.MIN_VALUE) continue;
				if (nextElevation == currentElevation + 1) {
					queue.addLast(v);
				}
			}
		}

		return new Pair<>(pathEnds.size(), completePaths);
	}

	private static int get(final int[][] terrain, final Point p) {
		if (p.isInBound(0, 0, terrain.length, terrain[0].length)) {
			return terrain[p.getX()][p.getY()];
		}

		return Integer.MIN_VALUE;
	}
}
