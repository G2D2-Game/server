package com.lukeoldenburg.g2d2.server.entity;

import com.lukeoldenburg.g2d2.server.level.Coordinate;

public class Player extends Entity {
	private long steamId;
	private String name;
	private double speed = 0.5;

	public Player(Coordinate coordinate, int hp, long steamId, String name) {
		super(coordinate, hp);
		this.steamId = steamId;
		this.name = name;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public long getSteamId() {
		return steamId;
	}

	public String getName() {
		return name;
	}
}