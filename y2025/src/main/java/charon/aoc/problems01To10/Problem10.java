package charon.aoc.problems01To10;

import charon.aoc.FileUtils;

import java.util.*;
import java.util.stream.IntStream;

public class Problem10 {
	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(10);

		final List<Machine> machines = new ArrayList<>();
		for (final String line : lines) {
			final int i1 = line.indexOf(']');
			final int i2 = line.indexOf('{');

			final String lightsPart = line.substring(1, i1);

			final boolean[] targetLights = new boolean[lightsPart.length()];
			for (int i = 0; i < lightsPart.length(); i++) {
				targetLights[i] = lightsPart.charAt(i) == '#';
			}

			final String buttonsPart = line.substring(i1 + 2, i2 - 1);
			final List<int[]> buttons = Arrays.stream(buttonsPart.split(" "))
					.map(buttonString -> {
						final String[] indexes = buttonString.substring(1, buttonString.length() - 1).split(",");
						final int[] button = new int[indexes.length];
						for (int i = 0; i < button.length; i++) {
							button[i] = Integer.parseInt(indexes[i]);
						}
						return button;
					})
					.toList();

			final String joltagesPart = line.substring(i2 + 1, line.length() - 1);
			final List<Integer> joltages = Arrays.stream(joltagesPart.split(","))
					.map(Integer::parseInt)
					.toList();

			machines.add(new Machine(targetLights, buttons, joltages));
		}

		// Part 1
		int total = 0;
		for (final Machine machine : machines) {
			final int buttonsCount = machine.buttons.size();
			final int subsetsCount = 1 << buttonsCount;
			final List<Integer> subsets = IntStream.range(0, subsetsCount)
					.boxed()
					.sorted(Comparator.comparingInt(Integer::bitCount))
					.toList();

			for (final int subset : subsets) {
				final boolean[] lights = new boolean[machine.targetLights.length];
				for (int j = 0; j < buttonsCount; j++) {
					if ((subset & (1 << j)) > 0) {
						for (final int index : machine.buttons.get(j)) {
							lights[index] = !lights[index];
						}
					}
				}

				if (Arrays.equals(lights, machine.targetLights)) {
					total += Integer.bitCount(subset);
					break;
				}
			}
		}

		System.out.println(total);

	}

	private record Machine(boolean[] targetLights, List<int[]> buttons, List<Integer> joltages) {

	}
}
