package com.lukeoldenburg.g2d2.server.item;

import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;

public class ShotgunRangedWeapon extends RangedWeapon {
	private final int spread;

	public ShotgunRangedWeapon(String id, BufferedImage texture, int damage, int attackTicks, Clip attackSound, boolean automatic, int range, int reloadTicks, int magazineCapacity, Clip reloadSound, int spread) {
		super(id, texture, damage, attackTicks, attackSound, automatic, range, reloadTicks, magazineCapacity, reloadSound);
		this.spread = spread;
	}

	public int getSpread() {
		return spread;
	}
}