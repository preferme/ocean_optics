package houlei.andriod.sfk.utils;

import java.util.Locale;

public class LocaleUtils {
    private LocaleUtils(){}

    public static boolean sameLanguage(Locale one, Locale another) {
        if (one != null && another != null) {
            return StringUtils.equals(one.getCountry(), another.getCountry())
                    && StringUtils.equals(one.getLanguage(), another.getLanguage());
        }
        return one == null && another == null;
    }

}
