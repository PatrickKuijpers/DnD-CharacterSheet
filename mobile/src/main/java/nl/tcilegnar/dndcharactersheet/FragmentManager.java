package nl.tcilegnar.dndcharactersheet;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class FragmentManager {
    private final android.support.v4.app.FragmentManager supportFragmentManager;

    public enum Anim {
        SLIDE_RIGHT_TO_LEFT, SLIDE_LEFT_TO_RIGHT, SLIDE_RIGHT_TO_LEFT_INCL_BACK, SLIDE_LEFT_TO_RIGHT_INCL_BACK,
        NO_ANIMATION
    }

    public FragmentManager(AppCompatActivity activity) {
        supportFragmentManager = activity.getSupportFragmentManager();
    }

    public void addFirstFragment(Fragment fragment, String tag) {
        replaceFragment(fragment, tag, false, Anim.NO_ANIMATION);
    }

    public void replaceFragment(Fragment fragment, String tag) {
        replaceFragment(fragment, tag, true, Anim.SLIDE_RIGHT_TO_LEFT_INCL_BACK);
    }

    public void replaceFragment(Fragment fragment, String tag, boolean addToBackstack, Anim animation) {
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        setAnimation(transaction, animation);
        transaction.replace(R.id.activity_content, fragment, tag);
        if (addToBackstack) {
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }

    private void setAnimation(FragmentTransaction transaction, Anim fragmentAnimation) {
        switch (fragmentAnimation) {
            case SLIDE_RIGHT_TO_LEFT_INCL_BACK:
                transaction.setCustomAnimations(R.anim.anim_enter_from_right, R.anim.anim_exit_to_left, R.anim
                        .anim_enter_from_left, R.anim.anim_exit_to_right);
                break;
            case SLIDE_LEFT_TO_RIGHT_INCL_BACK:
                transaction.setCustomAnimations(R.anim.anim_enter_from_left, R.anim.anim_exit_to_right, R.anim
                        .anim_enter_from_right, R.anim.anim_exit_to_left);
                break;
            case SLIDE_RIGHT_TO_LEFT:
                transaction.setCustomAnimations(R.anim.anim_enter_from_right, R.anim.anim_exit_to_left);
                break;
            case SLIDE_LEFT_TO_RIGHT:
                transaction.setCustomAnimations(R.anim.anim_enter_from_left, R.anim.anim_exit_to_right);
                break;
            case NO_ANIMATION:
            default:
                // Geen animatie
                break;
        }
    }

    public <T extends Fragment> T getFragment(Class<T> type, String tag) {
        return type.cast(supportFragmentManager.findFragmentByTag(tag));
    }
}
