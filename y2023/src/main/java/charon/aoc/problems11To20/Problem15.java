package charon.aoc.problems11To20;

import charon.aoc.FileUtils;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Problem15 {
	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(15);

		final String[] commands = lines.get(0).split(",");

		// Part 1
		final int totalHash = Arrays.stream(commands).mapToInt(Problem15::hash).sum();
		System.out.println(totalHash);

		// Part 2
		final Box[] boxes = new Box[256];
		for (int i = 0; i < boxes.length; i++) {
			boxes[i] = new Box(new LinkedHashMap<>());
		}

		for (final String command : commands) {
			if (command.contains("=")) {
				final String[] parts = command.split("=");
				final int index = hash(parts[0]);
				boxes[index].lenses.merge(parts[0], Integer.parseInt(parts[1]), (v1, v2) -> v2);
			} else if (command.contains("-")) {
				final String label = command.substring(0, command.length() - 1);
				final int index = hash(label);
				boxes[index].lenses.remove(label);
			} else {
				throw new IllegalArgumentException("Invalid command: " + command);
			}
		}

		int totalFocusingPower = 0;
		for (int i = 0; i < boxes.length; i++) {
			final Map<String, Integer> lenses = boxes[i].lenses;
			int boxFocusingPower = 0;
			int j = 1;
			for (final int focalLength : lenses.values()) {
				boxFocusingPower += ((i + 1) * j * focalLength);
				j++;
			}
			totalFocusingPower += boxFocusingPower;
		}

		System.out.println(totalFocusingPower);
	}

	private static int hash(final String string) {
		int hash = 0;
		for (int i = 0; i < string.length(); i++) {
			hash += string.charAt(i);
			hash = (hash * 17) & 0xFF;
		}
		return hash;
	}

	private record Box(Map<String, Integer> lenses) {}
}
