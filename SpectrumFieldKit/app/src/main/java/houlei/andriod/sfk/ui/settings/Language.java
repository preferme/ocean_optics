package houlei.andriod.sfk.ui.settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Locale;

import houlei.andriod.sfk.utils.StringUtils;

public class Language {
    private final String text;
    private final String name;
    private final Locale locale;

    public Language(String text, String name, Locale locale) {
        this.text = text;
        this.name = name;
        this.locale = locale;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Language) {
            Language other = (Language) obj;
            return StringUtils.equals(name, other.name);
        }
        return false;
    }

    @NonNull
    @Override
    public String toString() {
        return text;
    }

    public String getText() {
        return text;
    }

    public String getName() {
        return name;
    }

    public Locale getLocale() {
        return locale;
    }

}
