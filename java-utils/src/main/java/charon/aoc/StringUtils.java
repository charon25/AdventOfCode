package charon.aoc;

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


	@FunctionalInterface
	public interface CharConsumer {
		void accept(final int x, final int y, final char c);
	}
}
