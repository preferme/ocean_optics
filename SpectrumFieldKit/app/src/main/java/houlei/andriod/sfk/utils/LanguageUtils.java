package houlei.andriod.sfk.utils;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Locale;

import houlei.andriod.sfk.ui.settings.Language;

public class LanguageUtils {

    private LanguageUtils(){}

    public static final Language[] requireLanguages = new Language[]{
            new Language("English", "en", Locale.ENGLISH),
            new Language("简体中文", "zh_CN", Locale.SIMPLIFIED_CHINESE),
    };

    public static Locale findLocale(String name, Locale defLoc) {
        for (Language lang : requireLanguages) {
            if (lang.getName().equals(name)) {
                return lang.getLocale();
            }
        }
        return defLoc;
    }

    public static Language findLanguage(String name, Language defLang) {
        for (Language lang : requireLanguages) {
            if (lang.getName().equals(name)) {
                return lang;
            }
        }
        return defLang;
    }

    public static int loadLanguageIndex(Context context, int defIdx) {
        SharedPreferences sharedPreferences = SPUtils.getSharedPreferences(context);
        String language = sharedPreferences.getString("language", "en");
        for (int index = 0 ; index < requireLanguages.length; index++) {
            if (requireLanguages[index].getName().equals(language)) {
                return index;
            }
        }
        return defIdx;
    }

    public static Language loadLanguage(Context context) {
        return requireLanguages[loadLanguageIndex(context, 0)];
    }

    public static void storeLanguage(Context context, Language language) {
        SharedPreferences sharedPreferences = SPUtils.getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("language", language.getName());
        editor.apply();
    }

}
