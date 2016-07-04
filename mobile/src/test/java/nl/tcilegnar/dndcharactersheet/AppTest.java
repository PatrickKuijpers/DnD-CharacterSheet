package nl.tcilegnar.dndcharactersheet;

import android.content.Context;
import android.content.res.Resources;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class AppTest {
	@Test
	public void testContext() {
		// Arrange
		Context expectedContext = RuntimeEnvironment.application.getApplicationContext();

		// Act
		Context actualContext = App.getContext();
		//		Context actualContext = RuntimeEnvironment.application;

		// Assert
		assertEquals(expectedContext, actualContext);
	}

	@Test
	@Ignore("Kan App resources niet vinden")
	public void testGetAppResources() {
		// Arrange
		Resources expectedResources = RuntimeEnvironment.application.getApplicationContext().getResources();

		// Act
		Resources actualResources = App.getAppResources();

		// Assert
		assertEquals(expectedResources.getString(R.string.app_name), actualResources.getString(R.string.app_name));
	}
}
