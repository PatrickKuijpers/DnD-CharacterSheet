package nl.tcilegnar.dndcharactersheet.Storage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import nl.tcilegnar.dndcharactersheet.BuildConfig;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class SettingsTest {
	private Settings settings;

	@Before
	public void setUp() {
		settings = new Settings();
	}

	@Test
	public void testFileName_NotNull() {
		// Arrange

		// Act
		String fileName = settings.fileName();

		// Assert
		assertNotNull(fileName.isEmpty());
	}

	@Test
	public void testFileName_Settings() {
		// Arrange

		// Act
		String fileName = settings.fileName();

		// Assert
		assertEquals("Als de filename veranderd is kunnen gegevens mogelijk niet meer correct worden geladen", "Settings", fileName);
	}
}