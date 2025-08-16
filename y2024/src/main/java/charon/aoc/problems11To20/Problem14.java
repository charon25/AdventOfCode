package charon.aoc.problems11To20;

import charon.aoc.FileUtils;
import charon.aoc.Point;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Problem14 {
	private static final int WIDTH = 101;
	private static final int HEIGHT = 103;

	private static final Pattern ROBOT_PATTERN = Pattern.compile("p=([\\d-]+),([\\d-]+) v=([\\d-]+),([\\d-]+)");

	public static void main(String[] args) throws IOException {
		final List<String> lines = FileUtils.readProblemInput(14);
		final List<Robot> baseRobots = new ArrayList<>();
		for (final String line : lines) {
			final Matcher matcher = ROBOT_PATTERN.matcher(line);
			matcher.find();
			baseRobots.add(new Robot(
					new Point(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))),
					new Point(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)))
			));
		}

		// Part 1

		final List<Robot> robots1 = baseRobots.stream()
				.map(robot -> new Robot(robot.m_position, robot.m_velocity))
				.collect(Collectors.toList());
		for (int i = 0; i < 100; i++) {
			robots1.forEach(Robot::move);
		}

		final int[] robotCount = new int[4];
		for (final Robot robot : robots1) {
			final OptionalInt quadrant = getQuadrant(robot.m_position);
			if (quadrant.isPresent()) {
				robotCount[quadrant.getAsInt()]++;
			}
		}

		System.out.println(robotCount[0] * robotCount[1] * robotCount[2] * robotCount[3]);

		// Part 2
		// Explanation : as the Christmas tree pattern is more organized than a regular "uniform" repartition of robots,
		// a compressed image with this pattern will be slightly smaller

		final List<Robot> robots2 = baseRobots.stream()
				.map(robot -> new Robot(robot.m_position, robot.m_velocity))
				.collect(Collectors.toList());

		int minSize = Integer.MAX_VALUE;
		int minIndex = -1;
		for (int i = 0; i < 10000; i++) {
			final BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
			robots2.forEach(robot -> {
				bufferedImage.setRGB(robot.m_position.getX(), robot.m_position.getY(), 0xFFFFFF);
			});

			final ByteArrayOutputStream output = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "png", output);
			final int size = output.size();
			if (size < minSize) {
				minSize = size;
				minIndex = i;
			}

			robots2.forEach(Robot::move);
		}

		System.out.println(minIndex);
	}

	private static OptionalInt getQuadrant(final Point point) {
		if (point.getX() == WIDTH / 2 || point.getY() == HEIGHT / 2) return OptionalInt.empty();
		if (point.getX() < WIDTH / 2) {
			if (point.getY() < HEIGHT / 2) {
				return OptionalInt.of(0);
			} else {
				return OptionalInt.of(1);
			}
		} else {
			if (point.getY() < HEIGHT / 2) {
				return OptionalInt.of(2);
			} else {
				return OptionalInt.of(3);
			}
		}
	}

	private static final class Robot {
		private Point m_position;
		private final Point m_velocity;

		private Robot(final Point position, final Point velocity) {
			m_position = position;
			m_velocity = velocity;
		}

		private void move() {
			final int x = m_position.getX() + m_velocity.getX();
			final int y = m_position.getY() + m_velocity.getY();
			m_position = new Point(
					x < 0 ? x + WIDTH : (x >= WIDTH ? x % WIDTH : x),
					y < 0 ? y + HEIGHT : (y >= HEIGHT ? y % HEIGHT : y)
			);
		}

		@Override
		public String toString() {
			return "Robot{" +
					"m_position=" + m_position +
					", m_velocity=" + m_velocity +
					'}';
		}
	}
}
