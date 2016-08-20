package nl.tcilegnar.dndcharactersheet.Level.LevelUp.ViewGroup;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import nl.tcilegnar.dndcharactersheet.Level.Level.MaxLevelReachedException;
import nl.tcilegnar.dndcharactersheet.Level.Level.MinLevelReachedException;
import nl.tcilegnar.dndcharactersheet.Level.Level.ReadyForLevelChangeListener;
import nl.tcilegnar.dndcharactersheet.Level.LevelUp.LevelsReadyForChange;
import nl.tcilegnar.dndcharactersheet.Level.LevelUp.LevelsReadyForChange.ChangeLevelListener;
import nl.tcilegnar.dndcharactersheet.R;

public class LevelChangeView extends LinearLayout implements OnClickListener, ReadyForLevelChangeListener {
    private final LevelsReadyForChange levelsReadyForChange;

    private ImageButton changeLevelButton;
    private TextView changeLevelTimes;

    private LevelUpIconVisibilityChangedListener levelUpIconVisibilityChangedListener;

    public LevelChangeView(Context context, AttributeSet attrs) {
        this(context, attrs, new LevelsReadyForChange());
    }

    @VisibleForTesting
    protected LevelChangeView(Context context, AttributeSet attrs, LevelsReadyForChange levelsReadyForChange) {
        super(context, attrs, R.attr.levelUpStyle);
        this.levelsReadyForChange = levelsReadyForChange;
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.level_change, this);
        initViews();

        handleViews();
    }

    private void initViews() {
        changeLevelButton = (ImageButton) findViewById(R.id.change_level_button);
        changeLevelTimes = (TextView) findViewById(R.id.change_level_times);

        changeLevelButton.setOnClickListener(this);
    }

    private void handleViews() {
        if (levelsReadyForChange.isReadyForLevelChange()) {
            changeLevelButton.setVisibility(View.VISIBLE);
            if (levelsReadyForChange.shouldShowValue()) {
                setChangeLevelTimesText();
                changeLevelTimes.setVisibility(View.VISIBLE);
            } else {
                changeLevelTimes.setVisibility(View.GONE);
            }
        } else {
            changeLevelButton.setVisibility(View.GONE);
            changeLevelTimes.setVisibility(View.GONE);
        }
    }

    private void setChangeLevelTimesText() {
        changeLevelTimes.setText("x " + levelsReadyForChange.getNumberOfLevelsReadyForChange());
    }

    public int getNumberOfLevelsReadyForChange() {
        return levelsReadyForChange.getNumberOfLevelsReadyForChange();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.change_level_button) {
            handleChangeLevel();
        }
    }

    private void handleChangeLevel() {
        try {
            levelsReadyForChange.handleLevelChange();
            handleViews();
        } catch (MinLevelReachedException | MaxLevelReachedException e) {
            // TODO: iets hiermee doen?
        }
    }

    public void save() {
        levelsReadyForChange.save();
    }

    @Override
    public void onReadyForLevelChange(int levelChangeValue) {
        levelsReadyForChange.onReadyForLevelChange(levelChangeValue);
        handleViews();
        levelUpIconVisibilityChangedListener.onLevelUpIconVisibilityChanged();
    }

    public void setChangeLevelListener(ChangeLevelListener changeLevelListener) {
        levelsReadyForChange.setChangeLevelListener(changeLevelListener);
    }

    public void setLevelUpIconVisibilityChangedListener(LevelUpIconVisibilityChangedListener listener) {
        this.levelUpIconVisibilityChangedListener = listener;
    }

    public boolean isReadyForLevelChange() {
        return levelsReadyForChange.isReadyForLevelChange();
    }

    public interface LevelUpIconVisibilityChangedListener {
        void onLevelUpIconVisibilityChanged();
    }
}