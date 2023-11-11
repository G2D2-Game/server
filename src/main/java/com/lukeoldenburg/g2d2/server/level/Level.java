package com.lukeoldenburg.g2d2.server.level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Level {
	private static final Logger LOGGER = LoggerFactory.getLogger(Level.class);
	private final int size;
	private final String levelPath;
	private final long seed;
	private short[][] bgTiles;
	private short[][] fgTiles;
	private boolean loaded = false;

	public Level(String levelPath, int size, long seed) {
		this.levelPath = levelPath;
		this.size = size;
		this.seed = seed;
		this.bgTiles = new short[size][size];
		this.fgTiles = new short[size][size];
	}

	public int getSize() {
		return size;
	}

	public long getSeed() {
		return seed;
	}

	public boolean isLoaded() {
		return loaded;
	}

	public short[][] getBgTiles() {
		return bgTiles;
	}

	public short[][] getFgTiles() {
		return fgTiles;
	}

	public boolean loadLevel(BufferedInputStream bis) {
		try {
			GZIPInputStream gzis = new GZIPInputStream(bis);
			byte[] buffer = new byte[size * size * 2];

			gzis.read(buffer);
			for (int x = 0; x < size; x++) {
				for (int y = 0; y < size; y++) {
					int index = x * size + y;
					bgTiles[x][y] = (short) ((buffer[index * 2] << 8) | (buffer[index * 2 + 1] & 0xFF));
				}
			}

			gzis.read(buffer);
			for (int x = 0; x < size; x++) {
				for (int y = 0; y < size; y++) {
					int index = x * size + y;
					fgTiles[x][y] = (short) ((buffer[index * 2] << 8) | (buffer[index * 2 + 1] & 0xFF));
				}
			}

			gzis.close();
			loaded = true;
			LOGGER.info("Successfully loaded level");
			return true;

		} catch (IOException e) {
			LOGGER.error("Failed to load level", e);
			return false;
		}
	}

	public boolean saveLevel() {
		try {
			FileOutputStream fos = new FileOutputStream(levelPath);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			LOGGER.info("Successfully saved level to {}", levelPath);
			return saveLevel(bos);

		} catch (FileNotFoundException e) {
			LOGGER.error("Failed to save level", e);
			return false;
		}
	}

	public boolean saveLevel(BufferedOutputStream bos) {
		try {
			GZIPOutputStream gzos = new GZIPOutputStream(bos);

			// Write bgTiles
			for (int x = 0; x < size; x++) {
				for (int y = 0; y < size; y++) {
					short value = bgTiles[x][y];
					gzos.write((value >> 8) & 0xFF);
					gzos.write(value & 0xFF);
				}
			}

			// Write fgTiles
			for (int x = 0; x < size; x++) {
				for (int y = 0; y < size; y++) {
					short value = fgTiles[x][y];
					gzos.write((value >> 8) & 0xFF);
					gzos.write(value & 0xFF);
				}
			}

			gzos.flush();
			gzos.close();
			return true;

		} catch (IOException e) {
			LOGGER.error("Failed to save level", e);
			return false;
		}
	}

	public void generateLevel() {
		final double FREQUENCY = 1 / 24.0;
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				float value = OpenSimplexNoise.noise3_ImproveXY(seed, x * FREQUENCY, y * FREQUENCY, 0.0);
				if (value > 0) {
					bgTiles[x][y] = 3;
				} else {
					bgTiles[x][y] = 5;
				}
			}
		}

		loaded = true;
	}
}