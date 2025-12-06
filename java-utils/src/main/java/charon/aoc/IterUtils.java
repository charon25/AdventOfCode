package charon.aoc;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

public final class IterUtils {
	private IterUtils() {}

	/**
	 * Run the given action on each pair of the given objects, where order does not matter, and an object cannot be paired with itself.
	 * Exemple, if there are 4 objects, it will be run on : <br>
	 * (1, 0), (2, 0), (2, 1), (3, 0), (3, 1), (3, 2)
	 */
	public static <T> void forEachUnorderedPair(final List<T> objects, final BiConsumer<T, T> action) {
		final int count = objects.size();
		for (int i = 0; i < count; i++) {
			for (int j = 0; j < i; j++) {
				action.accept(objects.get(i), objects.get(j));
			}
		}
	}

	public static <K, V> void add(final Map<K, List<V>> map, final K key, final V value) {
		map.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
	}

	public static <T> T getLast(final List<T> list) {
		return list.get(list.size() - 1);
	}

	public static <T> Set<T> union(final Set<T> set, final T element) {
		final HashSet<T> union = new HashSet<>(set);
		union.add(element);
		return union;
	}

	public static <T> Set<T> intersection(final Set<T> set, final Collection<T> elements) {
		final HashSet<T> intersection = new HashSet<>(Math.min(set.size(), elements.size()));
		for (final T element : elements) {
			if (set.contains(element)) {
				intersection.add(element);
			}
		}
		return intersection;
	}

	public static <T> T getOrDefault(final List<T> list, final int index, final T defaultValue) {
		if (index < 0) return defaultValue;
		if (index >= list.size()) return defaultValue;
		return list.get(index);
	}

	public static <T> boolean isConstant(final List<T> list, final T value) {
		for (final T v : list) {
			if (!Objects.equals(v, value)) return false;
		}

		return true;
	}

	public static <T> void print2DArray(final T[][] array, final Function<T, Character> printer) {
		final int height = array.length;
		final int width = array[0].length;
		final StringBuilder sb = new StringBuilder(height * (width + 1));

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				sb.append(printer.apply(array[y][x]));
			}
			sb.append('\n');
		}

		System.out.println(sb);
	}

	public static char[][] transpose(final char[][] array) {
		final int height = array.length;
		final int width = array[0].length;

		final char[][] transposed = new char[width][height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				transposed[x][y] = array[y][x];
			}
		}
		return transposed;
	}
}
