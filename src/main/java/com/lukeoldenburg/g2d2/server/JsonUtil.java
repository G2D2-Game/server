package com.lukeoldenburg.g2d2.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;

public class JsonUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

	public static <T> T readJsonFile(String path, Class<T> clazz) {
		try {
			String data = Files.readString(Path.of(path));
			LOGGER.info("Successfully loaded " + path);
			return GSON.fromJson(data, clazz);

		} catch (Exception e) {
			LOGGER.error("Failed to load " + path, e);
			return null;
		}
	}

	public static boolean writeJsonFile(String path, Object data) {
		try {
			Files.write(Path.of(path), GSON.toJson(data).getBytes());
			LOGGER.info("Successfully saved " + path);
			return true;

		} catch (Exception e) {
			LOGGER.error("Failed to save " + path, e);
			return false;
		}
	}

	public static String toJson(Object data) {
		return GSON.toJson(data);
	}

	public static <T> T fromJson(String json, Class<T> clazz) {
		return GSON.fromJson(json, clazz);
	}
}