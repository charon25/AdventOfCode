package charon.aoc.problems01To10;

import charon.aoc.FileUtils;
import charon.aoc.Point;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Problem04 {
	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(4);

		final int height = lines.size();
		final int width = lines.get(0).length();

		final List<Point> rolls = new ArrayList<>(height * width);
		for (int y = 0; y < height; y++) {
			final String row = lines.get(y);
			for (int x = 0; x < width; x++) {
				if (row.charAt(x) == '@') {
					rolls.add(new Point(x, y));
				}
			}
		}

		final HashSet<Point> rollsSet = new HashSet<>(rolls);

		// Part 1
		System.out.println(getRemovableRolls(rolls, rollsSet).size());

		// Part 2
		int totalRemoved = 0;
		List<Point> removableRolls;
		do {
			removableRolls = getRemovableRolls(rolls, rollsSet);
			totalRemoved += removableRolls.size();
			removableRolls.forEach(roll -> {
				rolls.remove(roll);
				rollsSet.remove(roll);
			});
		} while (!removableRolls.isEmpty());

		System.out.println(totalRemoved);
	}

	private static List<Point> getRemovableRolls(final List<Point> rolls, final Set<Point> rollsSet) {
		final List<Point> removableRolls = new ArrayList<>();

		for (final Point roll : rolls) {
			int surrounding = 0;
			for (final Point adjacent : roll.getAdjacent8()) {
				if (rollsSet.contains(adjacent)) surrounding++;
			}

			if (surrounding < 4) removableRolls.add(roll);
		}

		return removableRolls;
	}
}
