package nl.tcilegnar.dndcharactersheet.Level.ViewGroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import nl.tcilegnar.dndcharactersheet.App;
import nl.tcilegnar.dndcharactersheet.R;

public class Level extends LinearLayout implements OnClickListener {
    private TextView levelIndicator;

    public Level(Context context, AttributeSet attrs) {
        super(context, attrs, R.attr.expEditorStyle);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.level, this);

        levelIndicator = (TextView) findViewById(R.id.level_indicator);

        (findViewById(R.id.level_up_button)).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(App.getContext(), "Ga nu pas level up!", Toast.LENGTH_SHORT).show();
    }
}
