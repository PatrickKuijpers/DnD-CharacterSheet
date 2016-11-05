package nl.tcilegnar.dndcharactersheet.Experience.Animations;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ProgressBar;

import nl.tcilegnar.dndcharactersheet.Base.Animations.ProgressBarAnimation;
import nl.tcilegnar.dndcharactersheet.Experience.Experience;

public enum ExperienceProgressBarAnimation implements AnimationListener {
    INSTANCE;
    private static final int PROGRESS_DURATION_MILLIS = 1000;

    private ProgressBar progressBar;
    private float startProgress;
    private float finalProgress;
    private int numberOfLevelsChanged;
    private int startMaxProgress;
    private int newMaxProgress;

    private Experience experience;

    private boolean onProgressMaxReached;

    private boolean finalAnimationStarted = false;

    public void start(ProgressBar progressBar, Experience experience, int numberOfLevelsChanged) {
        init(progressBar, experience, numberOfLevelsChanged);
        performInitialAnimation(progressBar, numberOfLevelsChanged);
    }

    private void init(ProgressBar progressBar, Experience experience, int numberOfLevelsChanged) {
        this.progressBar = progressBar;

        this.startProgress = progressBar.getProgress();
        this.finalProgress = experience.getCurrentExp();
        this.numberOfLevelsChanged = numberOfLevelsChanged;
        this.startMaxProgress = progressBar.getMax();
        this.newMaxProgress = experience.getMax();

        this.experience = experience;

        finalAnimationStarted = false;
    }

    private void performInitialAnimation(ProgressBar progressBar, int numberOfLevelsChanged) {
        if (numberOfLevelsChanged > 0) {
            startAnimation(startProgress, progressBar.getMax());
        } else if (numberOfLevelsChanged < 0) {
            startAnimation(startProgress, 0);
        } else { // numberOfLevelsChanged == 0
            startAnimation(startProgress, finalProgress);
            finalAnimationStarted = true;
        }
    }

    private void performFinalAnimation() {
        if (numberOfLevelsChanged > 0) {
            progressBar.setMax(newMaxProgress);
            progressBar.setProgress(0);
            startAnimation(0, finalProgress);
        } else if (numberOfLevelsChanged < 0) {
            progressBar.setMax(newMaxProgress);
            progressBar.setProgress(progressBar.getMax());
            startAnimation(progressBar.getMax(), finalProgress);
        } else { // numberOfLevelsChanged == 0
            // Hier hoeft niets meer te gebeuren
        }
        finalAnimationStarted = true;
    }

    private void startAnimation(float from, float to) {
        ProgressBarAnimation animation = new ProgressBarAnimation(progressBar, from, to);
        animation.setDuration(PROGRESS_DURATION_MILLIS);
        animation.setAnimationListener(this);
        progressBar.startAnimation(animation);
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (!finalAnimationStarted) {
            performFinalAnimation();
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }
}
