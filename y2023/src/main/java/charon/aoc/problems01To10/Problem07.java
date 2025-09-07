package charon.aoc.problems01To10;

import charon.aoc.FileUtils;
import charon.aoc.IterUtils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.LongStream;

public class Problem07 {

	private static final List<Character> CARDS1 = List.of(
			'A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2'
	);

	private static final List<Character> CARDS2 = List.of(
			'A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J'
	);

	private static final int FIVE_OF_A_KIND = 0;
	private static final int FOUR_OF_A_KIND = 1;
	private static final int FULL_HOUSE = 2;
	private static final int THREE_OF_A_KIND = 3;
	private static final int TWO_PAIRS = 4;
	private static final int ONE_PAIR = 5;
	private static final int HIGH_CARD = 6;

	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(7);

		final List<Hand1> hands = lines.stream()
				.map(line -> {
					final String[] parts = line.split(" ");
					return new Hand1(parts[0], Integer.parseInt(parts[1]));
				})
				.toList();

		// Part 1
		final List<Hand1> sortedHands1 = hands.stream().sorted(Hand.COMPARATOR).toList();
		System.out.println(getTotalWinnings(sortedHands1));

		// Part 2
		final List<Hand2> sortedHands2 = hands.stream()
				.map(hand1 -> new Hand2(hand1.cards, hand1.bid()))
				.sorted(Hand.COMPARATOR).toList();
		System.out.println(getTotalWinnings(sortedHands2));

	}

	private static long getTotalWinnings(final List<? extends Hand> sortedHands) {
		final int handsCount = sortedHands.size();
		return LongStream.range(0, handsCount)
				.map(k -> sortedHands.get((int) k).bid() * (handsCount - k))
				.sum();
	}

	private interface Hand {
		Comparator<Hand> FIRST_RULE = Comparator.comparingInt(Hand::getHandType);
		Comparator<Hand> SECOND_RULE = Comparator
				.<Hand>comparingInt(hand -> hand.getCardRank(0))
				.thenComparingInt(hand -> hand.getCardRank(1))
				.thenComparingInt(hand -> hand.getCardRank(2))
				.thenComparingInt(hand -> hand.getCardRank(3))
				.thenComparingInt(hand -> hand.getCardRank(4));

		Comparator<Hand> COMPARATOR = FIRST_RULE.thenComparing(SECOND_RULE);

		int getCardRank(final int index);
		int getHandType();
		int bid();
	}

	private record Hand1(String cards, int bid) implements Hand {

		@Override
		public int getCardRank(final int index) {
			return CARDS1.indexOf(cards.charAt(index));
		}

		@Override
		public int getHandType() {
			final Map<Character, Integer> quantities = new HashMap<>(cards.length());
			for (int i = 0; i < cards.length(); i++) {
				quantities.merge(cards.charAt(i), 1, Integer::sum);
			}
			final List<Integer> counts = quantities.values().stream()
					.sorted(Comparator.<Integer>comparingInt(n -> n).reversed())
					.toList();

			return switch (counts.get(0)) {
				case 5 -> FIVE_OF_A_KIND;
				case 4 -> FOUR_OF_A_KIND;
				case 3 -> counts.get(1) == 2 ? FULL_HOUSE : THREE_OF_A_KIND;
				case 2 -> counts.get(1) == 2 ? TWO_PAIRS : ONE_PAIR;
				case 1 -> HIGH_CARD;
				default -> throw new IllegalArgumentException("Invalid cards \"" + cards + '"');
			};
		}
	}

	private record Hand2(String cards, int bid) implements Hand {

		@Override
		public int getCardRank(final int index) {
			return CARDS2.indexOf(cards.charAt(index));
		}

		@Override
		public int getHandType() {
			final Map<Character, Integer> quantities = new HashMap<>(cards.length());
			for (int i = 0; i < cards.length(); i++) {
				quantities.merge(cards.charAt(i), 1, Integer::sum);
			}
			final int jokers = quantities.getOrDefault('J', 0);
			quantities.remove('J');
			final List<Integer> counts = quantities.values().stream()
					.sorted(Comparator.<Integer>comparingInt(n -> n).reversed())
					.toList();

			return switch (IterUtils.getOrDefault(counts, 0, 0) + jokers) {
				case 5 -> FIVE_OF_A_KIND;
				case 4 -> FOUR_OF_A_KIND;
				case 3 -> IterUtils.getOrDefault(counts, 1, 0) == 2 ? FULL_HOUSE : THREE_OF_A_KIND;
				case 2 -> IterUtils.getOrDefault(counts, 1, 0) == 2 ? TWO_PAIRS : ONE_PAIR;
				case 1 -> HIGH_CARD;
				default -> throw new IllegalArgumentException("Invalid cards \"" + cards + '"');
			};
		}
	}
}
