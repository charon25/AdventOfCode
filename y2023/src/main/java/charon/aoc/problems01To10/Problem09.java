package charon.aoc.problems01To10;

import charon.aoc.FileUtils;
import charon.aoc.IterUtils;
import charon.aoc.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Problem09 {
	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(9);

		final List<List<Long>> lists = lines.stream()
				.map(StringUtils::parseLongList)
				.toList();

		// Part 1 & 2
		long total1 = 0L;
		long total2 = 0L;
		for (final List<Long> list : lists) {
			final List<List<Long>> subLists = new ArrayList<>(list.size() + 1);
			List<Long> subList = new ArrayList<>(list);
			subLists.add(subList);
			while (!IterUtils.isConstant(subList, 0L)) {
				final List<Long> diffs = new ArrayList<>(subList.size());
				for (int i = 0; i < subList.size() - 1; i++) {
					diffs.add(subList.get(i + 1) - subList.get(i));
				}
				subList = diffs;
				subLists.add(subList);
			}

			subLists.getLast().add(0L);
			for (int i = subLists.size() - 2; i >= 0; i--) {
				subLists.get(i).add(subLists.get(i).getLast() + subLists.get(i + 1).getLast());
			}

			total1 += subLists.getFirst().getLast();

			subLists.getLast().add(0, 0L);
			for (int i = subLists.size() - 2; i >= 0; i--) {
				subLists.get(i).add(0, subLists.get(i).getFirst() - subLists.get(i + 1).getFirst());
			}

			total2 += subLists.getFirst().getFirst();
		}

		System.out.println(total1);
		System.out.println(total2);
	}
}
