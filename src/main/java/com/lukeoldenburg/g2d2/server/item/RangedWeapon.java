package com.lukeoldenburg.g2d2.server.item;

import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;

public class RangedWeapon extends Weapon {
	private final boolean automatic;
	private final int range;
	private final int reloadTicks;
	private final int magazineCapacity;
	private final Clip reloadSound;

	public RangedWeapon(String id, BufferedImage texture, int damage, int attackTicks, Clip attackSound, boolean automatic, int range, int reloadTicks, int magazineCapacity, Clip reloadSound) {
		super(id, texture, damage, attackTicks, attackSound);
		this.automatic = automatic;
		this.range = range;
		this.reloadTicks = reloadTicks;
		this.magazineCapacity = magazineCapacity;
		this.reloadSound = reloadSound;
	}

	public Clip getReloadSound() {
		return reloadSound;
	}

	public boolean getAutomatic() {
		return automatic;
	}

	public int getRange() {
		return range;
	}

	public int getReloadTicks() {
		return reloadTicks;
	}

	public int getMagazineCapacity() {
		return magazineCapacity;
	}
}