package nl.tcilegnar.dndcharactersheet.Experience.Animations;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ProgressBar;

import nl.tcilegnar.dndcharactersheet.Base.Animations.ProgressBarAnimation;
import nl.tcilegnar.dndcharactersheet.Experience.Experience;

public class ExperienceProgressBarAnimation implements AnimationListener {
    private static final int PROGRESS_DURATION_MILLIS = 1000;

    private final ProgressBar progressBar;
    private final float startProgress;
    private final float finalProgress;
    private final int numberOfLevelsChanged;
    private final int startMaxProgress;
    private final int newMaxProgress;

    private final Experience experience;

    private boolean onProgressMaxReached;

    public ExperienceProgressBarAnimation(ProgressBar progressBar, Experience experience, int numberOfLevelsChanged) {
        this.progressBar = progressBar;

        this.startProgress = progressBar.getProgress();
        this.finalProgress = experience.getCurrentExp();
        this.numberOfLevelsChanged = numberOfLevelsChanged;
        this.startMaxProgress = progressBar.getMax();
        this.newMaxProgress = experience.getMax();

        this.experience = experience;
    }

    public void start() {
        if (numberOfLevelsChanged > 0) {
            startAnimation(startProgress, progressBar.getMax());
        } else if (numberOfLevelsChanged < 0) {
            startAnimation(startProgress, 0);
        } else { // numberOfLevelsChanged == 0
            startAnimation(startProgress, finalProgress);
        }
    }

    private void performFinalAnimation() {
        if (numberOfLevelsChanged > 0) {
            progressBar.setMax(newMaxProgress);
            progressBar.setProgress(0);
            startFinalAnimation(0, finalProgress);
        } else if (numberOfLevelsChanged < 0) {
            progressBar.setMax(newMaxProgress);
            progressBar.setProgress(progressBar.getMax());
            startFinalAnimation(progressBar.getMax(), finalProgress);
        } else { // numberOfLevelsChanged == 0
            // Hier hoeft niets meer te gebeuren
        }
    }

    private void startAnimation(float from, float to) {
        ProgressBarAnimation animation = new ProgressBarAnimation(progressBar, from, to);
        animation.setDuration(PROGRESS_DURATION_MILLIS);
        animation.setAnimationListener(this);
        progressBar.startAnimation(animation);
    }

    private void startFinalAnimation(float from, float to) {
        ProgressBarAnimation animation = new ProgressBarAnimation(progressBar, from, to);
        animation.setDuration(PROGRESS_DURATION_MILLIS);
        progressBar.startAnimation(animation);
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        performFinalAnimation();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }
}
