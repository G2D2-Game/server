package com.lukeoldenburg.g2d2.server.packet;

import java.nio.ByteBuffer;

public class Packet {
	protected final PacketType packetType;
	protected ByteBuffer bytes;

	public Packet(PacketType packetType) {
		this.packetType = packetType;
		updateBytes();
	}

	public Packet(ByteBuffer bytes) {
		this.bytes = bytes;
		this.packetType = PacketType.getType(bytes.get(0x00));
	}

	public ByteBuffer getBytes() {
		return bytes;
	}

	public PacketType getPacketType() {
		return packetType;
	}

	protected void updateBytes() {
		bytes.put(0x00, packetType.getId());
	}
}