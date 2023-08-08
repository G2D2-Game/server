package com.lukeoldenburg.g2d2.server.level;

public class Region {
	private Coordinate location;
	private Chunk[][] fgChunks;
	private Chunk[][] bgChunks;

	public Region(Coordinate location) {
		this.location = location;
		this.fgChunks = new Chunk[32][32];
		this.bgChunks = new Chunk[32][32];
	}

	public Coordinate getLocation() {
		return location;
	}

	public Chunk getFgChunk(int x, int y) {
		return fgChunks[x][y];
	}

	public void setFgChunk(int x, int y, Chunk chunk) {
		fgChunks[x][y] = chunk;
	}

	public Chunk getBgChunk(int x, int y) {
		return bgChunks[x][y];
	}

	public void setBgChunk(int x, int y, Chunk chunk) {
		bgChunks[x][y] = chunk;
	}
}