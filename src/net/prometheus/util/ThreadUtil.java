package net.prometheus.util;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ThreadUtil {
	
	public static void execute(Runnable runnable) {
		Executor executor = Executors.newSingleThreadExecutor();
		executor.execute(runnable);
	}
	
	public static void sleep(long duration) {
		try {
			
			Thread.sleep(duration);
			
		} catch (InterruptedException exception) {
			exception.printStackTrace();
		}
	}
	
}
