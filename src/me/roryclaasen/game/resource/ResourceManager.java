package me.roryclaasen.game.resource;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

import me.roryclaasen.language.LangUtil;
import me.roryclaasen.language.LanguageFile;
import me.roryclaasen.util.Log;

public class ResourceManager {

	public static Font roboto = loadFont("assets/fonts/Roboto-Bold.ttf");

	public static void init() {
		LanguageFile enUK = new LanguageFile("assets/lang/en_GB.lang");
		try {
			Log.info("Reading language file");
			LangUtil.setLanguageFileAndRead(enUK);
			Log.info("Language file applied");
		} catch (IOException e) {
			Log.error("Failed to read language file");
			Log.stackError(e);
		}
	}

	public static enum colors {
		BACKGROUND("D7CCC8"), //
		DEBUG(173, 255, 47), //
		DEBUG_BACK(Color.BLACK, 255 / 2), //
		PANEL_BACKGROUND("8D6E63"), //
		TILE_TEXT(Color.WHITE), //
		TILE_BLANK("eee4da", 100), //
		TILE_ONE("eee4da"), // 2
		TILE_TWO("edc22e"), // 4
		TILE_THREE("f78e48"), // 8
		TILE_FOUR("fc5e2e"), // 16
		TILE_FIVE("ff3333"), // 32
		TILE_SIX("ff0000"), // 64 
		TILE_SEVEN("4DB6AC"), // 128
		TILE_EIGHT("81C784"), // 256
		TILE_NINE("81C784"), // 512
		TILE_TEN("81C784"), // 1024
		TILE_ELEVEN("81C784"), // 2048
		;

		private Color color;

		colors(Color color) {
			this.color = color;
		}

		colors(Color color, int alpha) {
			this.color = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
		}

		colors(String hex) {
			this(getColorFromHex(hex));
		}

		colors(String hex, int alpha) {
			this(getColorFromHex(hex), alpha);
		}

		colors(int r, int g, int b, int a) {
			this(new Color(r, g, b, a));
		}

		colors(int r, int g, int b) {
			this(r, g, b, 255);
		}

		public Color get() {
			return color;
		}
	}

	private static Color getColorFromHex(String hex) {
		if (!hex.startsWith("#")) hex = "#" + hex;
		return new Color(Integer.valueOf(hex.substring(1, 3), 16), Integer.valueOf(hex.substring(3, 5), 16), Integer.valueOf(hex.substring(5, 7), 16));
	}

	private static Font loadFont(String ref) {
		Log.info("loading font '" + ref + "'");
		try {
			return Font.createFont(java.awt.Font.TRUETYPE_FONT, ResourceManager.class.getClassLoader().getResourceAsStream(ref));
		} catch (FontFormatException | IOException e) {
			Log.warn("failed to load font '" + ref + "'");
		}
		return null;
	}
}
