package nl.tcilegnar.dndcharactersheet;

import android.app.Fragment;

public abstract class BaseFragment extends Fragment {
	@Override
	public void onPause() {
		onSaveData();
		super.onPause();
	}

	protected abstract void onSaveData();
}
