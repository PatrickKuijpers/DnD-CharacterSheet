package nl.tcilegnar.dndcharactersheet.abilities.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;

import nl.tcilegnar.dndcharactersheet.BuildConfig;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class AbilityTest {
    @Test
    public void getConstructorValues_Strength_NotNull() {
        // Arrange
        Ability strength = Ability.STRENGTH;

        // Act
        String name = strength.getName();
        String abbreviation = strength.getAbbreviation();
        @DrawableRes int imageRes = strength.getImageRes();
        @ColorInt int colorRes = strength.getColor();

        // Assert
        assertNotNull(name);
        assertNotNull(abbreviation);
        assertNotNull(imageRes);
        assertNotNull(colorRes);
    }

    @Test
    public void getConstructorValue_Strength_ImageDescriptionContainsName() {
        // Arrange
        Ability strength = Ability.STRENGTH;
        String expectedSubstring = strength.getName();

        // Act
        String imageDescription = strength.getImageDescription();

        // Assert
        assertTrue(imageDescription.contains(expectedSubstring));
    }

    @Test
    public void saveValue_Strength_StorageSaveAbilityIsCalledWithSameValue() {
        // Arrange
        int expectedSavedAbilityValue = 11;
        Ability ability = Ability.STRENGTH;
        Storage storageMock = setStorageMock(ability);

        // Act
        ability.saveValue(expectedSavedAbilityValue);

        // Assert
        verify(storageMock).saveAbility(any(Storage.Key.class), eq(expectedSavedAbilityValue));
    }

    @Test
    public void loadValue() {
        // Arrange
        Ability ability = Ability.STRENGTH;
        Storage storageMock = setStorageMock(ability);

        // Act
        ability.loadValue();

        // Assert
        verify(storageMock).loadAbility(any(Storage.Key.class));
    }

    private Storage setStorageMock(Ability ability) {
        Storage storageMock = mock(Storage.class);
        ability.setStorage(storageMock);
        return storageMock;
    }
}