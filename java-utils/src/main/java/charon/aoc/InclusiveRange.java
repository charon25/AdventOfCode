package charon.aoc;

public record InclusiveRange(long start, long end) {

	public InclusiveRange {
		if (end < start) throw new IllegalArgumentException("End cannot be before start: " + start + ", " + end);
	}

	/**
	 * Parse a string in the format "start-end".
	 */
	public static InclusiveRange parse(final String string) {
		final String[] parts = string.split("-");
		if (parts.length != 2) throw new IllegalArgumentException("Could not parse string \"" + string + '"');
		return new InclusiveRange(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
	}

	public boolean contains(final long value) {
		return start <= value && value <= end;
	}

	public long size() {
		return end - start + 1L;
	}
}
