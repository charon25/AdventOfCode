package charon.aoc.problems01To10;

import charon.aoc.FileUtils;
import charon.aoc.IterUtils;
import charon.aoc.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Problem06 {
	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(6);

		// Part 1
		System.out.println(solvePart1(lines));

		// Part 2
		System.out.println(solvePart2(lines));
	}

	private static long solvePart1(final List<String> lines) {
		final List<List<Long>> values = new ArrayList<>();
		final List<Operation> operations = new ArrayList<>();

		for (final String line : lines) {
			final String[] parts = line.trim().split("\\s+");
			if (StringUtils.isDigit(parts[0].charAt(0))) {
				values.add(Arrays.stream(parts).map(Long::parseLong).toList());
			} else {
				operations.addAll(Arrays.stream(parts).map(Operation::get).toList());
			}
		}

		final int width = values.get(0).size();
		final int height = values.size();
		final long[][] numbers = new long[width][height];
		for (int y = 0; y < height; y++) {
			final List<Long> row = values.get(y);
			for (int x = 0; x < row.size(); x++) {
				numbers[x][y] = row.get(x);
			}
		}

		return computeTotal(numbers, operations);
	}

	private static long solvePart2(final List<String> lines) {
		final String operationsLine = lines.getLast();
		final List<Operation> operations = new ArrayList<>();
		final List<Integer> indexes = new ArrayList<>();
		for (int i = 0; i < operationsLine.length(); i++) {
			final char c = operationsLine.charAt(i);
			if (c != ' ') {
				operations.add(Operation.get(c));
				indexes.add(i);
			}
		}
		indexes.add(operationsLine.length() + 1);

		final long[][] numbers = new long[indexes.size() - 1][];
		final int numbersLineCount = lines.size() - 1;
		for (int i = 0; i < indexes.size() - 1; i++) {
			final int start = indexes.get(i);
			final int end = indexes.get(i + 1) - 1;

			final char[][] block = new char[numbersLineCount][end - start];
			for (int k = 0; k < numbersLineCount; k++) {
				final String chunk = lines.get(k).substring(start, end);
				block[k] = chunk.toCharArray();
			}

			final char[][] transposedBlock = IterUtils.transpose(block);
			final long[] blockNumbers = new long[transposedBlock.length];
			for (int k = 0; k < transposedBlock.length; k++) {
				blockNumbers[k] = Long.parseLong(new String(transposedBlock[k]).trim());
			}

			numbers[i] = blockNumbers;
		}

		return computeTotal(numbers, operations);
	}

	private static long computeTotal(final long[][] numbers, final List<Operation> operations) {
		long total = 0L;
		for (int x = 0; x < numbers.length; x++) {
			final long[] row = numbers[x];

			long result = row[0];
			final Operation operation = operations.get(x);
			for (int y = 1; y < row.length; y++) {
				result = operation.compute(result, row[y]);
			}
			total += result;
		}
		return total;
	}

	private enum Operation implements OperationAction {
		ADDITION{
			@Override
			public long compute(final long a, final long b) {
				return a + b;
			}
		},
		MULTIPLICATION{
			@Override
			public long compute(final long a, final long b) {
				return a * b;
			}
		};

		private static Operation get(final String string) {
			return switch (string) {
				case "+" -> ADDITION;
				case "*" -> MULTIPLICATION;
				default -> throw new IllegalArgumentException("Invalid operation symbol: " + string);
			};
		}

		private static Operation get(final char c) {
			return switch (c) {
				case '+' -> ADDITION;
				case '*' -> MULTIPLICATION;
				default -> throw new IllegalArgumentException("Invalid operation symbol: " + c);
			};
		}
	}

	@FunctionalInterface
	private interface OperationAction {
		long compute(final long a, final long b);
	}
}
