package nl.tcilegnar.dndcharactersheet.Experience.Animations;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ProgressBar;

import nl.tcilegnar.dndcharactersheet.Base.Animations.ProgressBarAnimation;
import nl.tcilegnar.dndcharactersheet.Experience.Experience;

public enum ExperienceProgressBarAnimation implements AnimationListener {
    INSTANCE;
    private static final int PROGRESS_DURATION_MILLIS = 1000;
    public static final int NO_REPEATS = 0;

    private ProgressBar progressBar;
    private float startProgress;
    private float finalProgress;
    private int numberOfLevelsChanged;
    private int newMaxProgress;

    private Experience experience;

    private boolean repeatingAnimationsStarted = false;
    private boolean finalAnimationStarted = false;
    private boolean allAnimationsEnded = true;

    public void start(ProgressBar progressBar, Experience experience, int numberOfLevelsChanged) {
        init(progressBar, experience, numberOfLevelsChanged);
        performInitialAnimation();
    }

    private void init(ProgressBar progressBar, Experience experience, int numberOfLevelsChanged) {
        this.progressBar = progressBar;

        this.startProgress = progressBar.getProgress();
        this.finalProgress = experience.getCurrentExp();
        this.numberOfLevelsChanged = numberOfLevelsChanged;
        this.newMaxProgress = experience.getMax();

        this.experience = experience;

        repeatingAnimationsStarted = false;
        finalAnimationStarted = false;
        allAnimationsEnded = false;
    }

    private void performInitialAnimation() {
        if (numberOfLevelsChanged > 0) {
            startAnimation(startProgress, progressBar.getMax());
        } else if (numberOfLevelsChanged < 0) {
            startAnimation(startProgress, 0);
        } else { // numberOfLevelsChanged == 0
            startAnimation(startProgress, finalProgress);
        }
    }

    private void performRepeatingAnimation() {
        repeatingAnimationsStarted = true;
        if (numberOfLevelsChanged > 1) {
            int numberOfRepeatingAnimations = numberOfLevelsChanged - 1;
            int numberOfRepeatingAnimationRepeats = numberOfRepeatingAnimations - 1;
            startAnimationFromMinimumProgressTo(progressBar.getMax(), numberOfRepeatingAnimationRepeats);
        } else if (numberOfLevelsChanged < -1) {
            int numberOfRepeatingAnimations = -numberOfLevelsChanged - 1;
            int numberOfRepeatingAnimationRepeats = numberOfRepeatingAnimations - 1;
            startAnimationFromMaximumProgressTo(0, numberOfRepeatingAnimationRepeats);
        } else { // numberOfLevelsChanged == -1 || 0 || 1
            startNextAnimation();
        }
    }

    private void performFinalAnimation() {
        finalAnimationStarted = true;
        if (numberOfLevelsChanged > 0) {
            progressBar.setMax(newMaxProgress);
            startAnimationFromMinimumProgressTo(finalProgress, NO_REPEATS);
        } else if (numberOfLevelsChanged < 0) {
            progressBar.setMax(newMaxProgress);
            startAnimationFromMaximumProgressTo(finalProgress, NO_REPEATS);
        } else { // numberOfLevelsChanged == 0
            startNextAnimation();
        }
    }

    private void startAnimationFromMinimumProgressTo(float to, int repeats) {
        float from = 0;
        progressBar.setProgress((int) from);
        startAnimation(from, to, repeats);
    }

    private void startAnimationFromMaximumProgressTo(float to, int repeats) {
        float from = progressBar.getMax();
        progressBar.setProgress((int) from);
        startAnimation(from, to, repeats);
    }

    private void startAnimation(float from, float to) {
        startAnimation(from, to, NO_REPEATS);
    }

    private void startAnimation(float from, float to, int repeat) {
        ProgressBarAnimation animation = new ProgressBarAnimation(progressBar, from, to);
        animation.setRepeatCount(repeat);
        animation.setDuration(PROGRESS_DURATION_MILLIS);
        animation.setAnimationListener(this);
        progressBar.startAnimation(animation);
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        startNextAnimation();
    }

    private void startNextAnimation() {
        if (!repeatingAnimationsStarted) {
            performRepeatingAnimation();
        } else if (!finalAnimationStarted) {
            performFinalAnimation();
        } else {
            allAnimationsEnded = true;
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }

    public boolean hasAnimationEnded() {
        return allAnimationsEnded;
    }
}
