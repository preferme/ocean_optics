package houlei.andriod.sfk.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtils {
    private SPUtils() {}

    private static final String SP_NAME = "sfk_preference";

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

}
