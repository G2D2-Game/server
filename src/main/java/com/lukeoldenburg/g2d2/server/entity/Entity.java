package com.lukeoldenburg.g2d2.server.entity;

import com.lukeoldenburg.g2d2.server.level.Coordinate;

public class Entity {
	private Coordinate coordinate;
	private int hp;

	public Entity(Coordinate coordinate, int hp) {
		this.coordinate = coordinate;
		this.hp = hp;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}
}