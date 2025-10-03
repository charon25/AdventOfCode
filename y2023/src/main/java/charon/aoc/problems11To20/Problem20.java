package charon.aoc.problems11To20;

import charon.aoc.FileUtils;

import java.util.*;

public class Problem20 {

	private static final String BROADCASTER_MODULE_NAME = "broadcaster";

	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(20);

		final Map<String, List<String>> descending = new HashMap<>();
		final Map<String, List<String>> ascending = new HashMap<>();
		for (final String line : lines) {
			final String[] parts = line.split(" -> ");
			final String moduleName = parts[0];
			final List<String> moduleDestinations = List.of(parts[1].split(", "));

			descending.computeIfAbsent(moduleName, m -> new ArrayList<>())
					.addAll(moduleDestinations);
			moduleDestinations.forEach(destination -> ascending.computeIfAbsent(destination, d -> new ArrayList<>()).add(moduleName));
		}

		Module broadcaster = null;
		final Map<String, Module> moduleByNames = new HashMap<>(lines.size());
		for (final String moduleName : descending.keySet()) {
			final String realName;
			final Module module;
			if (moduleName.charAt(0) == '%') {
				realName = moduleName.substring(1);
				module = new FlipFlopModule(realName);
			} else if (moduleName.charAt(0) == '&') {
				realName = moduleName.substring(1);
				module = new ConjunctionModule(realName, ascending.get(realName));
			} else if (BROADCASTER_MODULE_NAME.equals(moduleName)) {
				realName = BROADCASTER_MODULE_NAME;
				module = new BroadcasterModule(realName);
				broadcaster = module;
			} else {
				throw new IllegalArgumentException("Unknown module type: " + moduleName);
			}

			moduleByNames.put(realName, module);
		}

		final Map<Module, List<Module>> graph = new HashMap<>(lines.size());
		descending.forEach((moduleName, children) -> {
			final Module parent = moduleByNames.get(cleanName(moduleName));
			graph.put(parent, children.stream().map(name -> moduleByNames.getOrDefault(cleanName(name), new OutputModule(name))).toList());
		});

		if (broadcaster == null) throw new IllegalArgumentException("No broadcaster module found.");

		// Part 1
		long totalLowPulses = 0L;
		long totalHighPulses = 0L;
		for (int i = 0; i < 1000; i++) {
			final int[] pulses = pushButton(broadcaster, graph);
			totalLowPulses += pulses[0];
			totalHighPulses += pulses[1];
		}

		System.out.println(totalLowPulses * totalHighPulses);
	}

	private static String cleanName(final String name) {
		final char c = name.charAt(0);
		if (c == '%' || c == '&') return name.substring(1);
		return name;
	}

	private static int[] pushButton(final Module broadcaster, final Map<Module, List<Module>> graph) {
		final Deque<Pulse> queue = new ArrayDeque<>();
		queue.add(new Pulse(null, broadcaster, false));

		int lowPulseCount = 0;
		int highPulseCount = 0;
		while (!queue.isEmpty()) {
			final Pulse pulse = queue.pop();
			if (pulse.signal) {
				highPulseCount++;
			} else {
				lowPulseCount++;
			}
			final Module source = pulse.source;
			final Module current = pulse.destination;
			current.onSignalReceive(source == null ? null : source.m_name, pulse.signal)
					.ifPresent(signal -> graph.get(current).forEach(child -> queue.add(new Pulse(current, child, signal))));
		}
		return new int[] {lowPulseCount, highPulseCount};
	}

	private record Pulse(Module source, Module destination, boolean signal) {}

	private abstract static class Module {
		final String m_name;

		Module(final String name) {
			m_name = name;
		}

		// False = low signal, True = high signal
		protected abstract Optional<Boolean> onSignalReceive(final String sender, final boolean signal);
	}

	private static class FlipFlopModule extends Module {
		private boolean m_state;

		private FlipFlopModule(final String name) {
			super(name);
			m_state = false;
		}

		@Override
		public Optional<Boolean> onSignalReceive(final String sender, final boolean signal) {
			if (signal) return Optional.empty();
			m_state = !m_state;
			return Optional.of(m_state);
		}

		@Override
		public String toString() {
			return "FlipFlop{" + m_name + '/' + m_state + '}';
		}
	}

	private static class ConjunctionModule extends Module {
		private final Map<String, Boolean> m_lastReceivedSignal;

		private ConjunctionModule(final String name, final List<String> inputs) {
			super(name);
			m_lastReceivedSignal = new HashMap<>(inputs.size());
			inputs.forEach(input -> m_lastReceivedSignal.put(cleanName(input), false));
		}

		@Override
		public Optional<Boolean> onSignalReceive(final String sender, final boolean signal) {
			m_lastReceivedSignal.put(sender, signal);
			final boolean allInputsHigh = m_lastReceivedSignal.values().stream().allMatch(Boolean::booleanValue);
			return Optional.of(!allInputsHigh);
		}

		@Override
		public String toString() {
			return "Conjunction{" + m_name + '/' + m_lastReceivedSignal + '}';
		}
	}

	private static class BroadcasterModule extends Module {
		private BroadcasterModule(final String name) {
			super(name);
		}

		@Override
		public Optional<Boolean> onSignalReceive(final String sender, final boolean signal) {
			return Optional.of(signal);
		}

		@Override
		public String toString() {
			return "Broadcaster";
		}
	}

	private static class OutputModule extends Module {
		private OutputModule(final String name) {
			super(name);
		}

		@Override
		protected Optional<Boolean> onSignalReceive(final String sender, final boolean signal) {
			return Optional.empty();
		}

		@Override
		public String toString() {
			return "Output{" + m_name + '}';
		}
	}
}
