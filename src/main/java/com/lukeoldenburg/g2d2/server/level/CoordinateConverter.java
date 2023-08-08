package com.lukeoldenburg.g2d2.server.level;

public class CoordinateConverter {
	public static Coordinate tileToChunkCoords(int tileX, int tileY) {
		int chunkX = (int) Math.floor(tileX / 256);
		int chunkY = tileY / 256;
		return new Coordinate(chunkX, chunkY);
	}

	public static Coordinate tileToRegionCoords(int tileX, int tileY) {
		int regionX = (int) Math.floor(tileX / 8192);
		int regionY = (int) Math.floor(tileY / 8192);
		return new Coordinate(regionX, regionY);
	}

	public static Coordinate chunkToRegionCoords(int chunkX, int chunkY) {
		int regionX = (int) Math.floor(chunkX / 32);
		int regionY = (int) Math.floor(chunkY / 32);
		return new Coordinate(regionX, regionY);
	}
}