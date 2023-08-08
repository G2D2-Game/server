package com.lukeoldenburg.g2d2.server.level;

import com.lukeoldenburg.g2d2.server.OpenSimplexNoise;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Level {
	private static final int SIZE = 5000;
	private String levelPath;
	private short[][] bgTiles = new short[SIZE][SIZE];
	private short[][] fgTiles = new short[SIZE][SIZE];
	private Map<Coordinate, byte[]> tileData = new HashMap<>();

	public Level(String levelPath) {
		this.levelPath = levelPath;
		try {
			loadLevel(new BufferedInputStream(new FileInputStream(levelPath)));

		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public Level() {
		this.levelPath = "level.bin.gz";
		generateLevel();
	}

	public Level(byte[] data) {
		loadLevel(new BufferedInputStream(new ByteArrayInputStream(data)));
	}

	private Map<Coordinate, byte[]> loadTileData(GZIPInputStream gzis) throws IOException {
		byte[] buffer = new byte[4];
		int bytesRead;

		while ((bytesRead = gzis.read(buffer)) != -1) {
			if (bytesRead < 4) {
				// Insufficient bytes to read Coordinate data, break the loop
				break;
			}

			int maxX = (buffer[0] << 8) | (buffer[1] & 0xFF);
			int maxY = (buffer[2] << 8) | (buffer[3] & 0xFF);

			ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
			while (true) {
				int dataByte = gzis.read();
				if (dataByte == -1) {
					break; // No more data to read
				}

				dataStream.write(dataByte);
			}

			tileData.put(new Coordinate(maxX, maxY), dataStream.toByteArray());
		}

		return tileData;
	}

	private void loadLevel(BufferedInputStream bis) {
		try {
			GZIPInputStream gzis = new GZIPInputStream(bis);

			byte[] buffer = new byte[50000000];
			int bytesRead = gzis.read(buffer);

			short[][] bgTiles = new short[SIZE][SIZE];
			for (int x = 0; x < SIZE; x++) {
				for (int y = 0; y < SIZE; y++) {
					int index = x * SIZE + y;
					bgTiles[x][y] = (short) ((buffer[index * 2] << 8) | (buffer[index * 2 + 1] & 0xFF));
				}
			}

			bytesRead = gzis.read(buffer);

			short[][] fgTiles = new short[SIZE][SIZE];
			for (int x = 0; x < SIZE; x++) {
				for (int y = 0; y < SIZE; y++) {
					int index = x * SIZE + y;
					fgTiles[x][y] = (short) ((buffer[index * 2] << 8) | (buffer[index * 2 + 1] & 0xFF));
				}
			}

			loadTileData(gzis);
			gzis.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveLevel() {
		try {
			FileOutputStream fos = new FileOutputStream(levelPath);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			GZIPOutputStream gzos = new GZIPOutputStream(bos);

			// Write bgTiles
			for (int x = 0; x < SIZE; x++) {
				for (int y = 0; y < SIZE; y++) {
					short value = bgTiles[x][y];
					gzos.write((value >> 8) & 0xFF);
					gzos.write(value & 0xFF);
				}
			}

			// Write fgTiles
			for (int x = 0; x < SIZE; x++) {
				for (int y = 0; y < SIZE; y++) {
					short value = fgTiles[x][y];
					gzos.write((value >> 8) & 0xFF);
					gzos.write(value & 0xFF);
				}
			}

			// Write map data
			for (Map.Entry<Coordinate, byte[]> entry : tileData.entrySet()) {
				Coordinate coordinate = entry.getKey();
				byte[] data = entry.getValue();

				gzos.write((coordinate.getX() >> 8) & 0xFF);
				gzos.write(coordinate.getX() & 0xFF);
				gzos.write((coordinate.getY() >> 8) & 0xFF);
				gzos.write(coordinate.getY() & 0xFF);
				gzos.write(data);
			}

			gzos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void generateLevel() {
		double FREQUENCY = 1 / 24.0;
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < SIZE; y++) {
				short value = (short) Math.floor(OpenSimplexNoise.noise3_ImproveXY(System.nanoTime(), x * FREQUENCY, y * FREQUENCY, 0.0) * 100);
				bgTiles[x][y] = value;
			}
		}
	}
}