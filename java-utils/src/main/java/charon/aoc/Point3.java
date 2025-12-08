package charon.aoc;

public record Point3(int x, int y, int z) {

	public static Point3 parse(final String string, final String delimiter) {
		final String[] parts = string.split(delimiter);
		if (parts.length != 3) throw new IllegalArgumentException("Cannot parse string \"" + string + '"');

		return new Point3(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
	}

	public long squaredDistance(final Point3 other) {
		final long deltaX = x - other.x;
		final long deltaY = y - other.y;
		final long deltaZ = z - other.z;
		return deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
	}

}
