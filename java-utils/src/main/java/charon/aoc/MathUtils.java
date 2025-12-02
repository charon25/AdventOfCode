package charon.aoc;

public final class MathUtils {
	private MathUtils() {}

	public static long pow(final int base, final int exponent) {
		if (exponent == 0) {
			if (base == 0) throw new IllegalArgumentException("0^0 cannot be computed");
			return 1;
		}
		if (base == 1) return 1;
		if (base == 2) return 1L << exponent;

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

	public static long gcd(long a, long b) {
		if (a == 0) return b;
		if (b == 0) return a;
		while (a > 0 && b > 0) {
			final long max = Math.max(a, b);
			final long min = Math.min(a, b);
			a = max % min;
			b = min;
		}
		return a + b;
	}

	public static long lcm(final long a, final long b) {
		return a * (b / gcd(a, b));
	}

	public static int mod(final int a, final int b) {
		int modulo = a % b;
		if (modulo < 0) {
			modulo += b;
		}
		return modulo;
	}
}
