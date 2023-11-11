package com.lukeoldenburg.g2d2.server;

import com.google.gson.JsonObject;
import com.lukeoldenburg.g2d2.server.entity.Player;
import com.lukeoldenburg.g2d2.server.level.Coordinate;
import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.framing.CloseFrame;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.ServerHandshakeBuilder;
import org.java_websocket.server.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

public class ServerSocket extends WebSocketServer {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerSocket.class);

	public ServerSocket(InetSocketAddress address) {
		super(address);
		JsonObject config = Server.getConfig();
		setConnectionLostTimeout(config.get("connectionLostTimeout").getAsInt());
		setReuseAddr(config.get("reuseAddr").getAsBoolean());
		setTcpNoDelay(config.get("tcpNoDelay").getAsBoolean());
		setMaxPendingConnections(config.get("maxPendingConnections").getAsInt());
		start();
	}

	@Override
	public ServerHandshakeBuilder onWebsocketHandshakeReceivedAsServer(WebSocket conn, Draft draft, ClientHandshake request) throws InvalidDataException {
		ServerHandshakeBuilder builder = super.onWebsocketHandshakeReceivedAsServer(conn, draft, request);
		String clientAddress = conn.getRemoteSocketAddress().getAddress().getHostAddress();
		if (Server.getIpBans().contains(clientAddress)) {
			LOGGER.warn("Client tried to connect from: " + clientAddress + " but is IP banned");
			throw new InvalidDataException(CloseFrame.POLICY_VALIDATION, clientAddress + " is IP banned");
		}

		if (Server.getSteamIdBans().contains(request.getFieldValue("steamId"))) {
			LOGGER.warn("Client tried to connect from: " + clientAddress + " but is SteamId banned");
			throw new InvalidDataException(CloseFrame.POLICY_VALIDATION, request.getFieldValue("steamId") + " is SteamId banned");
		}

		if (!request.getFieldValue("password").equals(Server.getConfig().get("password").getAsString())) {
			LOGGER.warn("Client tried to connect from: " + clientAddress + " with an invalid password");
			throw new InvalidDataException(CloseFrame.POLICY_VALIDATION, "Invalid password");
		}

		Server.getEntities().add(new Player(new Coordinate(50, 50), 100, Long.valueOf(request.getFieldValue("steamId")), request.getFieldValue("name")));
		return builder;
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		LOGGER.info("New client connected from: " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		LOGGER.info("Client disconnected from: " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
	}

	@Override
	public void onMessage(WebSocket conn, String message) {
	}

	@Override
	public void onMessage(WebSocket conn, ByteBuffer message) {
	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
		LOGGER.error("A websocket error occurred: ", ex);
		conn.close();
	}

	@Override
	public void onStart() {
		LOGGER.info("Server started at: " + getAddress().getAddress().getHostAddress() + ":" + getPort());
	}
}