package com.lukeoldenburg.g2d2.server.item;

import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;

public class Consumable extends Item {
	private final int consumptionTicks;
	private final int health;
	private final int hunger;
	private final int thirst;
	private final Clip consumptionSound;

	public Consumable(String id, BufferedImage texture, int consumptionTicks, int health, int hunger, int thirst, Clip consumptionSound) {
		super(id, texture);
		this.consumptionTicks = consumptionTicks;
		this.health = health;
		this.hunger = hunger;
		this.thirst = thirst;
		this.consumptionSound = consumptionSound;
	}

	public Clip getConsumptionSound() {
		return consumptionSound;
	}

	public int getConsumptionTicks() {
		return consumptionTicks;
	}

	public int getHealth() {
		return health;
	}

	public int getHunger() {
		return hunger;
	}

	public int getThirst() {
		return thirst;
	}
}