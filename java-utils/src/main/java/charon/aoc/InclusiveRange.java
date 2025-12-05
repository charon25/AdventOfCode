package charon.aoc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public record InclusiveRange(long start, long end) {

	/**
	 * Parse a string in the format "start-end".
	 */
	public static InclusiveRange parse(final String string) {
		final String[] parts = string.split("-");
		if (parts.length != 2) throw new IllegalArgumentException("Could not parse string \"" + string + '"');
		return new InclusiveRange(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
	}

	public boolean isEmpty() {
		return end < start;
	}

	public boolean contains(final long value) {
		if (isEmpty()) return false;
		return start <= value && value <= end;
	}

	public long size() {
		if (isEmpty()) return 0;
		return end - start + 1L;
	}

	public boolean isDisjoint(final InclusiveRange other) {
		if (isEmpty() || other.isEmpty()) return true;
		return start > other.end || end < other.start;
	}

	/**
	 * This does not return empty ranges.
	 */
	public Collection<InclusiveRange> difference(final InclusiveRange other) {
		if (isEmpty()) return List.of();
		if (isDisjoint(other)) return List.of(this);

		final List<InclusiveRange> parts = new ArrayList<>(2);

		final InclusiveRange first = new InclusiveRange(start, other.start - 1);
		if (!first.isEmpty()) parts.add(first);

		final InclusiveRange second = new InclusiveRange(other.end + 1, end);
		if (!second.isEmpty()) parts.add(second);

		return parts;
	}

}
