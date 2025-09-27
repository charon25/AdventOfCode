package charon.aoc.problems11To20;

import charon.aoc.FileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Problem19 {

	public static final String STARTING_WORKFLOW = "in";
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
				final Part part = new Part(
						Integer.parseInt(matcher.group(1)),
						Integer.parseInt(matcher.group(2)),
						Integer.parseInt(matcher.group(3)),
						Integer.parseInt(matcher.group(4))
				);
				parts.add(part);
			} else {
				final String[] split = line.split("\\{");
				final String workflowName = split[0];
				final List<Rule> rules = new ArrayList<>();
				final Matcher matcher = rulePattern.matcher(split[1]);
				while (matcher.find()) {
					final Rule rule;
					if (matcher.group(1) != null) {
						final boolean greater = ">".equals(matcher.group(2));
						final int value = Integer.parseInt(matcher.group(3));
						final RuleValidator validator = switch (matcher.group(1)) {
							case "x" -> new XValidator(greater, value);
							case "m" -> new MValidator(greater, value);
							case "a" -> new AValidator(greater, value);
							case "s" -> new SValidator(greater, value);
							default -> throw new IllegalArgumentException("Invalid rule: " + line);
						};
						rule = new Rule(validator, matcher.group(4));
					} else {
						rule = new Rule(matcher.group(4));
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

	private record Part(int x, int m, int a, int s) {
		private int totalRating() {
			return x + m + a + s;
		}
	}

	private record Workflow(String name, List<Rule> rules) {
		private String process(final Part part) {
			for (final Rule rule : rules) {
				if (rule.validator.isValid(part)) return rule.destination;
			}

			throw new RuntimeException("Part " + part + " was not processed by " + this);
		}
	}

	private record Rule(RuleValidator validator, String destination) {
		private Rule(final String destination) {
			this(RuleValidator.ALL, destination);
		}
	}

	private record XValidator(boolean greater, int value) implements RuleValidator {
		@Override
		public boolean isValid(final Part part) {
			return greater ? part.x > value : part.x < value;
		}
	}

	private record MValidator(boolean greater, int value) implements RuleValidator {
		@Override
		public boolean isValid(final Part part) {
			return greater ? part.m > value : part.m < value;
		}
	}

	private record AValidator(boolean greater, int value) implements RuleValidator {
		@Override
		public boolean isValid(final Part part) {
			return greater ? part.a > value : part.a < value;
		}
	}

	private record SValidator(boolean greater, int value) implements RuleValidator {
		@Override
		public boolean isValid(final Part part) {
			return greater ? part.s > value : part.s < value;
		}
	}

	@FunctionalInterface
	private interface RuleValidator {
		RuleValidator ALL = part -> true;
		boolean isValid(final Part part);
	}
}
