package charon.aoc.problems11To20;

import charon.aoc.FileUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Problem14 {

	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(14);

		final int height = lines.size();
		final int width = lines.get(0).length();

		final Type[][] terrain = new Type[height][width];

		for (int y = 0; y < height; y++) {
			final String row = lines.get(y);
			for (int x = 0; x < width; x++) {
				terrain[y][x] = Type.fromCharacter(row.charAt(x));
			}
		}

		// Part 1
		int totalLoad = 0;
		for (int x = 0; x < width; x++) {
			int nextAvailable = 0;
			for (int y = 0; y < height; y++) {
				final Type type = terrain[y][x];
				if (type == Type.SQUARE) {
					nextAvailable = y + 1;
				} else if (type == Type.ROUND) {
					totalLoad += height - nextAvailable;
					nextAvailable++;
				}
			}
		}

		System.out.println(totalLoad);

		// Part 2
		final Map<Integer, Integer> hashes = new HashMap<>();
		int hashCode;
		int i = 0;
		while (true) {
			hashCode = Arrays.deepHashCode(terrain);
			if (hashes.containsKey(hashCode)) break;
			hashes.put(hashCode, i);
			doOneCycle(terrain, height, width);
			i++;
		}

		final int m = i - hashes.get(hashCode);
		final int r = (1_000_000_000 - i) % m;
		for (int j = 0; j < r; j++) {
			doOneCycle(terrain, height, width);
		}
		System.out.println(getTotalLoad(terrain, height, width));
	}

	private static void doOneCycle(final Type[][] terrain, final int height, final int width) {
		tiltNorth(terrain, height, width);
		tiltWest(terrain, height, width);
		tiltSouth(terrain, height, width);
		tiltEast(terrain, height, width);
	}

	private static void tiltNorth(final Type[][] terrain, final int height, final int width) {
		for (int x = 0; x < width; x++) {
			int nextAvailable = 0;
			for (int y = 0; y < height; y++) {
				final Type type = terrain[y][x];
				if (type == Type.SQUARE) {
					nextAvailable = y + 1;
				} else if (type == Type.ROUND) {
					if (nextAvailable < y) {
						terrain[nextAvailable][x] = Type.ROUND;
						terrain[y][x] = Type.EMPTY;
					}
					nextAvailable++;
				}
			}
		}
	}

	private static void tiltEast(final Type[][] terrain, final int height, final int width) {
		for (int y = 0; y < height; y++) {
			int nextAvailable = width - 1;
			for (int x = width - 1 ; x >= 0; x--) {
				final Type type = terrain[y][x];
				if (type == Type.SQUARE) {
					nextAvailable = x - 1;
				} else if (type == Type.ROUND) {
					if (x < nextAvailable) {
						terrain[y][nextAvailable] = Type.ROUND;
						terrain[y][x] = Type.EMPTY;
					}
					nextAvailable--;
				}
			}
		}
	}

	private static void tiltSouth(final Type[][] terrain, final int height, final int width) {
		for (int x = 0; x < width; x++) {
			int nextAvailable = height - 1;
			for (int y = height - 1; y >= 0; y--) {
				final Type type = terrain[y][x];
				if (type == Type.SQUARE) {
					nextAvailable = y - 1;
				} else if (type == Type.ROUND) {
					if (y < nextAvailable) {
						terrain[nextAvailable][x] = Type.ROUND;
						terrain[y][x] = Type.EMPTY;
					}
					nextAvailable--;
				}
			}
		}
	}

	private static void tiltWest(final Type[][] terrain, final int height, final int width) {
		for (int y = 0; y < height; y++) {
			int nextAvailable = 0;
			for (int x = 0; x < width; x++) {
				final Type type = terrain[y][x];
				if (type == Type.SQUARE) {
					nextAvailable = x + 1;
				} else if (type == Type.ROUND) {
					if (nextAvailable < x) {
						terrain[y][nextAvailable] = Type.ROUND;
						terrain[y][x] = Type.EMPTY;
					}
					nextAvailable++;
				}
			}
		}
	}

	private static int getTotalLoad(final Type[][] terrain, final int height, final int width) {
		int totalLoad = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				totalLoad += (terrain[y][x] == Type.ROUND ? height - y : 0);
			}
		}

		return totalLoad;
	}

	private enum Type {
		EMPTY('.'),
		ROUND('O'),
		SQUARE('#'),
		;

		private static final Type[] VALUES = values();
		private final char m_character;

		Type(final char character) {
			m_character = character;
		}

		private char getCharacter() {
			return m_character;
		}

		private static Type fromCharacter(final char character) {
			for (final Type type : VALUES) {
				if (type.m_character == character) return type;
			}

			throw new IllegalArgumentException("Unknown character: " + character);
		}
	}
}
