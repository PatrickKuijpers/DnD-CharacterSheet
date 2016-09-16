package nl.tcilegnar.dndcharactersheet.Health;

import android.support.annotation.VisibleForTesting;

import nl.tcilegnar.dndcharactersheet.Base.StorageObject;
import nl.tcilegnar.dndcharactersheet.Storage.Storage;

public class Hp extends StorageObject {
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

    public void setTotal(int newTotal) throws IllegalArgumentException {
        if (newTotal <= 0) {
            throw new IllegalArgumentException();
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

    public void setCurrent(int newCurrent) throws IllegalArgumentException {
        if (newCurrent > total) {
            throw new IllegalArgumentException();
        }
        this.current = newCurrent;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int newTemp) throws IllegalArgumentException {
        if (newTemp < 0) {
            throw new IllegalArgumentException();
        }
        this.temp = newTemp;
    }

    public int getCurrentComplete() {
        return getCurrent() + getTemp();
    }
}
