package houlei.andriod.sfk.utils;

public class StringUtils {
    private StringUtils(){}

    public static boolean equals(String one, String another) {
        if (one != null && another != null) {
            return one.equals(another);
        }
        return one == null && another == null;
    }

}
