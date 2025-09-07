package charon.aoc.problems01To10;

import charon.aoc.FileUtils;
import charon.aoc.MathUtils;
import charon.aoc.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Problem08 {

	private static final String START = "AAA";
	private static final String END = "ZZZ";

	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(8);

		final String instructions = lines.get(0);

		final Pattern pattern = Pattern.compile("([A-Z]{3}) = \\(([A-Z]{3}), ([A-Z]{3})\\)");
		final Map<String, Node> network = new HashMap<>(lines.size());
		for (int i = 2; i < lines.size(); i++) {
			final Matcher matcher = pattern.matcher(lines.get(i));
			if (!matcher.find()) throw new IllegalArgumentException("Could not parse line \"" + lines.get(i) + '"');
			network.put(matcher.group(1), new Node(matcher.group(2), matcher.group(3)));
		}

		// Part 1
		System.out.println(solvePart1(instructions, network));

		// Part 2
		System.out.println(solvePart2(instructions, network));
	}

	private static int solvePart1(final String instructions, final Map<String, Node> network) {
		String position = START;
		int instructionIndex = 0;
		int steps = 0;
		while (!END.equals(position)) {
			final Node node = network.get(position);
			position = instructions.charAt(instructionIndex) == 'L' ? node.left : node.right;
			instructionIndex = (instructionIndex + 1) % instructions.length();
			steps++;
		}

		return steps;
	}

	private static long solvePart2(final String instructions, final Map<String, Node> network) {
		final List<String> positions = network.keySet().stream()
				.filter(p -> StringUtils.lastChar(p) == 'A')
				.toList();
		final List<Integer> stepsToZs = new ArrayList<>(positions.size());
		for (int i = 0; i < positions.size(); i++) {
			String position = positions.get(i);
			int instructionIndex = 0;
			int steps = 0;
			do {
				final Node node = network.get(position);
				position = instructions.charAt(instructionIndex) == 'L' ? node.left : node.right;
				instructionIndex = (instructionIndex + 1) % instructions.length();
				steps++;
			} while (StringUtils.lastChar(position) != 'Z');
			stepsToZs.add(steps);
		}

		return stepsToZs.stream().mapToLong(n -> (long) n).reduce(MathUtils::lcm).getAsLong();
	}

	private record Node(String left, String right) {}
}
