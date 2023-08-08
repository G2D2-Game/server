package com.lukeoldenburg.g2d2.server;

import com.google.gson.JsonObject;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.Objects;

public class ServerSocket extends WebSocketServer {
	public ServerSocket(InetSocketAddress address) {
		super(address);
		JsonObject config = Server.getConfig();
		setConnectionLostTimeout(Objects.requireNonNullElse(config.get("connectionLostTimeout").getAsInt(), 15));
		setReuseAddr(Objects.requireNonNullElse(config.get("reuseAddr").getAsBoolean(), true));
		setTcpNoDelay(Objects.requireNonNullElse(config.get("tcpNoDelay").getAsBoolean(), true));
		setMaxPendingConnections(Objects.requireNonNullElse(config.get("connectionLostTimeout").getAsInt(), 50));
		start();
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		Server.getLogger().info("New client connected from: " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		Server.getLogger().info("Client disconnected from: " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
	}

	@Override
	public void onMessage(WebSocket conn, String message) {
	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
		conn.close();
	}

	@Override
	public void onStart() {
		Server.getLogger().info("Server started at: " + getAddress().getAddress().getHostAddress() + ":" + getPort());
	}
}