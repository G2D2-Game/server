package com.lukeoldenburg.g2d2.server.item;

import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;

public class Clothing extends Item {
	private final int slots;
	private final Clip equipSound;

	public Clothing(String id, BufferedImage texture, int slots, Clip equipSound) {
		super(id, texture);
		this.slots = slots;
		this.equipSound = equipSound;
	}

	public int getSlots() {
		return slots;
	}

	public Clip getEquipSound() {
		return equipSound;
	}
}