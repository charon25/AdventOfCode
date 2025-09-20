package charon.aoc.problems11To20;

import charon.aoc.Direction;
import charon.aoc.FileUtils;
import charon.aoc.LongPoint;
import charon.aoc.Point;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Problem18 {

	private static final Map<String, Direction> DIRECTIONS_MAP = Map.of(
			"U", Direction.UP,
			"R", Direction.RIGHT,
			"D", Direction.DOWN,
			"L", Direction.LEFT,
			"3", Direction.UP,
			"0", Direction.RIGHT,
			"1", Direction.DOWN,
			"2", Direction.LEFT
	);

	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(18);

		final Pattern pattern = Pattern.compile("([URDL]) (\\d+) \\(#([0-9a-f]+)\\)");

		final List<Instruction> instructions = new ArrayList<>(lines.size());
		for (final String line : lines) {
			final Matcher matcher = pattern.matcher(line);
			if (!matcher.find()) throw new IllegalArgumentException("Unable to parse line: " + line);
			final Instruction instruction = new Instruction(DIRECTIONS_MAP.get(matcher.group(1)), Integer.parseInt(matcher.group(2)), matcher.group(3));
			instructions.add(instruction);
		}

		// Part 1
		final Set<Point> digged = new HashSet<>();
		int minX = 0;
		int minY = 0;
		int maxX = 0;
		int maxY = 0;

		Point point = new Point(0, 0);
		digged.add(point);

		for (final Instruction instruction : instructions) {
			for (int i = 0; i < instruction.length; i++) {
				point = point.add(instruction.direction.getVector());
				digged.add(point);
				minX = Math.min(minX, point.getX());
				maxX = Math.max(maxX, point.getX());
				minY = Math.min(minY, point.getY());
				maxY = Math.max(maxY, point.getY());
			}
		}

		final Optional<Point> inInterior = findPointInInterior(digged, minX, maxX, minY, maxY);
		if (inInterior.isEmpty()) throw new IllegalArgumentException("Could not find point inside shape.");

		fillInterior(digged, inInterior.get(), minX, maxX, minY, maxY);

		System.out.println(digged.size());

		// Part 2 (Pick's theorem once again)
		final List<LongPoint> vertices = new ArrayList<>(instructions.size() + 1);
		LongPoint vertex = new LongPoint(0L, 0L);
		vertices.add(vertex);
		long totalDigged = 0;
		for (final Instruction instruction : instructions) {
			final int distance = Integer.parseInt(instruction.color.substring(0, instruction.color.length() - 1), 16);
			totalDigged += distance;
			final String lastDigit = instruction.color.substring(instruction.color.length() - 1);
			vertex = vertex.add(DIRECTIONS_MAP.get(lastDigit).getVector().toLongPoint().times(distance));
			vertices.add(vertex);
		}

		vertices.removeLast();

		long area = vertices.getLast().getX() * vertices.getFirst().getY() - vertices.getLast().getY() * vertices.getFirst().getX();
		for (int i = 0; i < vertices.size() - 1; i++) {
			final LongPoint p1 = vertices.get(i);
			final LongPoint p2 = vertices.get(i + 1);
			area += (p1.getX() * p2.getY() - p1.getY() * p2.getX());
		}
		area = Math.abs(area) / 2;
		totalDigged += area - totalDigged / 2 + 1;

		System.out.println(totalDigged);

	}

	private static void fillInterior(final Set<Point> digged, final Point point, final int minX, final int maxX, final int minY, final int maxY) {
		final Deque<Point> queue = new ArrayDeque<>((maxX - minX) * (maxY - minY));
		queue.add(point);
		final Set<Point> visited = new HashSet<>();
		visited.add(point);
		while (!queue.isEmpty()) {
			final Point u = queue.pop();

			for (final Point v : u.getAdjacent4()) {
				if (digged.contains(v)) continue;
				if (visited.contains(v)) continue;

				visited.add(v);
				queue.add(v);
			}
		}

		digged.addAll(visited);
	}

	private static Optional<Point> findPointInInterior(final Set<Point> digged, final int minX, final int maxX, final int minY, final int maxY) {
		for (int y = minY + 1; y < maxY + 1; y++) {
			for (int x = minX + 1; x < maxX + 1; x++) {
				final Point point = new Point(x, y);
				if (isStrictlyInside(digged, point, maxX)) {
					return Optional.of(point);
				}
			}
		}

		return Optional.empty();
	}

	private static boolean isStrictlyInside(final Set<Point> digged, final Point point, final int maxX) {
		if (digged.contains(point)) return false;

		boolean inside = false;
		boolean inHole = false;
		for (int x = point.getX() + 1; x < maxX + 1; x++) {
			final Point p = new Point(x, point.getY());
			if (!inHole && digged.contains(p)) {
				inside = !inside;
				inHole = true;
			} else if (inHole && !digged.contains(p)) {
				inHole = false;
			}
		}
		return inside;
	}

	private record Instruction(Direction direction, int length, String color) {}
}
