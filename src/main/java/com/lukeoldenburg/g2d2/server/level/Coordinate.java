package com.lukeoldenburg.g2d2.server.level;

public class Coordinate {
	private double x;
	private double y;

	public Coordinate(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void translate(double dx, double dy, int levelSize) {
		if (x + dx > 0 && x + dx < levelSize) this.x += dx;
		if (y + dy > 0 && y + dy < levelSize) this.y += dy;
	}
}