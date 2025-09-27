package charon.aoc.problems11To20;

import charon.aoc.FileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Problem19 {

	private static final String STARTING_WORKFLOW = "in";
	private static final String ACCEPT = "A";
	private static final String REFUSE = "R";

	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(19);

		final Pattern rulePattern = Pattern.compile("(?:([xmas])([<>])(\\d+):)?(\\w+)");
		final Pattern partPattern = Pattern.compile("\\{x=(\\d+),m=(\\d+),a=(\\d+),s=(\\d+)}");

		final Map<String, Workflow> workflows = new HashMap<>();
		final List<Part> parts = new ArrayList<>();

		for (final String line : lines) {
			if (line.isBlank()) continue;
			if (line.startsWith("{")) {
				final Matcher matcher = partPattern.matcher(line);
				if (!matcher.find()) throw new IllegalArgumentException("Could not parse part: " + line);
				final Part part = new Part(new int[] {
						Integer.parseInt(matcher.group(1)),
						Integer.parseInt(matcher.group(2)),
						Integer.parseInt(matcher.group(3)),
						Integer.parseInt(matcher.group(4))
				});
				parts.add(part);
			} else {
				final String[] split = line.split("\\{");
				final String workflowName = split[0];
				final List<Rule> rules = new ArrayList<>();
				final Matcher matcher = rulePattern.matcher(split[1]);
				while (matcher.find()) {
					final Rule rule;
					if (matcher.group(1) != null) {
						final int index = Part.INDEX_BY_LETTERS.get(matcher.group(1));
						final boolean greater = ">".equals(matcher.group(2));
						final int value = Integer.parseInt(matcher.group(3));
						rule = new Rule(matcher.group(4), index, greater, value);
					} else {
						rule = Rule.defaultRule(matcher.group(4));
					}
					rules.add(rule);
				}
				workflows.put(workflowName, new Workflow(workflowName, rules));
			}
		}

		// Part 1
		final List<Part> acceptedParts = new ArrayList<>(parts.size());

		for (final Part part : parts) {
			Workflow workflow = workflows.get(STARTING_WORKFLOW);
			while (true) {
				final String destination = workflow.process(part);
				if (ACCEPT.equals(destination)) {
					acceptedParts.add(part);
					break;
				} else if (REFUSE.equals(destination)) {
					break;
				} else {
					workflow = workflows.get(destination);
				}
			}
		}

		final int totalRating = acceptedParts.stream().mapToInt(Part::totalRating).sum();
		System.out.println(totalRating);
	}

	private record Part(int[] ratings) {
		private static final Map<String, Integer> INDEX_BY_LETTERS = Map.of(
				"x", 0, "m", 1, "a", 2, "s", 3
		);

		private int totalRating() {
			int total = 0;
			for (final int rating : ratings) {
				total += rating;
			}
			return total;
		}
	}

	private record Workflow(String name, List<Rule> rules) {
		private String process(final Part part) {
			for (final Rule rule : rules) {
				if (rule.isValid(part)) return rule.destination;
			}

			throw new RuntimeException("Part " + part + " was not processed by " + this);
		}
	}

	private record Rule(String destination, int index, boolean greater, int value) {
		private static final int DEFAULT_INDEX = -1;

		private static Rule defaultRule(final String destination) {
			return new Rule(destination, DEFAULT_INDEX, false, 0);
		}

		private boolean isValid(final Part part) {
			if (index == DEFAULT_INDEX) return true;
			final int rating = part.ratings[index];
			return greater ? rating > value : rating < value;
		}
	}
}
