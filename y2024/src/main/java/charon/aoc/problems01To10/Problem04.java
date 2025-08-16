package charon.aoc.problems01To10;

import charon.aoc.utils.FileUtils;
import charon.aoc.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Problem04 {
	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(4);

		// Part 1

		System.out.println(getHorizontalCount(lines) + getVerticalCount(lines) + getDiagonalCount(lines));

		// Part 2

		System.out.println(getX_MasesCount(lines));
	}

	private static int getHorizontalCount(final List<String> lines) {
		int count = 0;

		for (final String line : lines) {
			count += (line.length() - line.replace("XMAS", "").length()) / 4;
			count += (line.length() - line.replace("SAMX", "").length()) / 4;
		}

		return count;
	}

	private static int getVerticalCount(final List<String> lines) {
		return getHorizontalCount(Utils.transpose(lines));
	}

	private static int getDiagonalCount(final List<String> lines) {
		final int linesCount = lines.size();
		final List<String> bottomOffsetLines = new ArrayList<>();
		for (int i = 0; i < linesCount; i++) {
			bottomOffsetLines.add(new StringBuilder()
					.append(" ".repeat(i))
					.append(lines.get(i))
					.append(" ".repeat(linesCount - i - 1))
					.toString());
		}

		final List<String> topOffsetLines = new ArrayList<>();
		for (int i = 0; i < linesCount; i++) {
			topOffsetLines.add(new StringBuilder()
					.append(" ".repeat(linesCount - i - 1))
					.append(lines.get(i))
					.append(" ".repeat(i))
					.toString());
		}

		return getVerticalCount(bottomOffsetLines) + getVerticalCount(topOffsetLines);
	}

	private static int getX_MasesCount(final List<String> lines) {
		final int rowCount = lines.size();
		final int colCount = lines.get(0).length();

		int count = 0;
		for (int i = 0; i < rowCount - 2; i++) {
			for (int j = 0; j < colCount - 2; j++) {
				if (lines.get(i + 1).charAt(j + 1) != 'A') continue;

				final char topLeft = lines.get(i).charAt(j);
				final char topRight = lines.get(i).charAt(j + 2);
				final char bottomLeft = lines.get(i + 2).charAt(j);
				final char bottomRight = lines.get(i + 2).charAt(j + 2);

				if (!(topLeft == 'M' && bottomRight == 'S' || topLeft == 'S' && bottomRight == 'M')) continue;
				if (!(topRight == 'M' && bottomLeft == 'S' || topRight == 'S' && bottomLeft == 'M')) continue;
				count++;
			}
		}

		return count;
	}
}
