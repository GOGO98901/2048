package me.roryclaasen.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class Log {

	private Log() {}

	private static List<String> logged = new ArrayList<String>();

	public static enum LogType {
		LOG, INFO, WARN, ERROR, DEBUG;
		public void log(String log) {
			Log.print(this, log);
		}

		public void stack(Exception e) {
			Log.stack(this, e);
		}
	}

	private static void print(LogType type, Object log) {
		String output = log.toString();
		String msg = "[" + type + "] " + output;
		if (output.contains(System.getProperty("line.separator"))) {
			msg = "";
			for (String line : output.split(System.getProperty("line.separator"))) {
				msg += "[" + type + "] " + line + System.getProperty("line.separator");
			}
		}
		switch (type) {
		case ERROR: {
			System.err.println(msg);
			break;
		}
		case WARN: {
			System.err.println(msg);
			break;
		}
		default: {
			System.out.println(msg);
			break;
		}
		}
		logged.add(msg);
	}

	public static void log(Object log) {
		print(LogType.LOG, log);
	}

	public static void info(Object log) {
		print(LogType.INFO, log);
	}

	public static void warn(Object log) {
		print(LogType.WARN, log);
	}

	public static void error(Object log) {
		print(LogType.ERROR, log);
	}

	public static void debug(Object log) {
		print(LogType.DEBUG, log);
	}

	public static void stack(LogType type, Throwable e) {
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		print(type, errors.toString());
	}

	public static void stackWarn(Exception e) {
		stack(LogType.WARN, e);
	}

	public static void stackError(Exception e) {
		stack(LogType.ERROR, e);
	}

	public static List<String> getLogs() {
		return logged;
	}

	public static String getLast() {
		if (logged.size() > 1) return logged.get(logged.size() - 1);
		return null;
	}
}
