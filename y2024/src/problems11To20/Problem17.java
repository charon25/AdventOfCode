package problems11To20;

import utils.FileUtils;

import java.util.*;
import java.util.stream.Collectors;

public class Problem17 {
	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(17);
		final Processor processor = new Processor(
				Long.parseLong(lines.get(0).split(": ")[1]),
				Long.parseLong(lines.get(1).split(": ")[1]),
				Long.parseLong(lines.get(2).split(": ")[1]),
				Arrays.stream(lines.get(4).split(": ")[1].split(",")).map(Integer::parseInt).collect(Collectors.toList())
		);

		// Part 1

		processor.run();

		System.out.println(processor.m_output.stream().map(String::valueOf).collect(Collectors.joining(",")));

		// Part 2

		final ArrayList<Integer> target = new ArrayList<>(processor.m_instructions);
		Collections.reverse(target);

		Set<Long> registerAs = new HashSet<>();
		registerAs.add(target.get(0).longValue());

		for (int i = 0; i < target.size(); i++) {
			final Set<Long> nextAs = new HashSet<>();
			for (final long a : registerAs) {
				for (long start = 8 * a; start < 8 * (a + 1); start++) {
					if (start == 0) continue;

					long nextA = start;
					long nextB = nextA % 8;
					nextB ^= 5;
					final long nextC = nextA / (1L << nextB);
					nextB ^= nextC;
					nextA /= 8;
					nextB ^= 6;
					if (nextB % 8 == target.get(i)) {
						nextAs.add(start);
					}
				}
			}
			registerAs = nextAs;
		}

		System.out.println(registerAs.stream().min(Long::compare).get());
	}

	private static class Processor {
		private long m_registerA;
		private long m_registerB;
		private long m_registerC;
		private final List<Integer> m_instructions;
		private int m_instructionPointer;
		private final List<Long> m_output = new ArrayList<>();

		private Processor(final long registerA, final long registerB, final long registerC, final List<Integer> instructions) {
			m_registerA = registerA;
			m_registerB = registerB;
			m_registerC = registerC;
			m_instructions = instructions;
			m_instructionPointer = 0;
		}

		private void run() {
			while (m_instructionPointer < m_instructions.size()) {
				final int instruction = m_instructions.get(m_instructionPointer);
				final int operand = m_instructions.get(m_instructionPointer + 1);
				if (executeInstruction(instruction, operand)) {
					m_instructionPointer += 2;
				}
			}
		}

		private boolean executeInstruction(final int instruction, final long operand) {
			switch (instruction) {
				case 0 -> m_registerA /= (1L << getComboOperand(operand));
				case 1 -> m_registerB ^= operand;
				case 2 -> m_registerB = getComboOperand(operand) % 8;
				case 3 -> {
					if (m_registerA != 0) {
						m_instructionPointer = (int) operand;
						return false;
					}
				}
				case 4 -> m_registerB ^= m_registerC;
				case 5 -> m_output.add(getComboOperand(operand) % 8);
				case 6 -> m_registerB = m_registerA / (1L << getComboOperand(operand));
				case 7 -> m_registerC = m_registerA / (1L << getComboOperand(operand));
			}

			return true;
		}

		private long getComboOperand(final long operand) {
			if (0 <= operand && operand <= 3) return operand;
			if (operand == 4) return m_registerA;
			if (operand == 5) return m_registerB;
			if (operand == 6) return m_registerC;
			throw new IllegalArgumentException("Unknown combo operand: " + operand);
		}

		@Override
		public String toString() {
			return String.format(
					"(A=%d, B=%d, C=%d, IP=%d ; instr=%s ; out=%s)",
					m_registerA, m_registerB, m_registerC, m_instructionPointer,
					m_instructions, m_output
			);
		}
	}
}
