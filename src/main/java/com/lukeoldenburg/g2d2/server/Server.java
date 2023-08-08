package com.lukeoldenburg.g2d2.server;

import com.google.gson.JsonObject;
import com.lukeoldenburg.g2d2.server.level.Level;
import org.java_websocket.server.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.util.Objects;

public class Server {
	private static final Logger logger = LoggerFactory.getLogger(Server.class);
	private static JsonObject config;
	private static WebSocketServer webSocketServer;
	private static Level level;

	public static JsonObject getConfig() {
		return config;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void main(String[] args) {
		logger.info("Loading...");
		registerHooks();

		config = Objects.requireNonNullElse(JsonFile.read(Path.of("config.json"), JsonObject.class), new JsonObject());

		File levelFile = new File(Objects.requireNonNullElse(config.get("levelPath").getAsString(), "level.bin.gz"));
		if (levelFile.exists() && !levelFile.isDirectory()) level = new Level("level.bin.gz");
		else level = new Level();

		webSocketServer = new ServerSocket(new InetSocketAddress(Objects.requireNonNullElse(config.get("port").getAsInt(), 8000)));
	}

	private static void registerHooks() {
		Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> logger.error("There was an uncaught exception in thread {}", thread.getName(), throwable));
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			logger.info("Saving...");
			if (JsonFile.write(Path.of("config.json"), config)) logger.info("Successfully saved config.json");
			else logger.error("Failed to save config.json");
			level.saveLevel();
		}));
	}
}