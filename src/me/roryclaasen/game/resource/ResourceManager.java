package me.roryclaasen.game.resource;

import java.awt.Color;
import java.io.IOException;

import me.roryclaasen.language.LangUtil;
import me.roryclaasen.language.LanguageFile;
import me.roryclaasen.util.Log;

public class ResourceManager {

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
		BACKGROUND("#FFE0B2"), //
		DEBUG(173, 255, 47), //
		DEBUG_BACK(Color.BLACK, 255 / 2), //
		PANEL_BACKGROUND(Color.WHITE, 75), //
		TILE_TEXT(Color.WHITE), //
		TILE_BLANK(Color.GRAY.brighter());

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
		return new Color(Integer.valueOf(hex.substring(1, 3), 16), Integer.valueOf(hex.substring(3, 5), 16), Integer.valueOf(hex.substring(5, 7), 16));
	}
}
