package nl.tcilegnar.dndcharactersheet.ability.adapter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import android.content.Context;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.BuildConfig;
import nl.tcilegnar.dndcharactersheet.ability.model.Ability;
import nl.tcilegnar.dndcharactersheet.ability.viewgroup.AbilityView;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class AbilitiesAdapterTest {
    private AbilitiesAdapter abilitiesAdapter;

    @Before
    public void setUp() {
        abilitiesAdapter = new AbilitiesAdapter(getContext());
    }

    @Test
    public void getCount() {
        // Arrange

        // Act
        int numberOfAbilitiesInAdapter = abilitiesAdapter.getCount();

        // Assert
        assertEquals(6, numberOfAbilitiesInAdapter);
    }

    //TODO (test volgorde van ability items)
    @Test
    public void getItem() {
        // Arrange

        // Act
        int expectedIndex = 0;
        Ability ability = abilitiesAdapter.getItem(expectedIndex);

        // Assert
        Ability expectedAbility = getExpectedAbility(expectedIndex);
        assertEquals(expectedAbility, ability);
    }

    @Test
    public void getView_Position0_ReturnsAbilityViewWithStrength() {
        // Arrange
        int position = 0;
        AbilityView expectedView = getExpectedAbilityView(position);

        // Act
        AbilityView view = abilitiesAdapter.getView(position, null, null);

        // Assert
        assertEquals(expectedView, view);
    }

    @Test
    public void getView_NonNullConvertView_ReturnsSameView() {
        // Arrange
        AbilityView expectedConvertView = getExpectedAbilityView(Ability.CHARISMA);

        // Act
        AbilityView view = abilitiesAdapter.getView(0, expectedConvertView, null);

        // Assert
        assertEquals(expectedConvertView, view);
    }

    private AbilityView getExpectedAbilityView(int index) {
        return getExpectedAbilityView(getExpectedAbility(index));
    }

    private AbilityView getExpectedAbilityView(Ability ability) {
        return new AbilityView(getContext(), ability);
    }

    private Ability getExpectedAbility(int index) {
        return Ability.values()[index];
    }

    private Context getContext() {
        return App.getContext();
    }
}