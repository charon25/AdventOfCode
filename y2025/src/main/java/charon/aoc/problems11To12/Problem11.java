package charon.aoc.problems11To12;

import charon.aoc.FileUtils;

import java.util.*;
import java.util.function.BiFunction;

public class Problem11 {

	private static final String YOU = "you";
	private static final String OUT = "out";

	private static final String SVR = "svr";
	private static final String DAC = "dac";
	private static final String FFT = "fft";

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
		System.out.println(computePart1(network));

		// Part 2
		System.out.println(computePart2(network));
	}

	private static int computePart1(final Map<String, List<String>> network) {
		final Deque<String> queue = new ArrayDeque<>();
		queue.push(YOU);

		int paths = 0;
		while (!queue.isEmpty()) {
			final String u = queue.pop();
			if (OUT.equals(u)) {
				paths++;
				continue;
			}

			queue.addAll(network.get(u));
		}
		return paths;
	}

	// https://www.geeksforgeeks.org/dsa/count-possible-paths-two-vertices/
	private static long computePart2(final Map<String, List<String>> network) {
		final Set<String> devices = new HashSet<>();
		final Map<String, Integer> inDegreeMap = new HashMap<>();
		for (final Map.Entry<String, List<String>> e : network.entrySet()) {
			devices.add(e.getKey());
			devices.addAll(e.getValue());
			for (final String output : e.getValue()) {
				inDegreeMap.merge(output, 1, Integer::sum);
			}
		}

		final Deque<String> queue = new ArrayDeque<>();
		devices.forEach(device -> {
			if (!inDegreeMap.containsKey(device)) {
				queue.add(device);
			}
		});

		final List<String> devicesTopologicalOrder = new ArrayList<>(devices.size());
		while (!queue.isEmpty()) {
			final String u = queue.pop();
			devicesTopologicalOrder.add(u);

			final List<String> outputs = network.get(u);
			if (outputs == null) continue;

			for (final String output : outputs) {
				final Integer inDegree = inDegreeMap.get(output);
				inDegreeMap.put(output, inDegree - 1);
				if (inDegree == 1) {
					queue.add(output);
				}
			}
		}

		final BiFunction<String, String, Long> countPaths = (start, end) -> getPathsCountBetween(network, devicesTopologicalOrder, start, end);
		final long firstPossibility = countPaths.apply(SVR, DAC) * countPaths.apply(DAC, FFT) * countPaths.apply(FFT, OUT);
		final long secondPossibility = countPaths.apply(SVR, FFT) * countPaths.apply(FFT, DAC) * countPaths.apply(DAC, OUT);
		return firstPossibility + secondPossibility;
	}

	private static long getPathsCountBetween(final Map<String, List<String>> network, final List<String> devicesTopologicalOrder,
											final String start, final String end) {
		final Map<String, Long> pathCount = new HashMap<>(devicesTopologicalOrder.size());
		pathCount.put(start, 1L);

		for (final String device : devicesTopologicalOrder) {
			final long count = pathCount.getOrDefault(device, 0L);
			final List<String> outputs = network.get(device);
			if (outputs == null) continue;

			for (final String output : outputs) {
				pathCount.merge(output, count, Long::sum);
			}
		}

		return pathCount.getOrDefault(end, 0L);
	}
}
