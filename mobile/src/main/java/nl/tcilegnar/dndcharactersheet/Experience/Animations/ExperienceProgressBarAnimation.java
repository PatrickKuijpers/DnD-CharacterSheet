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
    private int newMaxProgress;

    private Experience experience;

    private boolean repeatingAnimationsEnded = false;
    private boolean finalAnimationEnded = true;
    private int repeatsLeft = 0;

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

        int repeats = getNumberOfRepeatingAnimations(numberOfLevelsChanged);
        this.repeatsLeft = repeats;
        repeatingAnimationsEnded = false;
        finalAnimationEnded = false;
    }

    private int getNumberOfRepeatingAnimations(int numberOfLevelsChanged) {
        if (numberOfLevelsChanged > 1) {
            return numberOfLevelsChanged - 1;
        } else if (numberOfLevelsChanged < -1) {
            return numberOfLevelsChanged + 1;
        } else { // numberOfLevelsChanged == -1 || 0 || 1
            return 0;
        }
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
        if (repeatsLeft > 0) {
            repeatsLeft--;
            startAnimationFromMinimumProgressTo(progressBar.getMax());
        } else if (repeatsLeft < 0) {
            repeatsLeft++;
            startAnimationFromMaximumProgressTo(0);
        } else { // repeatsLeft == 0
            repeatingAnimationsEnded = true;
            performFinalAnimation();
        }
    }

    private void performFinalAnimation() {
        if (numberOfLevelsChanged > 0) {
            progressBar.setMax(newMaxProgress);
            startAnimationFromMinimumProgressTo(finalProgress);
        } else if (numberOfLevelsChanged < 0) {
            progressBar.setMax(newMaxProgress);
            startAnimationFromMaximumProgressTo(finalProgress);
        } else { // numberOfLevelsChanged == 0
            // Hier hoeft niets meer te gebeuren
        }
    }

    private void startAnimationFromMinimumProgressTo(float to) {
        int from = 0;
        progressBar.setProgress(from);
        startAnimation(from, to);
    }

    private void startAnimationFromMaximumProgressTo(float to) {
        float from = progressBar.getMax();
        progressBar.setProgress((int) from);
        startAnimation(from, to);
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
        if (!repeatingAnimationsEnded) {
            performRepeatingAnimation();
        } else {
            finalAnimationEnded = true;
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }

    public boolean hasAnimationEnded() {
        return finalAnimationEnded;
    }
}
