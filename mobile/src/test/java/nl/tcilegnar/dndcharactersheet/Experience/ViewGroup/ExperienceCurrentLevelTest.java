package nl.tcilegnar.dndcharactersheet.Experience.ViewGroup;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.BuildConfig;
import nl.tcilegnar.dndcharactersheet.Experience.Experience;

import static org.mockito.Mockito.mock;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
@Ignore("Kan App resources niet vinden")
public class ExperienceCurrentLevelTest {
    private ExperienceCurrentLevel experienceCurrentLevel;
    private Experience experienceMock;

    @Before
    public void setUp() {
        experienceMock = mock(Experience.class);
        experienceCurrentLevel = new ExperienceCurrentLevel(App.getContext(), null, experienceMock);
    }

    //    @Test
    //    public void testSaveExp() {
    //        // Arrange
    //
    //        // Act
    //        experienceCurrentLevel.saveExp();
    //
    //        // Assert
    //        verify(experienceMock, times(1)).saveExp();
    //    }

    //    @Test
    //    public void testSetExperienceEdgeListener() {
    //        // Arrange
    //        Experience.ExperienceEdgeListener mockLevel = mock(Level.class);
    //
    //        // Act
    //        experienceCurrentLevel.setExperienceEdgeListener(mockLevel);
    //
    //        // Assert
    //        verify(experienceMock, times(1)).setExperienceEdgeListener(mockLevel);
    //    }
}
