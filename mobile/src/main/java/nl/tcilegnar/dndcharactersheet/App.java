package nl.tcilegnar.dndcharactersheet;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

public class App extends Application {
	private static Context context;

	@Override
	protected void attachBaseContext(Context base) {
		try {
			super.attachBaseContext(base);
		} catch (RuntimeException ignored) {
			// Multidex support doesn't play well with Robolectric yet
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
	}

	public static Context getContext() {
		return context;
	}

	public static Resources getAppResources() {
		return context.getResources();
	}
}
