package charon.aoc.problems01To10;

import charon.aoc.FileUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Problem02 {

	private static final int RED = 0;
	private static final int GREEN = 1;
	private static final int BLUE = 2;

	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(2);

		final List<Game> games = new ArrayList<>(lines.size());
		for (final String line : lines) {
			final String[] parts = line.split(": ");
			final int id = Integer.parseInt(parts[0].split(" ")[1]);

			final String[] roundsStr = parts[1].split("; ");
			final List<int[]> rounds = new ArrayList<>(roundsStr.length);

			for (final String roundStr : roundsStr) {
				final int[] colors = new int[3];
				final String[] colorsStr = roundStr.split(", ");
				for (final String colorStr : colorsStr) {
					final String[] colorData = colorStr.split(" ");
					final int quantity = Integer.parseInt(colorData[0]);
					colors[getIndexOfColor(colorData[1])] = quantity;
				}
				rounds.add(colors);
			}

			games.add(new Game(id, rounds));
		}

		// Part 1
		int total = 0;
		for (final Game game : games) {
			if (game.getColorMaximum(RED) <= 12 && game.getColorMaximum(GREEN) <= 13 && game.getColorMaximum(BLUE) <= 14) {
				total += game.id;
			}
		}
		System.out.println(total);

		// Part 2
		int total2 = 0;
		for (final Game game : games) {
			total2 += game.getColorMaximum(RED) * game.getColorMaximum(GREEN) * game.getColorMaximum(BLUE);
		}
		System.out.println(total2);
	}

	private static int getIndexOfColor(final String color) {
		return switch (color) {
			case "red" -> RED;
			case "green" -> GREEN;
			case "blue" -> BLUE;
			case null, default -> throw new IllegalArgumentException("Unknown color: " + color);
		};
	}
	
	private record Game(int id, List<int[]> rounds) {

		private int getColorMaximum(final int colorId) {
			return rounds.stream()
					.mapToInt(round -> round[colorId])
					.max()
					.getAsInt();
		}

		@Override
		public String toString() {
			return "Game{" +
					"id=" + id +
					", rounds=" + rounds.stream().map(Arrays::toString).toList() +
					'}';
		}
	}
}
