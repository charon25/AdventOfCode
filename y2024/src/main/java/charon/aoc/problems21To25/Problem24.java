package charon.aoc.problems21To25;

import charon.aoc.FileUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Problem24 {

	private static final Pattern GATE_PATTERN = Pattern.compile("(\\w{3}) (AND|OR|XOR) (\\w{3}) -> (\\w{3})");

	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(24);

		final Map<String, Wire> wiresByName = new HashMap<>();
		final Map<String, Gate> gatesByOutputName = new HashMap<>();
		final List<Gate> targetGates = new ArrayList<>();
		for (final String line : lines) {
			if (line.contains(":")) {
				final Wire wire = Wire.parse(line);
				wiresByName.put(wire.m_name, wire);
			} else if (line.contains("->")) {
				final Matcher matcher = GATE_PATTERN.matcher(line);
				matcher.find();
				final GateType gateType = GateType.valueOf(matcher.group(2));
				final Wire input1 = getOrCreateWire(wiresByName, matcher.group(1));
				final Wire input2 = getOrCreateWire(wiresByName, matcher.group(3));
				final Wire output = getOrCreateWire(wiresByName, matcher.group(4));
				final Gate gate = new Gate(gateType, input1, input2, output);
				gatesByOutputName.put(output.m_name, gate);
				if (output.m_name.charAt(0) == 'z') {
					targetGates.add(gate);
				}
			}
		}

		targetGates.sort(Comparator.comparing(gate -> ((Gate) gate).m_output.m_name).reversed());

		long number = 0L;
		for (final Gate gate : targetGates) {
			computeValue(createTree(gatesByOutputName, new Node(gate)));
			number = 2 * number + (gate.m_output.m_value ? 1 : 0);
		}

		System.out.println(number);
	}

	private static Node createTree(final Map<String, Gate> gatesByOutputName, final Node node) {
		if (node.m_gate.hasDefinedInputs()) {
			return node;
		}

		node.m_left = createTree(gatesByOutputName, new Node(gatesByOutputName.get(node.m_gate.m_input1.m_name)));
		node.m_right = createTree(gatesByOutputName, new Node(gatesByOutputName.get(node.m_gate.m_input2.m_name)));

		return node;
	}

	private static void computeValue(final Node node) {
		if (node.m_gate.hasValue()) return;

		if (node.m_gate.hasDefinedInputs()) {
			final boolean value = node.m_gate.compute();
			if (node.m_gate.m_output.m_value == null) {
				node.m_gate.m_output.setValue(value);
			}
			return;
		}

		computeValue(node.m_left);
		computeValue(node.m_right);
		computeValue(node);
	}

	private static Wire getOrCreateWire(final Map<String, Wire> wiresByName, final String name) {
		if (wiresByName.containsKey(name)) return wiresByName.get(name);

		final Wire wire = new Wire(name);
		wiresByName.put(name, wire);
		return wire;
	}

	private static class Wire {
		private final String m_name;
		private Boolean m_value;

		private Wire(final String name) {
			m_name = name;
		}

		private static Wire parse(final String string) {
			final String[] args = string.split(": ");
			final Wire wire = new Wire(args[0]);
			wire.setValue(args[1].charAt(0) == '1');
			return wire;
		}

		private void setValue(final Boolean value) {
			if (m_value != null) throw new IllegalArgumentException("Wire " + m_name + " already has a value!");
			m_value = value;
		}

		@Override
		public String toString() {
			return String.format("%s: %s", m_name, m_value);
		}
	}

	private static class Gate {
		private final GateType m_gateType;
		private final Wire m_input1;
		private final Wire m_input2;
		private final Wire m_output;

		private Gate(final GateType gateType, final Wire input1, final Wire input2, final Wire output) {
			m_gateType = gateType;
			m_input1 = input1;
			m_input2 = input2;
			m_output = output;
		}

		private boolean hasValue() {
			return m_output.m_value != null;
		}

		private boolean hasDefinedInputs() {
			return m_input1.m_value != null && m_input2.m_value != null;
		}

		private boolean compute() {
			return switch (m_gateType) {
				case AND -> m_input1.m_value && m_input2.m_value;
				case OR -> m_input1.m_value || m_input2.m_value;
				case XOR -> m_input1.m_value != m_input2.m_value;
			};
		}

		@Override
		public String toString() {
			return String.format("%s %s %s -> %s", m_input1.m_name, m_gateType, m_input2.m_name, m_output.m_name);
		}
	}

	private enum GateType {
		AND, OR, XOR
	}

	private static class Node {
		private final Gate m_gate;
		private Node m_left;
		private Node m_right;

		private Node(final Gate gate) {
			m_gate = gate;
		}

		@Override
		public String toString() {
			return "N(" + m_gate + ')';
		}
	}
}
