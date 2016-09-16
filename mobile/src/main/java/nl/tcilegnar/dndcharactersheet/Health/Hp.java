package nl.tcilegnar.dndcharactersheet.Health;

import android.support.annotation.VisibleForTesting;
import android.widget.Toast;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.Base.StorageObject;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;

public class Hp extends StorageObject {
    private static final int MINIMUM_TOTAL = 1;
    protected static final int MINIMUM_CURRENT_DISABLED = 0;
    protected static final int MINIMUM_CURRENT_DYING = -9;
    protected static final int MINIMUM_CURRENT_DEAD = -10;
    private static final int MINIMUM_TEMP = 0;

    private int total = storage.loadTotalHp();
    private int current = storage.loadCurrentHp();
    private int temp = storage.loadTempHp();

    public Hp() {
        this(new Storage());
    }

    @VisibleForTesting
    protected Hp(Storage storage) {
        super(storage);
    }

    @Override
    public void save() {
        storage.saveTotalHp(total);
        storage.saveCurrentHp(current);
        storage.saveTempHp(temp);
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int newTotal) throws TotalHpTooLowException {
        if (newTotal < MINIMUM_TOTAL) {
            throw new TotalHpTooLowException();
        }

        if (newTotal < current) {
            setCurrent(newTotal);
        }

        int increasedValue = newTotal - this.total;
        boolean hasTotalHpIncreased = increasedValue > 0;
        if (hasTotalHpIncreased) {
            int newCurrent = getCurrent() + increasedValue;
            setCurrent(newCurrent);
        }

        this.total = newTotal;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int newCurrent) throws CurrentHpTooHighException {
        if (newCurrent > total) {
            throw new CurrentHpTooHighException();
        }
        this.current = newCurrent;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int newTemp) throws TempHpTooLowException {
        if (newTemp < MINIMUM_TEMP) {
            throw new TempHpTooLowException();
        }
        this.temp = newTemp;
    }

    public int getCurrentComplete() {
        return getCurrent() + getTemp();
    }

    public HealthState getCurrentHealthState() {
        if (isAlive()) {
            return HealthState.Alive;
        } else if (isDisabled()) {
            return HealthState.Disabled;
        } else if (isDying()) {
            return HealthState.Dying;
        } else {
            return HealthState.Dead;
        }
    }

    public boolean isAlive() {
        return current > MINIMUM_CURRENT_DISABLED;
    }

    public boolean isDisabled() {
        return current == MINIMUM_CURRENT_DISABLED;
    }

    public boolean isDying() {
        return current >= MINIMUM_CURRENT_DYING && current < MINIMUM_CURRENT_DISABLED;
    }

    public boolean isDead() {
        return current <= MINIMUM_CURRENT_DEAD;
    }

    public class TotalHpTooLowException extends IllegalArgumentException {
        public TotalHpTooLowException() {
            super(String.format(App.getAppResources().getString(R.string.total_hp_too_low_exception), MINIMUM_TOTAL));
            Toast.makeText(App.getContext(), getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public class CurrentHpTooHighException extends IllegalArgumentException {
        public CurrentHpTooHighException() {
            super(String.format(App.getAppResources().getString(R.string.current_hp_too_high_exception)));
            Toast.makeText(App.getContext(), getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public class TempHpTooLowException extends IllegalArgumentException {
        public TempHpTooLowException() {
            super(String.format(App.getAppResources().getString(R.string.temp_hp_too_low_exception), MINIMUM_TEMP));
            Toast.makeText(App.getContext(), getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
