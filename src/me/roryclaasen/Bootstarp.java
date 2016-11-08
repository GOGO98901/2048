package me.roryclaasen;

import java.util.Date;

import me.roryclaasen.game.GameThread;
import me.roryclaasen.util.Log;

public class Bootstarp {
	private static Date startDate = new Date(), initDate;

	public static void main(String[] args) {
		Log.info(startDate);
		Log.info("Program starting");
		GameThread game = new GameThread();
		if (game.init()) {
			initDate = new Date();
			Log.info("Game initialization took " + (initDate.getTime() - startDate.getTime()) + " mills");
			game.start();
		} else Log.error("Game initialization... FAILED");
	}
}
