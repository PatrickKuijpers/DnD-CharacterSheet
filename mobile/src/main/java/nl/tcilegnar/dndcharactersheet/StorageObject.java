package nl.tcilegnar.dndcharactersheet;

import android.support.annotation.VisibleForTesting;

import nl.tcilegnar.dndcharactersheet.Storage.Storage;

public abstract class StorageObject {
    protected final Storage storage;

    protected StorageObject() {
        this(new Storage());
    }

    @VisibleForTesting
    protected StorageObject(Storage storage) {
        this.storage = storage;
        init();
    }

    protected abstract void init();

    public abstract void save();
}
