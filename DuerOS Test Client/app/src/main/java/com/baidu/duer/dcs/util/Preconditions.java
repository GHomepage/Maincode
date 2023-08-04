
package com.baidu.duer.dcs.util;


public final class Preconditions {

    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    public static void checkAllNotNull(Object... references) {
        for (Object reference : references) {
            if (reference == null) {
                throw new NullPointerException();
            }
        }
    }
}