package nl.tcilegnar.dndcharactersheet.Experience.Animations;

import android.view.animation.Animation;
import android.widget.ProgressBar;

import nl.tcilegnar.dndcharactersheet.Base.Animations.ProgressBarAnimation;

public class ExperienceProgressBarAnimation implements Animation.AnimationListener {
    private static final int PROGRESS_DURATION_MILLIS = 1000;

    private ProgressBar progressBar;
    private float fromInitial;
    private float toFinal;

    private boolean onProgressMaxReached;

    public ExperienceProgressBarAnimation(ProgressBar progressBar, float newProgress) {
        this.progressBar = progressBar;
        this.fromInitial = progressBar.getProgress();
        this.toFinal = newProgress;

        performInitialAnimation();
    }

    private void performInitialAnimation() {
        startAnimation(fromInitial, toFinal);
    }

    private void performFinalAnimation() {
        startAnimation(0, toFinal);
    }

    private void startAnimation(float from, float to) {
        ProgressBarAnimation animation = new ProgressBarAnimation(progressBar, from, to);
        animation.setDuration(PROGRESS_DURATION_MILLIS);
        progressBar.startAnimation(animation);
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (onProgressMaxReached) {
            onProgressMaxReached = false;
            performFinalAnimation();
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }
}
