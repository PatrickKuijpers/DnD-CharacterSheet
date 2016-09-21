package nl.tcilegnar.dndcharactersheet.Money.ViewGroup;

import nl.tcilegnar.dndcharactersheet.R;

import static junit.framework.Assert.assertNotNull;

public class Asserts {
    public static void hasCorrectEditors(MoneyEditor view) {
        assertNotNull(view.findViewById(R.id.money_editor_views));

        assertNotNull(view.findViewById(R.id.money_edittext));
        assertNotNull(view.findViewById(R.id.money_numberslider));
        assertNotNull(view.findViewById(R.id.money_numberpicker));
    }
}
