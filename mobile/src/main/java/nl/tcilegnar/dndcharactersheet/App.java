package nl.tcilegnar.dndcharactersheet;

import android.content.Context;
import android.content.res.Resources;
import android.support.multidex.MultiDexApplication;

public class App extends MultiDexApplication {
	private static Context context;

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
