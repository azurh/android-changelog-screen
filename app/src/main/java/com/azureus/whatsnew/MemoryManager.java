package com.azureus.whatsnew;

import android.content.Context;
import android.content.SharedPreferences;

public class MemoryManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "AppPreferences";
    public static final String KEY_VERSION_CODE = "KEY_VERSION_CODE";

    public MemoryManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    }

    public void setVersionCode(int versionCode) {
        editor = pref.edit();
        editor.putInt(KEY_VERSION_CODE, versionCode);
        editor.apply();
    }

    public int getVersionCode() {
        return pref.getInt(KEY_VERSION_CODE, 0);
    }

}
