package com.azureus.whatsnew;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.util.Log;

public class ChangeLogScreen {

    private final String LOG_TAG = "ChangeLogScreen";

    private Activity mActivity;
    private MemoryManager mMemoryManager;

    public ChangeLogScreen(Activity context) {
        mActivity = context;
        mMemoryManager = new MemoryManager(mActivity);
    }

    public void show() {
        try {
            PackageInfo packageInfo = mActivity.getPackageManager().getPackageInfo(mActivity.getPackageName(), 0);
            int currentVersionCode = packageInfo.versionCode;
            if (currentVersionCode > mMemoryManager.getVersionCode()) {
                Log.d(LOG_TAG, "New version detected");
                mMemoryManager.setVersionCode(currentVersionCode);
                String changeLog = mActivity.getString(R.string.new_version);
                if (changeLog.isEmpty()) {
                    Log.d(LOG_TAG, "But do not display a changelist, since the new version does't contain any " +
                            "significant updates.");
                    Snackbar.make(mActivity.findViewById(android.R.id.content), "First launch of a new version. No "
                            + "changelist.", Snackbar.LENGTH_INDEFINITE).show();
                    return;
                }
                Log.d(LOG_TAG, "Display the changelist");
                AlertDialog.Builder adb = new AlertDialog.Builder(mActivity);
                adb.setTitle("Version " + packageInfo.versionName);
                adb.setMessage(mActivity.getString(R.string.new_version));
                adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                adb.create().show();
            } else {
                Log.d(LOG_TAG, "The changelist was shown for this version.");
                Snackbar.make(mActivity.findViewById(android.R.id.content), "The changelist was shown for this " +
                        "version.", Snackbar.LENGTH_INDEFINITE).show();
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(LOG_TAG, "Unable to get current version");
            e.printStackTrace();
        }
    }
}
