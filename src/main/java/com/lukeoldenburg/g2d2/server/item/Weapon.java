package com.lukeoldenburg.g2d2.server.item;

import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;

public class Weapon extends Item {
	private final int damage;
	private final int attackTicks;
	private final Clip attackSound;

	public Weapon(String id, BufferedImage texture, int damage, int attackTicks, Clip attackSound) {
		super(id, texture);
		this.damage = damage;
		this.attackTicks = attackTicks;
		this.attackSound = attackSound;
	}

	public int getDamage() {
		return damage;
	}

	public int getAttackTicks() {
		return attackTicks;
	}

	public Clip getAttackSound() {
		return attackSound;
	}
}