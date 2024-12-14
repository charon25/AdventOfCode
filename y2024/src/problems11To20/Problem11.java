package problems11To20;

import utils.FileUtils;
import utils.Pair;

import java.util.*;

public class Problem11 {
	public static void main(String[] args) {
		final List<Long> stones = Arrays.stream(FileUtils.readProblemInput(11).get(0).split(" "))
				.map(Long::parseLong)
				.toList();

		// Part 1

		List<Long> stonesTemp = new ArrayList<>(stones);
		for (int i = 0; i < 25; i++) {
			stonesTemp = blink(stonesTemp);
		}

		System.out.println(stonesTemp.size());

		// Part 2

		long count = 0;
		for (final Long stone : stones) {
			count += countFrom(stone, 75);
		}

		System.out.println(count);
	}

	private static List<Long> blink(final List<Long> stones) {
		final List<Long> newStones = new ArrayList<>(stones.size());
		for (int i = 0; i < stones.size(); i++) {
			final long stone = stones.get(i);
			if (stone == 0) {
				newStones.add(1L);
				continue;
			}
			final String stoneString = Long.toString(stone);

			if (stoneString.length() % 2 == 0) {
				newStones.add(Long.parseLong(stoneString.substring(0, stoneString.length() / 2)));
				newStones.add(Long.parseLong(stoneString.substring(stoneString.length() / 2)));
			} else {
				newStones.add(stone * 2024);
			}
		}

		return newStones;
	}

	private static final Map<Pair<Long, Integer>, Long> COUNT_CACHE = new HashMap<>();

	private static long countFrom(final long stone, final int count) {
		final Pair<Long, Integer> key = new Pair<>(stone, count);
		if (COUNT_CACHE.containsKey(key)) {
			return COUNT_CACHE.get(key);
		}

		final long result;
		if (count == 0) {
			result = 1;
		} else if (stone == 0) {
			result = countFrom(1, count - 1);
		} else {
			final String stoneString = Long.toString(stone);
			if (stoneString.length() % 2 == 0) {
				result = countFrom(Long.parseLong(stoneString.substring(0, stoneString.length() / 2)), count - 1)
						+ countFrom(Long.parseLong(stoneString.substring(stoneString.length() / 2)), count - 1);
			} else {
				result = countFrom(2024 * stone, count - 1);
			}
		}

		COUNT_CACHE.put(key, result);
		return result;
	}
}
