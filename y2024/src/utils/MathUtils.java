package utils;

public final class MathUtils {
	private MathUtils() {}

	public static long pow(final int base, final int exponent) {
		if (exponent == 0) {
			if (base == 0) throw new IllegalArgumentException("0^0 cannot be computed");
			return 1;
		}
		if (base == 1) return 1;

		long result = base;
		for (int i = 1; i < exponent; i++) {
			result *= base;
		}
		return result;
	}
}
