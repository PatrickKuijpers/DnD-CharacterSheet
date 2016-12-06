package nl.tcilegnar.dndcharactersheet.Utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import nl.tcilegnar.dndcharactersheet.BuildConfig;
import nl.tcilegnar.dndcharactersheet.Utils.MyBuildConfig;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public abstract class MyBuildConfigTest {
    protected MyBuildConfig buildConfig;

    @Before
    public void setUp() {
        buildConfig = new MyBuildConfig();
    }

    @Test
    public abstract void isDebug_DependsOnBuildType();
}