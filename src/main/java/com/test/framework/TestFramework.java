package com.test.framework;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestFramework implements ModInitializer {
	public static final String MOD_ID = "test-framework";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// Folder for scenario json files. Points to ./minecraft/test_framework/scenarios
	public static final Path SCENARIOS_FOLDER = FabricLoader.getInstance().getGameDir().resolve(MOD_ID).resolve("scenarios");

	@Override
	public void onInitialize() {
		// Register custom commands
		LOGGER.info("Registering custom commands...");
		CommandRegistrationCallback.EVENT.register(
			(
				(dispatcher, registryAccess, environment) ->
				{
					ModCommands.register(dispatcher);
				}
			)
		);

		// Create scenarios folder if one doesn't exist.
		createScenariosFolder();
	}

	public static ResourceLocation id(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}

	/**
	 *  Creates a custom folder for the scenario json files, if one doesn't exist.
	 */
	private static void createScenariosFolder()
	{
		try
		{
			if (Files.notExists(SCENARIOS_FOLDER))
			{
				LOGGER.info("Creating scenarios folder...");
				Files.createDirectories(SCENARIOS_FOLDER);
			}
		}
		catch (IOException e)
        {
			String err_str = "Could not create scenarios folder: " + e.toString();
			LOGGER.error(err_str);
        }
	}
}
