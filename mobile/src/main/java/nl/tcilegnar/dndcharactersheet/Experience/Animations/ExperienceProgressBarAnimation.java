package nl.tcilegnar.dndcharactersheet.Experience.Animations;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ProgressBar;

import nl.tcilegnar.dndcharactersheet.Base.Animations.ProgressBarAnimation;
import nl.tcilegnar.dndcharactersheet.Experience.Experience;

public class ExperienceProgressBarAnimation implements AnimationListener {
    private static final int PROGRESS_DURATION_IMMEDIATE = 0;
    private static final int PROGRESS_DURATION_MILLIS = 1000;
    private static final int NO_REPEATS = 0;

    private boolean repeatingAnimationsStarted = false;
    private boolean finalAnimationStarted = false;
    private boolean allAnimationsEnded = true;

    private ProgressBar progressBar;
    private float startProgress;
    private float finalProgress;
    private int newMaxProgress;
    private int numberOfLevelsChanged;

    public void start(ProgressBar progressBar, Experience experience) {
        if (allAnimationsEnded) {
            init(progressBar, experience);
            performInitialAnimation();
        }
    }

    private void init(ProgressBar progressBar, Experience experience) {
        repeatingAnimationsStarted = false;
        finalAnimationStarted = false;
        allAnimationsEnded = false;

        this.progressBar = progressBar;

        this.startProgress = progressBar.getProgress();
        this.finalProgress = experience.getCurrentExp();
        this.newMaxProgress = experience.getMax();

        this.numberOfLevelsChanged = experience.getExperienceUpdater().getNumberOfLevelsChanged();
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
        startAnimation(from, to, repeat, PROGRESS_DURATION_MILLIS);
    }

    private void startAnimation(float from, float to, int repeat, int duration) {
        if (from == to) {
            duration = PROGRESS_DURATION_IMMEDIATE;
        }
        ProgressBarAnimation animation = new ProgressBarAnimation(progressBar, from, to);
        animation.setRepeatCount(repeat);
        animation.setDuration(duration);
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

    public boolean isFinished() {
        return allAnimationsEnded;
    }
}
