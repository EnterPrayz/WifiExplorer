/*
 * Copyright (C) 2009-2010 Aubort Jean-Baptiste (Rorist)
 * Licensed under GNU's GPL 2, see README
 */

// http://standards.ieee.org/regauth/oui/oui.txt

package com.enterprayz.urec.wifiexplorerlib.database;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.enterprayz.urec.wifiexplorerlib.utils.NetInfo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DbActionsHelper {

    private final static String TAG = "HardwareAddress";
    private final static String SELECT_VENDOR_FROM_OUI_WHERE_MAC = "select vendor from oui where mac=?";
    private final static String SELECT_DESCRIPTION_FROM_PORTS_WHERE_NUM = "select description, state from ports where num=?";

    // 0x1 is HW Type:  Ethernet (10Mb) [JBP]
    // 0x2 is ARP Flag: completed entry (ha valid)
    private final static String MAC_RE = "^%s\\s+0x1\\s+0x2\\s+([:0-9a-fA-F]+)\\s+\\*\\s+\\w+$";
    private final static int BUF = 8 * 1024;


    public static String getHardwareAddress(String ip) {
        String hw = NetInfo.NOMAC;
        try {
            if (ip != null) {
                String ptrn = String.format(MAC_RE, ip.replace(".", "\\."));
                Pattern pattern = Pattern.compile(ptrn);
                BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/net/arp"), BUF);
                String line;
                Matcher matcher;
                while ((line = bufferedReader.readLine()) != null) {
                    matcher = pattern.matcher(line);
                    if (matcher.matches()) {
                        hw = matcher.group(1);
                        break;
                    }
                }
                bufferedReader.close();
            } else {
                Log.e(TAG, "ip is null");
            }
        } catch (IOException e) {
            Log.e(TAG, "Can't open/read file ARP: " + e.getMessage());
            return hw;
        }
        return hw;
    }

    public static String getNicVendor(String hw, Context context) throws SQLiteDatabaseCorruptException {
        String ni = null;
        try {
            String macDBpath = DbMain.getMacDbPath(context);
            SQLiteDatabase db = SQLiteDatabase.openDatabase(macDBpath, null, SQLiteDatabase.OPEN_READONLY | SQLiteDatabase.NO_LOCALIZED_COLLATORS);
            if (db != null) {
                // Db request
                if (db.isOpen()) {
                    Cursor c = db.rawQuery(SELECT_VENDOR_FROM_OUI_WHERE_MAC, new String[]{hw.replace(":", "")
                            .substring(0, 6).toUpperCase()});
                    if (c.moveToFirst()) {
                        ni = c.getString(0);
                    }
                    c.close();
                }
                db.close();
            }
        } catch (IllegalStateException e) {
            Log.e(TAG, e.getMessage());
        } catch (SQLiteException e) {
            Log.e(TAG, e.getMessage());
        }
        return ni;
    }

    public static String[] getPortDestination(int port, Context context) throws SQLiteDatabaseCorruptException {
        String description = null;
        String status = null;
        try {
            String macDBpath = DbMain.getMacDbPath(context);
            SQLiteDatabase db = SQLiteDatabase.openDatabase(macDBpath, null, SQLiteDatabase.OPEN_READONLY | SQLiteDatabase.NO_LOCALIZED_COLLATORS);
            if (db != null) {
                // Db request
                if (db.isOpen()) {
                    Cursor c = db.rawQuery(SELECT_DESCRIPTION_FROM_PORTS_WHERE_NUM, new String[]{String.valueOf(port)});
                    if (c.moveToFirst()) {
                        description = c.getString(0);
                        status = c.getString(1);
                    }
                    c.close();
                }
                db.close();
            }
        } catch (IllegalStateException e) {
            Log.e(TAG, e.getMessage());
        } catch (SQLiteException e) {
            Log.e(TAG, e.getMessage());
        }
        return new String[]{description, status};
    }
}
