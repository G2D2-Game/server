package com.lukeoldenburg.g2d2.server.item;

import java.awt.image.BufferedImage;

public class Item {
	private final String id;
	private final BufferedImage texture;

	public Item(String id, BufferedImage texture) {
		this.id = id;
		this.texture = texture;
	}

	public String getId() {
		return id;
	}

	public BufferedImage getTexture() {
		return texture;
	}
}