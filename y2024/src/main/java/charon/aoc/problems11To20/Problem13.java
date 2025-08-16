package charon.aoc.problems11To20;

import charon.aoc.utils.FileUtils;
import charon.aoc.utils.LongPoint;
import charon.aoc.utils.Triplet;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Problem13 {

	private static final Pattern BUTTON_PATTERN = Pattern.compile("X\\+(\\d+), Y\\+(\\d+)");
	private static final Pattern TARGET_PATTERN = Pattern.compile("X=(\\d+), Y=(\\d+)");

	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(13);
		final List<Triplet<LongPoint, LongPoint, LongPoint>> machines = new ArrayList<>();
		for (int i = 0; i < lines.size(); i += 4) {
			machines.add(new Triplet<>(
					getButton(lines.get(i)),
					getButton(lines.get(i + 1)),
					getTarget(lines.get(i + 2))
			));
		}

		// Part 1

		long totalTokens1 = 0;
		for (final Triplet<LongPoint, LongPoint, LongPoint> machine : machines) {
			final Optional<Long> tokens = getTokens(machine.getFirst(), machine.getSecond(), machine.getThird());
			if (tokens.isPresent()) {
				totalTokens1 += tokens.get();
			}
		}

		System.out.println(totalTokens1);

		// Part 2

		final LongPoint offset = new LongPoint(10000000000000L, 10000000000000L);

		final List<Triplet<LongPoint, LongPoint, LongPoint>> farAwayMachines = machines.stream()
				.map(machine -> new Triplet<>(machine.getFirst(), machine.getSecond(), machine.getThird().add(offset)))
				.collect(Collectors.toList());

		BigInteger totalTokens2 = BigInteger.ZERO;
		for (final Triplet<LongPoint, LongPoint, LongPoint> machine : farAwayMachines) {
			final Optional<Long> tokens = getTokens(machine.getFirst(), machine.getSecond(), machine.getThird());
			if (tokens.isPresent()) {
				totalTokens2 = totalTokens2.add(BigInteger.valueOf(tokens.get()));
			}
		}

		System.out.println(totalTokens2);
	}

	private static LongPoint getButton(final String line) {
		final Matcher matcher = BUTTON_PATTERN.matcher(line);
		if (!matcher.find()) throw new IllegalArgumentException("Cannot find button values in " + line);
		return new LongPoint(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
	}

	private static LongPoint getTarget(final String line) {
		final Matcher matcher = TARGET_PATTERN.matcher(line);
		if (!matcher.find()) throw new IllegalArgumentException("Cannot find target values in " + line);
		return new LongPoint(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
	}

	private static Optional<Long> getTokens(final LongPoint btnA, final LongPoint btnB, final LongPoint target) {
		final long determinant = btnA.getX() * btnB.getY() - btnB.getX() * btnA.getY();
		final long a = target.getX() * btnB.getY() - target.getY() * btnB.getX();
		final long b = -target.getX() * btnA.getY() + target.getY() * btnA.getX();

		if (a % determinant != 0 || b % determinant != 0) return Optional.empty();

		final long btnAPresses = a / determinant;
		final long btnBPresses = b / determinant;

		return Optional.of(3 * btnAPresses + btnBPresses);
	}
}
