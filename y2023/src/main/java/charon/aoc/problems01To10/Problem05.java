package charon.aoc.problems01To10;

import charon.aoc.FileUtils;
import charon.aoc.Interval;
import charon.aoc.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Problem05 {
	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(5);

		final List<Long> seeds = StringUtils.parseLongList(lines.get(0).substring("seeds :".length()));
		final List<AlmanacMap> maps = new ArrayList<>(7);

		AlmanacMap currentMap = null;
		for (final String line : lines.subList(2, lines.size())) {
			if (line.isBlank()) continue;
			if (line.contains("map")) {
				if (currentMap != null) maps.add(currentMap);
				currentMap = new AlmanacMap(new ArrayList<>());
			} else if (StringUtils.isDigit(line.charAt(0))) {
				final List<Long> values = StringUtils.parseLongList(line);
				currentMap.ranges().add(new Range(values.get(0), values.get(1), values.get(2)));
			}
		}
		maps.add(currentMap);

		// Part 1
		long minLocation = Long.MAX_VALUE;
		for (final long seed : seeds) {
			long location = seed;
			for (final AlmanacMap map : maps) {
				location = map.map(location);
			}
			minLocation = Math.min(minLocation, location);
		}

		System.out.println(minLocation);

		// Part 2
		long minLocation2 = Long.MAX_VALUE;
		for (int i = 0; i < seeds.size(); i += 2) {
			List<Interval> intervals = List.of(new Interval(seeds.get(i), seeds.get(i) + seeds.get(i + 1)));
			for (final AlmanacMap map : maps) {
				intervals = splitIntervals(intervals, map.getAllRanges());
			}
			minLocation2 = Math.min(minLocation2, intervals.getFirst().start());
		}

		System.out.println(minLocation2);
	}

	private static List<Interval> splitIntervals(final List<Interval> intervals, final List<Range> ranges) {
		final List<Interval> newIntervals = new ArrayList<>(2 * intervals.size());

		for (final Interval interval : intervals) {
			long intervalStart = interval.start();
			for (final Range range : ranges) {
				if (interval.start() > range.sourceEnd()) continue;
				if (intervalStart >= interval.end()) break;
				newIntervals.add(new Interval(
						range.convert(Math.max(intervalStart, range.sourceStart)),
						range.convert(Math.min(interval.end(), range.sourceEnd()))
				));
				intervalStart = Math.min(interval.end(), range.sourceEnd());
			}
		}

		newIntervals.sort(Interval.BY_START);

		final List<Interval> mergedInterval = new ArrayList<>(newIntervals.size());
		mergedInterval.add(newIntervals.getFirst());
		for (int i = 1; i < newIntervals.size(); i++) {
			final Interval interval = newIntervals.get(i);
			final Interval previous = mergedInterval.getLast();
			if (interval.start() == previous.end()) {
				mergedInterval.removeLast();
				mergedInterval.add(new Interval(previous.start(), interval.end()));
			} else {
				mergedInterval.add(interval);
			}
		}

		return mergedInterval;
	}

	private record AlmanacMap(List<Range> ranges) {
		private long map(final long source) {
			for (final Range range : ranges) {
				if (range.contains(source)) {
					return range.convert(source);
				}
			}
			return source;
		}

		private List<Range> getAllRanges() {
			final List<Range> allRanges = new ArrayList<>(2 * ranges.size() + 2);
			allRanges.addAll(ranges);
			final List<Range> sortedRanges = ranges.stream()
					.sorted(Range.BY_SOURCE_START)
					.toList();

			if (sortedRanges.get(0).sourceStart > 0) {
				allRanges.add(new Range(0, 0, sortedRanges.get(0).sourceStart));
			}

			for (int i = 0; i < sortedRanges.size() - 1; i++) {
				final Range range = sortedRanges.get(i);
				final Range nextRange = sortedRanges.get(i + 1);

				final long rangeEnd = range.sourceEnd();
				if (rangeEnd < nextRange.sourceStart) {
					allRanges.add(new Range(rangeEnd, rangeEnd, nextRange.sourceStart - rangeEnd));
				}
			}

			final long lastRangeStart = ranges.stream().max(Range.BY_SOURCE_END).get().sourceEnd();
			allRanges.add(new Range(lastRangeStart, lastRangeStart, Long.MAX_VALUE - lastRangeStart));
			allRanges.sort(Range.BY_SOURCE_START);
			return allRanges;
		}
	}

	private record Range(long destinationStart, long sourceStart, long length) {
		private static final Comparator<Range> BY_SOURCE_START = Comparator.comparingLong(Range::sourceStart);
		private static final Comparator<Range> BY_SOURCE_END = Comparator.comparingLong(Range::sourceEnd);

		private boolean contains(final long source) {
			return sourceStart <= source && source < sourceStart + length;
		}

		private long convert(final long source) {
			return destinationStart + (source - sourceStart);
		}

		private long sourceEnd() {
			return sourceStart + length;
		}
	}
}
