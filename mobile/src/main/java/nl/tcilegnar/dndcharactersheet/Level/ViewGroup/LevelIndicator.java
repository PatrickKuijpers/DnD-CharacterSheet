package nl.tcilegnar.dndcharactersheet.Level.ViewGroup;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.Level.Level;
import nl.tcilegnar.dndcharactersheet.Level.Level.LevelDownListener;
import nl.tcilegnar.dndcharactersheet.Level.Level.LevelUpListener;
import nl.tcilegnar.dndcharactersheet.R;

public class LevelIndicator extends LinearLayout implements OnClickListener, LevelUpListener, LevelDownListener {
    private TextView levelIndicator;
    private ImageButton levelUpButton;
    private TextView experiernceRemainder;

    private final Level level;

    public LevelIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, new Level());
    }

    @VisibleForTesting
    protected LevelIndicator(Context context, AttributeSet attrs, Level level) {
        super(context, attrs, R.attr.expCurrentLvlStyle);
        this.level = level;
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.level, this);
        initViews();
        setLevelText();
    }

    private void initViews() {
        levelIndicator = (TextView) findViewById(R.id.level_indicator);
        levelUpButton = (ImageButton) findViewById(R.id.level_up_button);
        experiernceRemainder = (TextView) findViewById(R.id.experience_remainder);

        levelUpButton.setOnClickListener(this);
    }

    private void setLevelText() {
        String levelText = String.valueOf(level.getCurrentLevel());
        levelIndicator.setText(levelText);
    }

    public Level getLevel() {
        return level;
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(App.getContext(), "Ga nu pas level up!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLevelUp() {
        setLevelText();
        levelUpButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLevelDown() {
        setLevelText();
    }
}
