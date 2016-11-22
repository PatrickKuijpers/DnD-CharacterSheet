package nl.tcilegnar.dndcharactersheet.Money;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import nl.tcilegnar.dndcharactersheet.BuildConfig;

import static junit.framework.Assert.assertEquals;
import static nl.tcilegnar.dndcharactersheet.Money.MoneyConstants.MAX_BRONZE_VALUE;
import static nl.tcilegnar.dndcharactersheet.Money.MoneyConstants.MAX_GOLD_VALUE;
import static nl.tcilegnar.dndcharactersheet.Money.MoneyConstants.MAX_PLATINUM_VALUE;
import static nl.tcilegnar.dndcharactersheet.Money.MoneyConstants.MAX_SILVER_VALUE;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class MoneyConstantsTest {
    @Test
    public void maxPlatinumValue() {
        assertEquals(99, MAX_PLATINUM_VALUE);
    }

    @Test
    public void maxGoldValue() {
        assertEquals(99, MAX_GOLD_VALUE);
    }

    @Test
    public void maxSilverValue() {
        assertEquals(9, MAX_SILVER_VALUE);
    }

    @Test
    public void maxBronzeValue() {
        assertEquals(9, MAX_BRONZE_VALUE);
    }
}