package houlei.andriod.sfk;

import androidx.fragment.app.Fragment;

public class ApplicationContext {

    private static final ApplicationContext INSTANCE = new ApplicationContext();
    private ApplicationContext(){}
    public static ApplicationContext getInstance() {
        return INSTANCE;
    }

    private Fragment currentFragment;

    public void setCurrentFragment(Fragment currentFragment) {
        this.currentFragment = currentFragment;
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }

}

