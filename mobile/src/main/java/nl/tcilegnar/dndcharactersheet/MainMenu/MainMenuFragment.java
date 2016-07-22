package nl.tcilegnar.dndcharactersheet.MainMenu;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import nl.tcilegnar.dndcharactersheet.R;

import static android.view.View.OnClickListener;

public class MainMenuFragment extends Fragment implements OnClickListener {
    private MainMenuActivity callbackMainMenu;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainMenuActivity) {
            callbackMainMenu = (MainMenuActivity) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mainmenu, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        Button btnExperience = (Button) view.findViewById(R.id.main_menu_button_level_and_experience);
        btnExperience.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.main_menu_button_level_and_experience) {
            callbackMainMenu.startLevelAndExperience();
        }
    }
}
