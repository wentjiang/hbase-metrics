package com.wentjiang;

import java.util.UUID;

public final class StringUtil {
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static String join(final char delimiter, final String... strings) {
        if (strings.length == 0) {
            return null;
        }
        if (strings.length == 1) {
            return strings[0];
        }
        int length = strings.length - 1;
        for (final String s : strings) {
            if (s == null) {
                continue;
            }
            length += s.length();
        }
        final StringBuilder sb = new StringBuilder(length);
        if (strings[0] != null) {
            sb.append(strings[0]);
        }
        for (int i = 1; i < strings.length; ++i) {
            if (!isEmpty(strings[i])) {
                sb.append(delimiter).append(strings[i]);
            } else {
                sb.append(delimiter);
            }
        }
        return sb.toString();
    }

    public static boolean substringMatch(CharSequence str, int index, CharSequence substring) {
        if (index + substring.length() > str.length()) {
            return false;
        }
        for (int i = 0; i < substring.length(); i++) {
            if (str.charAt(index + i) != substring.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static String generateRequestId() {
        return UUID.randomUUID().toString();
    }
}