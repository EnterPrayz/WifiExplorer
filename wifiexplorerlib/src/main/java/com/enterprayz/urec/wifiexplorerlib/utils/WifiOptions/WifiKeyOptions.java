package com.enterprayz.urec.wifiexplorerlib.utils.WifiOptions;

import android.net.wifi.WifiConfiguration;

import java.util.regex.Pattern;

/**
 * Created by ura on 08.12.14.
 */
public abstract class WifiKeyOptions extends WifiConfiguration{
    private static final Pattern HEX_DIGITS = Pattern.compile("[0-9A-Fa-f]+");
    private String KEY_VALUE = null;
    private int KEY_ATTR = -1;
    private String SSID;
    private boolean isShow;

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public String getSSID() {
        return SSID;
    }

    public void setShow(boolean isShow) {
        this.isShow = isShow;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setKEY_ATTR(int KEY_ATTR) {
        this.KEY_ATTR = KEY_ATTR;
    }

    public int getKEY_ATTR() {
        return KEY_ATTR;
    }

    public void setKEY_VALUE(String KEY_VALUE) {
        this.KEY_VALUE = KEY_VALUE;
    }

    public String getKEY_VALUE() {
        return KEY_VALUE;
    }

    public abstract WifiConfiguration getConfig();

    public static String quoteNonHex(String value, int... allowedLengths) {
        return isHexOfLength(value, allowedLengths) ? value : convertToQuotedString(value);
    }
    private static boolean isHexOfLength(CharSequence value, int... allowedLengths) {
        if (value == null || !HEX_DIGITS.matcher(value).matches()) {
            return false;
        }
        if (allowedLengths.length == 0) {
            return true;
        }
        for (int length : allowedLengths) {
            if (value.length() == length) {
                return true;
            }
        }
        return false;
    }
    private static String convertToQuotedString(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        // If already quoted, return as-is
        if (s.charAt(0) == '"' && s.charAt(s.length() - 1) == '"') {
            return s;
        }
        return s;
    }
}
