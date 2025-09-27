package charon.aoc;

import java.util.Comparator;

public record Interval(long start, long end) {
	public static final Comparator<Interval> BY_START = Comparator.comparingLong(Interval::start);
}