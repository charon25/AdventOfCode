package charon.aoc.problems1To10;

import charon.aoc.FileUtils;
import charon.aoc.IterUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Problem04 {
	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(4);

		final Pattern pattern = Pattern.compile("Card (\\d+): ([ \\d]+) \\| ([ \\d]+)");

		final List<Card> cards = new ArrayList<>(lines.size());
		for (final String line : lines) {
			final Matcher matcher = pattern.matcher(line.replaceAll("\\s+", " "));
			if (!matcher.find()) throw new IllegalArgumentException("Could not parse line");
			cards.add(new Card(
					Integer.parseInt(matcher.group(1)),
					parseNumberSet(matcher.group(2)),
					parseNumberSet(matcher.group(3))
			));
		}

		// Part 1
		int totalPoints = 0;
		for (final Card card : cards) {
			final int winningCount = card.getWinningCount();
			if (winningCount == 0) continue;
			totalPoints += (1 << (winningCount - 1));
		}

		System.out.println(totalPoints);

		// Part 2
		final int[] cardsCount = new int[cards.size()];
		Arrays.fill(cardsCount, 1);

		for (int i = 0; i < cards.size(); i++) {
			final int winningCount = cards.get(i).getWinningCount();
			for (int k = 0; k < winningCount; k++) {
				cardsCount[i + k + 1] += cardsCount[i];
			}
		}

		System.out.println(Arrays.stream(cardsCount).sum());
	}

	private static Set<Integer> parseNumberSet(final String string) {
		return Arrays.stream(string.split(" "))
				.map(Integer::parseInt)
				.collect(Collectors.toSet());
	}

	private record Card(int id, Set<Integer> winningNumbers, Set<Integer> numbers) {
		private int getWinningCount() {
			return IterUtils.intersection(winningNumbers, numbers).size();
		}
	}
}
