package me.roryclaasen.game.res;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import me.roryclaasen.util.Log;

public class ResourceManager {

	private static Map<String, String> strings = new HashMap<String, String>();
	static {
		strings.put("game.title", "2048 clone");
	}

	public static String getString(String key) {
		if (strings.containsKey(key)) return strings.get(key);
		Log.warn("No String found with key '" + key + "'");
		return "";
	}

	public static enum Colors {
		BACKGROUND("#FFE0B2"), DEBUG(173, 255, 47), DEBUG_BACK(0, 0, 0, 255 / 2);
		private Color color;

		Colors(Color color) {
			this.color = color;
		}

		Colors(String hex) {
			this(getColorFromHex(hex));
		}

		Colors(int r, int g, int b, int a) {
			this(new Color(r, g, b, a));
		}

		Colors(int r, int g, int b) {
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
