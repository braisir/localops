package io.github.braisir.localops.component;

public class Logger {

	private Logger() {
		super();
	}
	
	public static void info(String msg) {
		System.out.println(msg);
	}
	
	public static void warn(String msg) {
		System.out.println(msg);
	}
	
	public static void error(String msg) {
		System.out.println(msg);
	}
}
