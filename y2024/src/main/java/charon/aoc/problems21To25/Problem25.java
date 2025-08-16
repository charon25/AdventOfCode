package charon.aoc.problems21To25;

import charon.aoc.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

public class Problem25 {

	private static final String LOCK = "#####";
	private static final String KEY = ".....";

	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(25);

		final List<int[]> locks = new ArrayList<>();
		final List<int[]> keys = new ArrayList<>();
		for (int i = 0; i < lines.size(); i += 8) {
			if (lines.get(i).equals(LOCK)) {
				final int[] lock = new int[5];
				for (int column = 0; column < lock.length; column++) {
					for (int row = 1; row < 7; row++) {
						if (lines.get(i + row).charAt(column) == '.') {
							lock[column] = row - 1;
							break;
						}
					}
				}
				locks.add(lock);
			} else if (lines.get(i).equals(KEY)) {
				final int[] key = new int[5];
				for (int column = 0; column < key.length; column++) {
					for (int row = 1; row < 7; row++) {
						if (lines.get(i + row).charAt(column) == '#') {
							key[column] = 5 - row + 1;
							break;
						}
					}
				}
				keys.add(key);
			} else {
				throw new IllegalArgumentException("Unknown pattern! " + lines.get(i));
			}
		}

		int count = 0;
		for (final int[] lock : locks) {
			for (final int[] key : keys) {
				if (keyFitsLock(lock, key)) count++;
			}
		}

		System.out.println(count);
	}

	private static boolean keyFitsLock(final int[] lock, final int[] key) {
		for (int i = 0; i < lock.length; i++) {
			if (lock[i] + key[i] > 5) return false;
		}
		return true;
	}
}
