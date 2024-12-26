package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

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
}
