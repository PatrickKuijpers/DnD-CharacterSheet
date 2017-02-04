package nl.tcilegnar.dndcharactersheet.MainMenu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import nl.tcilegnar.dndcharactersheet.Base.BaseFragment;
import nl.tcilegnar.dndcharactersheet.R;
import nl.tcilegnar.dndcharactersheet.Utils.AppData;
import nl.tcilegnar.dndcharactersheet.characters.CurrentCharacter;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mainmenu, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setVersionNumber(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        setActivityTitle();
    }

    @Override
    public String getTitle() {
        return CurrentCharacter.DnDCharacter().getName();
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
        callbackMainMenu.onMainMenuButtonClicked(viewId);
    }

    public void setVersionNumber(View view) {
        TextView versionNrView = (TextView) view.findViewById(R.id.version_nr);
        versionNrView.setText(AppData.getAppVersionName());
    }
}
