package charon.aoc.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class FileUtils {
	private FileUtils() {
	}

	public static List<String> readProblemInput(final int problemId) {
		final String problemFileName = String.format("problem%02d.txt", problemId);
		try (final InputStream resource = FileUtils.class.getClassLoader().getResourceAsStream(problemFileName)) {
			if (resource == null) {
				throw new FileNotFoundException("Could not find input file for problem " + problemId);
			}

			final BufferedReader reader = new BufferedReader(new InputStreamReader(resource));
			final List<String> lines = new ArrayList<>();
			String line;

			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}

			return lines;
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}
}
