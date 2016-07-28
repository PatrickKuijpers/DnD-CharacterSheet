package nl.tcilegnar.dndcharactersheet.MainMenu;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.BaseFragment;
import nl.tcilegnar.dndcharactersheet.R;

import static android.view.View.OnClickListener;

public class MainMenuFragment extends BaseFragment implements OnClickListener {
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
        LinearLayout buttonsContainer = (LinearLayout) view.findViewById(R.id.main_menu_buttons_container);
        setOnClickListenersOnChildren(buttonsContainer);
    }

    private void setOnClickListenersOnChildren(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            (viewGroup.getChildAt(i)).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.main_menu_button_basic_character_info) {
            showComingSoon();
        } else if (viewId == R.id.main_menu_button_level_and_experience) {
            callbackMainMenu.startLevelAndExperience();
        } else if (viewId == R.id.main_menu_button_abilities) {
            showComingSoon();
        } else if (viewId == R.id.main_menu_button_hp) {
            showComingSoon();
        } else if (viewId == R.id.main_menu_button_money) {
            showComingSoon();
        }
    }

    private void showComingSoon() {
        Toast.makeText(App.getContext(), R.string.coming_soon, Toast.LENGTH_SHORT).show();
    }
}
