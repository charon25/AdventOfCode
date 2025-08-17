package charon.aoc;

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

	public static boolean isPerfectSquare(final int n) {
		if (n < 0) return false;
		if (n <= 1) return true;
		int left = 1;
		int right = n / 2;
		while (left <= right) {
			final long mid = (left + right) / 2;
			final long square = mid * mid;
			if (square <= n) {
				left = (int) (mid + 1);
			} else {
				right = (int) (mid - 1);
			}
		}
		return right * right == n;
	}
}
