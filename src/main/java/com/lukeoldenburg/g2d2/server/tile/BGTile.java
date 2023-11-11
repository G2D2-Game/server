package com.lukeoldenburg.g2d2.server.tile;

import java.awt.image.BufferedImage;

public class BGTile {
	private final short id;
	private final BufferedImage texture;

	public BGTile(short id, BufferedImage texture) {
		this.id = id;
		this.texture = texture;
	}
}