package nl.tcilegnar.dndcharactersheet.Money.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import android.content.Context;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.BuildConfig;

import static junit.framework.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class MoneyIndicatorTextViewTest {
    private MoneyIndicatorTextView moneyIndicatorTextView;

    @Before
    public void setUp() {
        moneyIndicatorTextView = new MoneyIndicatorTextView(getContext(), null);
    }

    @Test
    public void getMoneyValue_Default_NoTextIsValue0() {
        // Arrange

        // Act
        int moneyValue = moneyIndicatorTextView.getMoneyValue();

        // Assert
        assertEquals(0, moneyValue);
    }

    @Test
    public void getMoneyValue_TextIsSetTo12_GetValueOf12() {
        // Arrange
        int expectedValue = 12;
        moneyIndicatorTextView.setText(String.valueOf(expectedValue));

        // Act
        int moneyValue = moneyIndicatorTextView.getMoneyValue();

        // Assert
        assertEquals(expectedValue, moneyValue);
    }

    @Test
    public void setMoneyValue_TextIsSetTo12_ValueIsSetAsText() {
        // Arrange
        int expectedIntValue = 12;

        // Act
        moneyIndicatorTextView.setMoneyValue(expectedIntValue);

        // Assert
        String expectedStringValue = String.valueOf(expectedIntValue);
        assertEquals(expectedStringValue, moneyIndicatorTextView.getText());
    }

    private Context getContext() {
        return App.getContext();
    }
}