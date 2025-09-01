package charon.aoc;

import java.util.Arrays;
import java.util.List;

public final class StringUtils {
	private StringUtils() {}

	public static void forEachChar(final List<String> lines, final CharConsumer consumer) {
		final int height = lines.size();
		for (int y = 0; y < height; y++) {
			final String line = lines.get(y);
			final int lineWidth = line.length();
			for (int x = 0; x < lineWidth; x++) {
				consumer.accept(x, y, line.charAt(x));
			}
		}
	}

	public static boolean isDigit(final char character) {
		return '0' <= character && character <= '9';
	}

	public static List<Long> parseLongList(final String string) {
		return Arrays.stream(string.strip().split(" "))
				.map(Long::parseLong)
				.toList();
	}

	public static List<Integer> parseIntList(final String string) {
		return parseIntList(string, " ");
	}

	public static List<Integer> parseIntList(final String string, final String delimiter) {
		return Arrays.stream(string.strip().split(delimiter))
				.map(Integer::parseInt)
				.toList();
	}

	public static char lastChar(final String string) {
		if (string.isEmpty()) throw new IndexOutOfBoundsException("string is empty");
		return string.charAt(string.length() - 1);
	}


	@FunctionalInterface
	public interface CharConsumer {
		void accept(final int x, final int y, final char c);
	}
}
