package nl.tcilegnar.dndcharactersheet;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class FragmentManager {
    private final android.support.v4.app.FragmentManager supportFragmentManager;

    public enum Anim {
        SLIDE_RIGHT_TO_LEFT, SLIDE_LEFT_TO_RIGHT, NO_ANIMATION
    }

    public FragmentManager(AppCompatActivity activity) {
        supportFragmentManager = activity.getSupportFragmentManager();
    }

    public void addFirstFragment(Fragment fragment, String tag) {
        replaceFragment(fragment, tag, false, Anim.NO_ANIMATION);
    }

    public void replaceFragment(Fragment fragment, String tag, boolean addToBackstack, Anim animation) {
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        transaction.replace(R.id.activity_content, fragment, tag);
        setAnimation(transaction, animation);
        if (addToBackstack) {
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }

    private void setAnimation(FragmentTransaction transaction, Anim fragmentAnimation) {
        switch (fragmentAnimation) {
            case SLIDE_RIGHT_TO_LEFT:
                // transaction.setCustomAnimations(R.anim.anim_enter_from_right, R.anim.anim_exit_to_left,
                // R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case SLIDE_LEFT_TO_RIGHT:
                // transaction.setCustomAnimations(R.anim.anim_exit_to_left, R.anim.anim_enter_from_right,
                // R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case NO_ANIMATION:
            default:
                // Niks doen
                break;
        }
    }

    public <T extends Fragment> T getFragment(Class<T> type, String tag) {
        return type.cast(supportFragmentManager.findFragmentByTag(tag));
    }
}
