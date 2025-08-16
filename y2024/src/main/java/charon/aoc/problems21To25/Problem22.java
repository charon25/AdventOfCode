package charon.aoc.problems21To25;

import charon.aoc.FileUtils;
import charon.aoc.IterUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Problem22 {

	private static final long PRUNE_VALUE = 0xFFFFFFL;

	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(22);

		final List<List<Long>> sequences = new ArrayList<>();
		for (final String line : lines) {
			long secret = Long.parseLong(line);
			final List<Long> sequence = new ArrayList<>();
			sequence.add(secret);
			for (int i = 0; i < 2000; i++) {
				secret = nextInSequence(secret);
				sequence.add(secret);
			}
			sequences.add(sequence);
		}

		// Part 1

		final long total = sequences.stream().mapToLong(IterUtils::getLast).sum();
		System.out.println(total);

		// Part 2

		final List<List<Integer>> prices = new ArrayList<>();
		final List<List<Integer>> priceDifferences = new ArrayList<>();
		for (final List<Long> sequence : sequences) {
			final List<Integer> sequencePrices = sequence.stream().map(n -> (int) (n % 10)).toList();
			final List<Integer> sequencePriceDifferences = new ArrayList<>();
			for (int i = 1; i < sequence.size(); i++) {
				sequencePriceDifferences.add(sequencePrices.get(i) - sequencePrices.get(i - 1));
			}
			prices.add(sequencePrices);
			priceDifferences.add(sequencePriceDifferences);
		}

		final List<Map<List<Integer>, Integer>> priceByDiffSeqByMonkey = new ArrayList<>();
		for (int monkeyIndex = 0; monkeyIndex < priceDifferences.size(); monkeyIndex++) {
			final List<Integer> monkeyPriceDiff = priceDifferences.get(monkeyIndex);
			final List<Integer> monkeyPrices = prices.get(monkeyIndex);
			final Map<List<Integer>, Integer> priceByDiffSeq = new HashMap<>();
			for (int priceIndex = 0; priceIndex < monkeyPriceDiff.size() - 3; priceIndex++) {
				final List<Integer> diffSeq = monkeyPriceDiff.subList(priceIndex, priceIndex + 4);
				if (!priceByDiffSeq.containsKey(diffSeq)) {
					priceByDiffSeq.put(diffSeq, monkeyPrices.get(1 + priceIndex + 3)); // The 1+ is because there is one less difference than prices
				}
			}
			priceByDiffSeqByMonkey.add(priceByDiffSeq);
		}

		final Map<List<Integer>, Integer> priceByDiffSeqForAllMonkeys = new HashMap<>();
		for (final Map<List<Integer>, Integer> m : priceByDiffSeqByMonkey) {;
			m.forEach((diffSeq, price) -> priceByDiffSeqForAllMonkeys.merge(diffSeq, price, Integer::sum));
		}

		System.out.println(priceByDiffSeqForAllMonkeys.values().stream().max(Integer::compare).get());
	}

	private static long nextInSequence(final long secret) {
		return multiply(divide(multiply(secret, 64), 32), 2048);
	}

	private static long multiply(final long secret, final long factor) {
		return (secret ^ (secret * factor)) & PRUNE_VALUE;
	}

	private static long divide(final long secret, final long factor) {
		return (secret ^ (secret / factor)) & PRUNE_VALUE;
	}
}
