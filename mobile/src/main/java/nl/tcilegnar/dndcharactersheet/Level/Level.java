package nl.tcilegnar.dndcharactersheet.Level;

import android.support.annotation.VisibleForTesting;
import android.widget.Toast;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.Experience.Experience.ExperienceEdgeListener;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;

public class Level implements ExperienceEdgeListener {
	private final Storage storage;
	private int currentLevel;

	public Level() {
		this(new Storage());
	}

	@VisibleForTesting
	protected Level(Storage storage) {
		this.storage = storage;
		this.currentLevel = storage.loadLevel();
	}

	@Override
	public void onExperienceMaxReached() {
		currentLevel++;
		Toast.makeText(App.getContext(), "Level up: " + currentLevel, Toast.LENGTH_LONG).show();
	}

	public void saveLevel() {
		storage.saveLevel(currentLevel);
	}
}
