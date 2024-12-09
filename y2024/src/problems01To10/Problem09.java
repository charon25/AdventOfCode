package problems01To10;

import utils.FileUtils;
import utils.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Problem09 {

	private static final int EMPTY = -1;

	public static void main(String[] args) {
		final String diskMap = FileUtils.readProblemInput(9).get(0);

		// Part 1

		final int[] disk1 = getDisk(diskMap).getFirst();

		int freeSpacePos = 0;
		for (int i = disk1.length - 1; i >= 0; i--) {
			if (disk1[i] == EMPTY) continue;
			while (disk1[freeSpacePos] != EMPTY && freeSpacePos < i) freeSpacePos++;
			if (freeSpacePos >= i) break;
			disk1[freeSpacePos] = disk1[i];
			disk1[i] = EMPTY;
		}

		System.out.println(getChecksum(disk1));

		// Part 2

		final Pair<int[], List<Pair<Integer, Integer>>> diskAndFiles = getDisk(diskMap);
		final int[] disk2 = diskAndFiles.getFirst();
		final List<Pair<Integer, Integer>> files = diskAndFiles.getSecond();

		for (int fileId = files.size() - 1; fileId >= 0; fileId--) {
			final int filePos = files.get(fileId).getFirst();
			final int fileSize = files.get(fileId).getSecond();

			for (int i = 0; i < filePos; i++) {
				if (startsWithNEmpty(disk2, i, fileSize)) {
					System.arraycopy(disk2, filePos, disk2, i, fileSize);
					Arrays.fill(disk2, filePos, filePos + fileSize, EMPTY);
					break;
				}
			}
		}

		System.out.println(getChecksum(disk2));
	}

	private static long getChecksum(final int[] disk1) {
		long total = 0L;
		for (int i = 0; i < disk1.length; i++) {
			if (disk1[i] == EMPTY) continue;
			total += (long) i * disk1[i];
		}
		return total;
	}

	private static Pair<int[], List<Pair<Integer, Integer>>> getDisk(final String diskMap) {
		final int diskSize = diskMap.chars().map(c -> c - '0').sum();

		final int[] disk1 = new int[diskSize];
		final List<Pair<Integer, Integer>> filesPos = new ArrayList<>();
		boolean freeSpace = false;
		int pointer = 0;
		int id = 0;
		for (int i = 0; i < diskMap.length(); i++) {
			final int blockSize = diskMap.charAt(i) - '0';

			if (!freeSpace) {
				filesPos.add(new Pair<>(pointer, blockSize));
			}
			for (int j = 0; j < blockSize; j++) {
				disk1[pointer] = (freeSpace ? EMPTY : id);
				pointer++;
			}

			if (!freeSpace) id++;

			freeSpace = !freeSpace;
		}

		return new Pair<>(disk1, filesPos);
	}

	private static boolean startsWithNEmpty(final int[] disk, final int position, final int length) {
		for (int i = position; i < position + length; i++) {
			if (disk[i] != EMPTY) return false;
		}

		return true;
	}
}
