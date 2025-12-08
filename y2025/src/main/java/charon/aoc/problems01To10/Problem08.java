package charon.aoc.problems01To10;

import charon.aoc.FileUtils;
import charon.aoc.IterUtils;
import charon.aoc.Point3;

import java.util.*;
import java.util.stream.Collectors;

public class Problem08 {
	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(8);

		final List<Point3> boxes = lines.stream().map(line -> Point3.parse(line, ",")).toList();

		// Part 1
		final PriorityQueue<BoxesPair> minHeap = getPairsMinHeap(boxes);

		final int connections = 1000;
		final Map<Point3, List<Point3>> graph = new HashMap<>();
		for (int i = 0; i < connections; i++) {
			final BoxesPair pair = minHeap.poll();
			if (pair == null) throw new IllegalArgumentException("Not enough boxes!");

			graph.computeIfAbsent(pair.p1, p -> new ArrayList<>()).add(pair.p2);
			graph.computeIfAbsent(pair.p2, p -> new ArrayList<>()).add(pair.p1);
		}

		final List<Integer> circuitLengths = getCircuitLengths(graph);

		final int product = circuitLengths.stream()
				.sorted(Comparator.reverseOrder())
				.limit(3)
				.reduce(1, (a, b) -> a * b);

		System.out.println(product);

		// Part 2
		final PriorityQueue<BoxesPair> minHeap2 = getPairsMinHeap(boxes);
		while (!minHeap2.isEmpty()) {
			final BoxesPair pair = minHeap2.poll();

			graph.computeIfAbsent(pair.p1, p -> new ArrayList<>()).add(pair.p2);
			graph.computeIfAbsent(pair.p2, p -> new ArrayList<>()).add(pair.p1);
			final List<Integer> lengths = getCircuitLengths(graph);
			if (lengths.size() == 1 && lengths.get(0) == boxes.size()) {
				System.out.println((long) pair.p1.x() * pair.p2.x());
				break;
			}
		}
	}

	private static PriorityQueue<BoxesPair> getPairsMinHeap(final List<Point3> boxes) {
		final PriorityQueue<BoxesPair> minHeap = new PriorityQueue<>(BoxesPair.EUCLIDEAN_DISTANCE);
		final int boxesCount = boxes.size();
		for (int i = 0; i < boxesCount; i++) {
			for (int j = i + 1; j < boxesCount; j++) {
				minHeap.add(new BoxesPair(boxes.get(i), boxes.get(j)));
			}
		}
		return minHeap;
	}

	private static List<Integer> getCircuitLengths(final Map<Point3, List<Point3>> graph) {
		final List<Integer> circuitLengths = new ArrayList<>();
		final Set<Point3> globalVisited = new HashSet<>();

		for (final Point3 root : graph.keySet()) {
			if (globalVisited.contains(root)) continue;
			final Queue<Point3> queue = new ArrayDeque<>();
			queue.add(root);
			final Set<Point3> visited = new HashSet<>();
			visited.add(root);

			int circuitLength = 0;
			while (!queue.isEmpty()) {
				final Point3 u = queue.poll();
				globalVisited.add(u);
				circuitLength++;

				for (final Point3 v : graph.get(u)) {
					if (!visited.contains(v)) {
						queue.add(v);
						visited.add(v);
					}
				}
			}

			circuitLengths.add(circuitLength);
		}
		return circuitLengths;
	}

	private record BoxesPair(Point3 p1, Point3 p2) {
		private static final Comparator<BoxesPair> EUCLIDEAN_DISTANCE = Comparator
				.comparingLong(pair -> pair.p1.squaredDistance(pair.p2));
	}
}
