package nl.tcilegnar.dndcharactersheet.Level.ViewGroup;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import nl.tcilegnar.dndcharactersheet.Level.Level;
import nl.tcilegnar.dndcharactersheet.Level.Level.CurrentProjectedLevelListener;
import nl.tcilegnar.dndcharactersheet.Level.Level.LevelChangedListener;
import nl.tcilegnar.dndcharactersheet.Level.LevelUp.ViewGroup.LevelChangeView;
import nl.tcilegnar.dndcharactersheet.R;

public class LevelIndicatorView extends LinearLayout implements LevelChangedListener, CurrentProjectedLevelListener {
    private TextView levelView;

    private final Level level;
    private LevelChangeView levelChangeView;

    public LevelIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, new Level());
    }

    @VisibleForTesting
    protected LevelIndicatorView(Context context, AttributeSet attrs, Level level) {
        super(context, attrs, R.attr.levelIndicatorStyle);
        this.level = level;
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.level_indicator, this);
        initViews();
        setLevelText();
        setListeners();
    }

    private void initViews() {
        levelView = (TextView) findViewById(R.id.level_view);
        levelChangeView = (LevelChangeView) findViewById(R.id.level_change_view);
    }

    private void setLevelText() {
        String levelText = String.valueOf(level.getCurrentLevel());
        levelView.setText(levelText);
    }

    private void setListeners() {
        level.setReadyForLevelDownListener(levelChangeView);
        level.setReadyForLevelUpListener(levelChangeView);
        levelChangeView.setChangeLevelListener(level);
        level.addLevelChangedListener(this);
        level.setCurrentProjectedLevelListener(this);
    }

    public Level getLevel() {
        return level;
    }

    public void save() {
        level.save();
        levelChangeView.save();
    }

    @Override
    public void onLevelChanged() {
        setLevelText();
    }

    @Override
    public int getCurrentProjectedLevel() {
        return level.getCurrentLevel() + levelChangeView.getNumberOfLevelsReadyForChange();
    }
}