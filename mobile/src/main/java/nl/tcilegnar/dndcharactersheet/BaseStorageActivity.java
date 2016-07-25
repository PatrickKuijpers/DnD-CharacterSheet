package nl.tcilegnar.dndcharactersheet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import nl.tcilegnar.dndcharactersheet.Settings.SettingsActivity;

public abstract class BaseStorageActivity extends BaseActivity {
    private SettingsResponseReceiver settingsChangedReceiver;
    private LocalBroadcastManager broadcastManager;
    private boolean settingsChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        settingsChangedReceiver = new SettingsResponseReceiver();
        broadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastManager.registerReceiver(settingsChangedReceiver, new IntentFilter(SettingsActivity.SETTINGS_CHANGED));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (settingsChanged) {
            settingsChanged = false;
            updateSettingsData();
        }
    }

    protected abstract void updateSettingsData();

    @Override
    protected void onDestroy() {
        broadcastManager.unregisterReceiver(settingsChangedReceiver);
        super.onDestroy();
    }

    class SettingsResponseReceiver extends BroadcastReceiver {
        public SettingsResponseReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(SettingsActivity.SETTINGS_CHANGED)) {
                settingsChanged = true;
            }
        }
    }
}
