package com.enterprayz.urec.wifiexplorerlib.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.enterprayz.urec.wifiexplorerlib.database.DbMain;

import java.util.ArrayList;

/**
 * Created by con on 07.01.15.
 */
public class DbActionHelper {
    private Context context;
    private SQLiteDatabase database;


    public DbActionHelper(Context context, SQLiteDatabase database) {
        this.context = context;
        this.database = database;
    }

    public String getDeviceVendor(String macAddress) {
        if (database != null) {
            String vendor = "Unknown";
            String formatedMacAddress = macAddress.replace(":", "")
                    .substring(0, 6).toUpperCase();
            String selectQuery = "SELECT " + DbMain.VENDOR_COLUMN_NAME
                    + " FROM " + DbMain.OUI_TABLE_NAME
                    + " WHERE " + DbMain.MAC_COLUMN_NAME
                    + " = " + "\"" + formatedMacAddress + "\"";
            DbOperator operator = new DbOperator(database);
            vendor = operator.getVendorByMac(formatedMacAddress);
            return vendor;
        } else {
            return null;
        }
    }

    private static class DbOperator {
        private  final String TAG = DbOperator.class.getName();
        private  SQLiteDatabase db;

        public DbOperator(SQLiteDatabase db) {
            this.db = db;
        }

        synchronized String getVendorByMac(String macAdress) {
            Cursor cursor = db.query(DbMain.OUI_TABLE_NAME, new String[]{DbMain.VENDOR_COLUMN_NAME}, DbMain.MAC_COLUMN_NAME + "=?",
                    new String[]{macAdress}, null, null, null, null);
            String result = "";
            if (cursor != null) {
                cursor.moveToFirst();
                result = cursor.getString(0);
            }


            return result;
        }

    }
}
