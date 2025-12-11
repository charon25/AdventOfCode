package charon.aoc.problems11To12;

import charon.aoc.FileUtils;

import java.util.*;

public class Problem11 {

	private static final String ROOT = "you";
	private static final String OUT = "out";

	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(11);

		final Map<String, List<String>> network = new HashMap<>();
		for (final String line : lines) {
			final String[] parts = line.split(": ");
			final String[] devices = parts[1].split(" ");
			final List<String> outputs = new ArrayList<>(devices.length);
			outputs.addAll(Arrays.asList(devices));
			network.put(parts[0], outputs);
		}

		// Part 1
		int paths = 0;
		final Deque<String> queue = new ArrayDeque<>();
		queue.push(ROOT);
		while (!queue.isEmpty()) {
			final String u = queue.pop();
			if (OUT.equals(u)) {
				paths++;
				continue;
			}

			queue.addAll(network.get(u));
		}

		System.out.println(paths);
	}
}
