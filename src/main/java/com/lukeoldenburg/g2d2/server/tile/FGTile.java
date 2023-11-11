package com.lukeoldenburg.g2d2.server.tile;

import java.awt.image.BufferedImage;

public class FGTile extends BGTile {
	private final String name;
	private final StrengthLevel strengthLevel;

	public FGTile(short id, BufferedImage texture, String name, StrengthLevel strengthLevel) {
		super(id, texture);
		this.name = name;
		this.strengthLevel = strengthLevel;
	}
}