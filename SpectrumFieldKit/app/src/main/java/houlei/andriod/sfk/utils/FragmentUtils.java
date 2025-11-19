package houlei.andriod.sfk.utils;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class FragmentUtils {

    private FragmentUtils(){}

    public static Fragment findFragment(FragmentActivity activity, Class<? extends Fragment> type) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        for (Fragment fragment : fragmentManager.getFragments()) {
            if (fragment.getClass()==type) {
                return fragment;
            }
        }
        return null;
    }

    public static Fragment findFragment(FragmentActivity activity, int id) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        return fragmentManager.findFragmentById(id);
    }

}
