package com.lukeoldenburg.g2d2.server.packet;

public enum PacketType {
	COMMAND((byte) 0x00),
	CHAT((byte) 0x01),
	LOCATION((byte) 0x02),
	ATTACK((byte) 0x03),
	EQUIP((byte) 0x04),
	LEVEL((byte) 0x05);

	private final byte id;

	PacketType(byte id) {
		this.id = id;
	}

	public static PacketType getType(byte id) {
		for (PacketType packetType : PacketType.values()) {
			if (packetType.getId() == id) {
				return packetType;
			}
		}

		return null;
	}

	public byte getId() {
		return this.id;
	}
}