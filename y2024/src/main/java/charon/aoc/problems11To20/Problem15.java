package charon.aoc.problems11To20;

import charon.aoc.FileUtils;
import charon.aoc.Point;
import charon.aoc.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class Problem15 {
	public static void main(String[] args) {
		final List<String> lines = FileUtils.readProblemInput(15);
		final int separation = getSeparation(lines);

		// Setup

		final int height = separation;
		final int width1 = lines.get(0).length();
		final Tile[][] terrain1 = new Tile[width1][height];
		final AtomicReference<Point> robotInitialPositionRef = new AtomicReference<>();
		StringUtils.forEachChar(lines.subList(0, separation), (x, y, c) -> {
			if (c == '#') {
				terrain1[x][y] = Tile.WALL;
			} else if (c == 'O') {
				terrain1[x][y] = Tile.BOX;
			} else if (c == '@') {
				robotInitialPositionRef.set(new Point(x, y));
				terrain1[x][y] = Tile.EMPTY;
			} else {
				terrain1[x][y] = Tile.EMPTY;
			}
		});

		final int width2 = 2 * width1;
		final Tile[][] terrain2 = new Tile[width2][height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width1; x++) {
				final Tile tile = terrain1[x][y];
				if (tile == Tile.BOX) {
					terrain2[2 * x][y] = Tile.LEFT_BOX;
					terrain2[2 * x + 1][y] = Tile.RIGHT_BOX;
				} else {
					terrain2[2 * x][y] = tile;
					terrain2[2 * x + 1][y] = tile;
				}
			}
		}

		if (robotInitialPositionRef.get() == null) throw new IllegalArgumentException("No initial position found for the robot!");
		final Point robotInitialPosition = robotInitialPositionRef.get();

		final List<Character> instructions = new ArrayList<>();
		for (int i = separation + 1; i < lines.size(); i++) {
			instructions.addAll(lines.get(i).chars().mapToObj(c -> (char) c).toList());
		}

		// Part 1

		Point robot1 = robotInitialPosition.copy();
		for (final Character instruction : instructions) {
			final Point vector = getMovementVector(instruction);
			final Point newRobot = robot1.add(vector);
			final Tile newTile = terrain1[newRobot.getX()][newRobot.getY()];
			if (newTile == Tile.EMPTY) {
				robot1 = newRobot;
			} else if (newTile == Tile.BOX) {
				boolean move = false;
				Point point = newRobot;
				while (true) {
					point = point.add(vector);
					final Tile tile = terrain1[point.getX()][point.getY()];
					if (tile == Tile.WALL) {
						break;
					} else if (tile == Tile.EMPTY) {
						move = true;
						break;
					}
				}

				if (move) {
					terrain1[point.getX()][point.getY()] = Tile.BOX;
					terrain1[newRobot.getX()][newRobot.getY()] = Tile.EMPTY;
					robot1 = newRobot;
				}
			}
		}

		System.out.println(getGPSCoordinates(terrain1, height, width1));

		// Part 2

		Point robot2 = new Point(robotInitialPosition.getX() * 2, robotInitialPosition.getY());
		for (final Character instruction : instructions) {
			final Point vector = getMovementVector(instruction);
			final Point newRobot = robot2.add(vector);
			final Tile newTile = terrain2[newRobot.getX()][newRobot.getY()];
			if (newTile == Tile.EMPTY) {
				robot2 = newRobot;
			} else if (newTile == Tile.LEFT_BOX || newTile == Tile.RIGHT_BOX) {
				if (vector.getY() == 0) {
					final List<Point> boxesToMove = new ArrayList<>();
					boxesToMove.add(newRobot);
					boolean move = false;
					Point point = newRobot;
					while (true) {
						point = point.add(vector);
						final Tile tile = terrain2[point.getX()][point.getY()];
						if (tile == Tile.WALL) {
							break;
						} else if (tile == Tile.EMPTY) {
							move = true;
							boxesToMove.add(point);
							break;
						}
						boxesToMove.add(point);
					}

					if (move) {
						for (int i = boxesToMove.size() - 1; i >= 1; i--) {
							final Point box = boxesToMove.get(i);
							final Point previousBox = boxesToMove.get(i - 1);
							terrain2[box.getX()][box.getY()] = terrain2[previousBox.getX()][previousBox.getY()];
						}
						terrain2[newRobot.getX()][newRobot.getY()] = Tile.EMPTY;
						robot2 = newRobot;
					}
				} else {
					final Set<Point> toCheck = new HashSet<>();
					toCheck.add(newRobot);
					if (newTile == Tile.LEFT_BOX) {
						toCheck.add(newRobot.add(RIGHT));
					} else {
						toCheck.add(newRobot.add(LEFT));
					}

					boolean move = false;
					final Set<Point> boxesToMove = new HashSet<>();
					while (true) {
						boxesToMove.addAll(toCheck);
						final List<Point> nextToCheck = new ArrayList<>();
						for (final Point point : toCheck) {
							nextToCheck.add(point.add(vector));
						}

						toCheck.clear();

						boolean allEmpty = true;
						boolean foundWall = false;
						for (int i = nextToCheck.size() - 1; i >= 0; i--) {
							final Point point = nextToCheck.get(i);
							final Tile tile = terrain2[point.getX()][point.getY()];
							if (tile == Tile.EMPTY) continue;

							toCheck.add(point);
							allEmpty = false;
							if (tile == Tile.WALL) {
								foundWall = true;
							} else if (tile == Tile.LEFT_BOX) {
								toCheck.add(point);
								toCheck.add(point.add(RIGHT));
							} else if (tile == Tile.RIGHT_BOX) {
								toCheck.add(point);
								toCheck.add(point.add(LEFT));
							}
						}

						if (allEmpty) {
							move = true;
							break;
						}
						if (foundWall) break;
					}

					if (move) {
						final List<Point> toMove = boxesToMove.stream()
								.sorted(vector.equals(UP) ? Point.Y_COMPARATOR : Point.Y_COMPARATOR.reversed())
								.toList();
						final int dy = vector.getY();
						for (int i = 0; i < toMove.size(); i++) {
							final Point box = toMove.get(i);
							terrain2[box.getX()][box.getY() + dy] = terrain2[box.getX()][box.getY()];
							terrain2[box.getX()][box.getY()] = Tile.EMPTY;
						}
						robot2 = newRobot;
					}
				}
			}
		}

		System.out.println(getGPSCoordinates(terrain2, height, width2));
	}

	private static int getGPSCoordinates(final Tile[][] terrain1, final int height, final int width1) {
		int total = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width1; x++) {
				if (terrain1[x][y] == Tile.BOX || terrain1[x][y] == Tile.LEFT_BOX) {
					total += (100 * y + x);
				}
			}
		}
		return total;
	}

	private static int getSeparation(final List<String> lines) {
		for (int i = 0; i < lines.size(); i++) {
			if (lines.get(i).isEmpty()) {
				return i;
			}
		}

		throw new IllegalArgumentException("No empty line found in input");
	}

	private static void print(final Tile[][] terrain, final Point robot) {
		final StringBuilder sb = new StringBuilder();
		for (int y = 0; y < terrain[0].length; y++) {
			for (int x = 0; x < terrain.length; x++) {
				if (robot.getX() == x && robot.getY() == y) {
					sb.append('@');
					continue;
				}
				final Tile tile = terrain[x][y];
				if (tile == Tile.EMPTY) {
					sb.append('.');
				} else if (tile == Tile.BOX) {
					sb.append('O');
				} else if (tile == Tile.WALL) {
					sb.append('#');
				} else if (tile == Tile.LEFT_BOX) {
					sb.append('[');
				} else if (tile == Tile.RIGHT_BOX) {
					sb.append(']');
				}
			}
			sb.append('\n');
		}
		sb.append('\n');

		System.out.println(sb);
	}

	private static final Point UP = new Point(0, -1);
	private static final Point RIGHT = new Point(1, 0);
	private static final Point DOWN = new Point(0, 1);
	private static final Point LEFT = new Point(-1, 0);

	private static Point getMovementVector(final char instruction) {
		switch (instruction) {
			case '^':
				return UP;
			case '>':
				return RIGHT;
			case 'v':
				return DOWN;
			case '<':
				return LEFT;
			default:
				throw new IllegalArgumentException("Unknown instruction: " + instruction);
		}
	}

	private enum Tile {
		WALL, BOX, EMPTY,
		LEFT_BOX, RIGHT_BOX
	}
}
