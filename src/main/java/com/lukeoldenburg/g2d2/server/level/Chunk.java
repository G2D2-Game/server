package com.lukeoldenburg.g2d2.server.level;

public class Chunk {
	private Coordinate location;
	private short[][] fgLayer;
	private short[][] bgLayer;

	public Chunk(Coordinate location) {
		this.location = location;
		this.fgLayer = new short[256][256];
		this.bgLayer = new short[256][256];
	}

	public Coordinate getLocation() {
		return location;
	}

	public short getFgTile(int x, int y) {
		return fgLayer[x][y];
	}

	public void setFgTile(int x, int y, short tile) {
		fgLayer[x][y] = tile;
	}

	public short getBgTile(int x, int y) {
		return bgLayer[x][y];
	}

	public void setBgTile(int x, int y, short tile) {
		bgLayer[x][y] = tile;
	}
}