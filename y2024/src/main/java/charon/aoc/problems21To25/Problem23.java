package charon.aoc.problems21To25;

import charon.aoc.FileUtils;
import charon.aoc.IterUtils;

import java.util.*;
import java.util.stream.Collectors;

public class Problem23 {
	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(23);
		final Map<String, List<String>> network = new HashMap<>();
		for (final String line : lines) {
			final String[] computers = line.split("-");
			network.computeIfAbsent(computers[0], c -> new ArrayList<>()).add(computers[1]);
			network.computeIfAbsent(computers[1], c -> new ArrayList<>()).add(computers[0]);
		}

		// Part 1

		final Set<List<String>> computerSets = new HashSet<>();
		for (final String c1 : network.keySet()) {
			for (final String c2 : network.get(c1)) {
				for (final String c3 : network.get(c2)) {
					if (!network.get(c3).contains(c1)) continue;
					if (c1.equals(c2) || c2.equals(c3) || c3.equals(c1)) continue;
					if (c1.charAt(0) != 't' && c2.charAt(0) != 't' && c3.charAt(0) != 't') continue;
					final List<String> computerSet = new ArrayList<>();
					computerSet.add(c1);
					computerSet.add(c2);
					computerSet.add(c3);
					computerSet.sort(String::compareTo);
					computerSets.add(computerSet);
				}
			}
		}

		System.out.println(computerSets.size());

		// Part 2

		final List<Set<String>> fullyConnectedNetworks = new ArrayList<>();
		bronKerbosch(network, new HashSet<>(), new HashSet<>(network.keySet()), new HashSet<>(), fullyConnectedNetworks);

		final String password = fullyConnectedNetworks
				.stream()
				.max(Comparator.comparingInt(Set::size))
				.get()
				.stream()
				.sorted()
				.collect(Collectors.joining(","));
		System.out.println(password);
	}

	// Thank you https://en.wikipedia.org/wiki/Bron%E2%80%93Kerbosch_algorithm
	private static void bronKerbosch(final Map<String, List<String>> network, final Set<String> r, final Set<String> p, final Set<String> x, final List<Set<String>> cliques) {
		if (p.isEmpty() && x.isEmpty()) {
			cliques.add(new HashSet<>(r));
			return;
		}

		for (final String v : new ArrayList<>(p)) {
			bronKerbosch(
					network,
					IterUtils.union(r, v),
					IterUtils.intersection(p, network.get(v)),
					IterUtils.intersection(x, network.get(v)),
					cliques
			);
			p.remove(v);
			x.add(v);
		}
	}
}
