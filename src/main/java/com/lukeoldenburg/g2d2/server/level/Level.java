package com.lukeoldenburg.g2d2.server.level;

import java.util.HashMap;

public class Level {
	private HashMap<Coordinate, Chunk> loadedChunks;

	public Level() {
		this.loadedChunks = new HashMap<>();
	}

	public Chunk getChunk(Coordinate chunkLocation) {
		return loadedChunks.get(chunkLocation);
	}

	public void setChunk(Coordinate chunkLocation, Chunk chunk) {
		loadedChunks.put(chunkLocation, chunk);
	}

	public short getTile(int tileX, int tileY, boolean isForeground) {
		Chunk chunk = getChunk(CoordinateConverter.tileToChunkCoords(tileX, tileY));
		if (chunk == null) {
			// Chunk not loaded, handle as needed (load from disk or create new chunk).
			return 0;
		}

		int tileXInChunk = tileX % 256;
		int tileYInChunk = tileY % 256;
		if (isForeground) {
			return chunk.getFgTile(tileXInChunk, tileYInChunk);

		} else {
			return chunk.getBgTile(tileXInChunk, tileYInChunk);
		}
	}

	public void setTile(int tileX, int tileY, short tile, boolean isForeground) {
		Coordinate chunkCoords = CoordinateConverter.tileToChunkCoords(tileX, tileY);

		Chunk chunk = getChunk(chunkCoords);
		if (chunk == null) {
			// Chunk not loaded, handle as needed (load from disk or create new chunk).
			chunk = new Chunk(chunkCoords);
			setChunk(chunkCoords, chunk);
		}

		int tileXInChunk = tileX % 256;
		int tileYInChunk = tileY % 256;
		if (isForeground) {
			chunk.setFgTile(tileXInChunk, tileYInChunk, tile);
		} else {
			chunk.setBgTile(tileXInChunk, tileYInChunk, tile);
		}
	}
}