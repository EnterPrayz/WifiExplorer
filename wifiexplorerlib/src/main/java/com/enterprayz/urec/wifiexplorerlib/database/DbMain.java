package com.enterprayz.urec.wifiexplorerlib.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class DbMain {

    public static final String MAC_COLUMN_NAME = "mac";
    public static final String VENDOR_COLUMN_NAME = "vendor";
    public static final String OUI_TABLE_NAME = "oui";
    public static final int VENDOR_COLUMN_ID = 1;
    private static final String TAG = DbMain.class.getName();
    private static String DB_NAME = "device_base.db";
    private SQLiteDatabase db;
    private Context context;


    // constructor
    public DbMain(Context context) {
        this.context = context;
    }


    public SQLiteDatabase openDatabase() {
        File dbFile = context.getDatabasePath(DB_NAME);

        if (!dbFile.exists()) {
            try {
                String absoluteDBPath = dbFile.getAbsolutePath();
                File absoluteDbFile = new File(absoluteDBPath.substring(0, absoluteDBPath.lastIndexOf(File.separator)));
                if (!absoluteDbFile.isDirectory()) {
                    absoluteDbFile.mkdirs();
                }
                dbFile.createNewFile();

                copyDatabase(dbFile);
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }

        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READONLY);
    }

    private void copyDatabase(File dbFile) throws IOException {
        InputStream is = context.getAssets().open(DB_NAME);
        String[] io = context.getAssets().list("");
        Log.d(TAG, io.length + "");
        OutputStream os = new FileOutputStream(dbFile);

        byte[] buffer = new byte[1024];
        while (is.read(buffer) > 0) {
            os.write(buffer);
        }

        os.flush();
        os.close();
        is.close();
    }

}