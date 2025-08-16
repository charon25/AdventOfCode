package charon.aoc.problems01To10;

import charon.aoc.FileUtils;
import charon.aoc.MathUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Problem07 {
	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(7);
		final List<Equation> equations = new ArrayList<>();
		for (final String line : lines) {
			final String[] parts = line.split(": ");
			final long result = Long.parseLong(parts[0]);
			final List<Integer> operands = Arrays.stream(parts[1].split(" ")).map(Integer::valueOf).collect(Collectors.toList());

			equations.add(new Equation(result, operands));
		}

		// Part 1

		long total1 = 0;
		for (final Equation equation : equations) {
			final int operatorsCount = equation.m_operands.size() - 1;
			final int limit = 1 << operatorsCount;
			for (int i = 0; i < limit; i++) {
				final StringBuilder sb = new StringBuilder().append(Integer.toBinaryString(i));
				if (sb.length() < operatorsCount) {
					sb.insert(0, "0".repeat(operatorsCount - sb.length()));
				}
				if (equation.isTrueWithTwoOperators(sb.toString())) {
					total1 += equation.m_result;
					break;
				}
			}
		}

		System.out.println(total1);

		// Part 2

		long total2 = 0;
		for (final Equation equation : equations) {
			final int operatorsCount = equation.m_operands.size() - 1;
			final long limit = MathUtils.pow(3, operatorsCount);
			for (int i = 0; i < limit; i++) {
				final StringBuilder sb = new StringBuilder().append(Integer.toString(i, 3));
				if (sb.length() < operatorsCount) {
					sb.insert(0, "0".repeat(operatorsCount - sb.length()));
				}
				if (equation.isTrueWithThreeOperators(sb.toString())) {
					total2 += equation.m_result;
					break;
				}
			}
		}

		System.out.println(total2);
	}

	private static class Equation {
		private final long m_result;
		private final List<Integer> m_operands;

		private Equation(final long result, final List<Integer> operands) {
			m_result = result;
			m_operands = operands;
		}

		private boolean isTrueWithTwoOperators(final String operators) {
			final int length = operators.length();
			long total = m_operands.get(0).longValue();
			for (int i = 0; i < length; i++) {
				final char operator = operators.charAt(i);
				if (operator == '0') {
					total += m_operands.get(i + 1);
				} else if (operator == '1') {
					total *= m_operands.get(i + 1);
				} else {
					throw new IllegalArgumentException("character other than 0 or 1 in the binary operators");
				}

				if (total > m_result) return false;
			}

			return total == m_result;
		}

		private boolean isTrueWithThreeOperators(final String operators) {
			final int length = operators.length();
			long total = m_operands.get(0).longValue();
			for (int i = 0; i < length; i++) {
				final char operator = operators.charAt(i);
				final int operand = m_operands.get(i + 1);
				if (operator == '0') {
					total += operand;
				} else if (operator == '1') {
					total *= operand;
				} else if (operator == '2') {
					long pow10 = 10;
					while (pow10 <= operand) {
						pow10 *= 10;
					}
					total = total * pow10 + operand;
				} else {
					throw new IllegalArgumentException("character other than 0, 1 or 2 in the ternary operators");
				}

				if (total > m_result) return false;
			}

			return total == m_result;
		}

		@Override
		public String toString() {
			return "Equation{" +
					"m_result=" + m_result +
					", m_operands=" + m_operands +
					'}';
		}
	}
}
