package com.enterprayz.urec.wifiexplorerlib.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

/**
 * Created by con on 07.01.15.
 */
public class Packager {

    public static String getPackageName(Context context){
        String s = context.getPackageName();
        return s;
    }


}
