package nl.tcilegnar.dndcharactersheet;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import nl.tcilegnar.dndcharactersheet.enums.FragTag;

public class FragmentManager {
    private final android.support.v4.app.FragmentManager supportFragmentManager;

    public enum Anim {
        SLIDE_RIGHT_TO_LEFT, SLIDE_LEFT_TO_RIGHT, SLIDE_RIGHT_TO_LEFT_INCL_BACK, SLIDE_LEFT_TO_RIGHT_INCL_BACK,
        NO_ANIMATION
    }

    public FragmentManager(AppCompatActivity activity) {
        supportFragmentManager = activity.getSupportFragmentManager();
    }

    public void addFirstFragment(FragTag fragTag) {
        replaceFragment(fragTag, false, Anim.NO_ANIMATION);
    }

    public void replaceFragment(FragTag fragTag) {
        replaceFragment(fragTag, true, Anim.SLIDE_RIGHT_TO_LEFT_INCL_BACK);
    }

    public void replaceFragment(FragTag fragTag, boolean addToBackstack, Anim animation) {
        Fragment fragment = getFragment(fragTag);
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        setAnimation(transaction, animation);
        String tag = fragTag.tag();
        transaction.replace(R.id.activity_content, fragment, tag);
        if (addToBackstack) {
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }

    private void setAnimation(FragmentTransaction transaction, Anim fragmentAnimation) {
        switch (fragmentAnimation) {
            case SLIDE_RIGHT_TO_LEFT_INCL_BACK:
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left,
                        R.anim.exit_to_right);
                break;
            case SLIDE_LEFT_TO_RIGHT_INCL_BACK:
                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim
                        .enter_from_right, R.anim.exit_to_left);
                break;
            case SLIDE_RIGHT_TO_LEFT:
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                break;
            case SLIDE_LEFT_TO_RIGHT:
                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                break;
            case NO_ANIMATION:
            default:
                // Geen animatie
                break;
        }
    }

    public Fragment getFragment(FragTag fragTag) {
        Fragment returnedFragment = getExistingFragment(fragTag);
        if (returnedFragment == null) {
            returnedFragment = fragTag.get();
        }
        return returnedFragment;
    }

    public Fragment getExistingFragment(FragTag fragmentAndTag) {
        Fragment newFragment = fragmentAndTag.get();
        Class<? extends Fragment> classType = newFragment.getClass();
        return classType.cast(supportFragmentManager.findFragmentByTag(fragmentAndTag.tag()));
    }
}
