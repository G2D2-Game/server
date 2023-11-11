package com.lukeoldenburg.g2d2.server;

import com.google.gson.JsonObject;
import com.lukeoldenburg.g2d2.server.entity.Entity;
import com.lukeoldenburg.g2d2.server.level.Level;
import org.apache.logging.log4j.LogManager;
import org.java_websocket.server.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Server {
	private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);
	private static JsonObject config;
	private static WebSocketServer webSocketServer;
	private static Level level;
	private static List<Entity> entities = new ArrayList<>();
	private static List<String> ipBans = new ArrayList<>();
	private static List<String> steamIdBans = new ArrayList<>();

	public static List<String> getIpBans() {
		return ipBans;
	}

	public static void addIpBan(String ip) {
		ipBans.add(ip);
	}

	public static List<String> getSteamIdBans() {
		return steamIdBans;
	}

	public static void addSteamIdBan(String steamId) {
		steamIdBans.add(steamId);
	}

	public static Level getLevel() {
		return level;
	}

	public static List<Entity> getEntities() {
		return entities;
	}

	public static void addEntity(Entity entity) {
		entities.add(entity);
	}

	public static JsonObject getConfig() {
		return config;
	}

	public static void main(String[] args) {
		LOGGER.info("Loading...");
		registerHooks();
		loadConfig();
		ipBans = JsonUtil.readJsonFile("ipBans.json", List.class);
		steamIdBans = JsonUtil.readJsonFile("steamIdBans.json", List.class);

		level = new Level(config.get("levelPath").getAsString(), 1000, System.nanoTime());
		try {
			level.loadLevel(new BufferedInputStream(new FileInputStream(config.get("levelPath").getAsString())));

		} catch (FileNotFoundException e) {
			level.generateLevel();
			level.saveLevel();
		}

		webSocketServer = new ServerSocket(new InetSocketAddress(config.get("port").getAsInt()));
	}

	private static void loadConfig() {
		config = Objects.requireNonNullElse(JsonUtil.readJsonFile("config.json", JsonObject.class), new JsonObject());
		if (!config.has("name")) config.addProperty("name", "G2D2");
		if (!config.has("description")) config.addProperty("description", "G2D2");
		if (!config.has("password")) config.addProperty("password", "");
		if (!config.has("port")) config.addProperty("port", 8000);
		if (!config.has("maxPlayerCount")) config.addProperty("maxPlayerCount", 50);
		if (!config.has("dummyLifespan")) config.addProperty("dummyLifespan", 9000);
		if (!config.has("connectionLostTimeout")) config.addProperty("connectionLostTimeout", 15);
		if (!config.has("maxPendingConnections")) config.addProperty("maxPendingConnections", 10);
		if (!config.has("tcpNoDelay")) config.addProperty("tcpNoDelay", true);
		if (!config.has("reuseAddr")) config.addProperty("reuseAddr", true);
		if (!config.has("maxPing")) config.addProperty("maxPing", 500);
		if (!config.has("friendlyFire")) config.addProperty("friendlyFire", false);
		if (!config.has("pvp")) config.addProperty("pvp", false);
		if (!config.has("levelPath")) config.addProperty("levelPath", "level.bin.gz");
	}

	private static void registerHooks() {
		Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> LOGGER.error("There was an uncaught exception in thread {}", thread.getName(), throwable));
		Runtime.getRuntime().addShutdownHook(new Thread(Server::shutdown));
		LOGGER.info("Successfully registered hooks");
	}

	private static void shutdown() {
		LOGGER.info("Saving...");
		try {
			webSocketServer.stop();

		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		JsonUtil.writeJsonFile("config.json", config);
		JsonUtil.writeJsonFile("ipBans.json", ipBans);
		JsonUtil.writeJsonFile("steamIdBans.json", steamIdBans);
		level.saveLevel();
		LogManager.shutdown();
	}
}