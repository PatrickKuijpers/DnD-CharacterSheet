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
import nl.tcilegnar.dndcharactersheet.Level.Level.ReadyForLevelDownListener;
import nl.tcilegnar.dndcharactersheet.Level.Level.ReadyForLevelUpListener;
import nl.tcilegnar.dndcharactersheet.Level.LevelUp.LevelsReadyForChange;
import nl.tcilegnar.dndcharactersheet.Level.LevelUp.LevelsReadyForChange.ChangeLevelListener;
import nl.tcilegnar.dndcharactersheet.R;

public class LevelChangeView extends LinearLayout implements OnClickListener, ReadyForLevelUpListener,
        ReadyForLevelDownListener {
    private ImageButton changeLevelButton;
    private TextView changeLevelTimes;

    private LevelsReadyForChange levelsReadyForChange;

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
        if (levelsReadyForChange.isReadyForLevelDown() || levelsReadyForChange.isReadyForLevelUp()) {
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
            e.printStackTrace();
        }
    }

    public void save() {
        levelsReadyForChange.save();
    }

    @Override
    public void onReadyForLevelDown() throws MinLevelReachedException {
        levelsReadyForChange.onReadyForLevelDown();
        handleViews();
    }

    @Override
    public void onReadyForLevelUp() throws MaxLevelReachedException {
        levelsReadyForChange.onReadyForLevelUp();
        handleViews();
    }

    public void setChangeLevelListener(ChangeLevelListener changeLevelListener) {
        levelsReadyForChange.setChangeLevelListener(changeLevelListener);
    }
}