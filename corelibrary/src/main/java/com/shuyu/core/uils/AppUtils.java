package com.shuyu.core.uils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.shuyu.core.CoreApplication;

import java.io.IOException;
import java.util.UUID;

/**
 * 跟App相关的辅助类
 */
public class AppUtils {

    private AppUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获取系统版本号
     *
     * @return 获取系统版本号
     */
    public static int getAppVersion() {
        try {
            PackageInfo info = CoreApplication.getApplication().getPackageManager().getPackageInfo(
                    CoreApplication.getApplication().getPackageName(), 0);
            return info.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 获取应用程序名称
     *
     * @return 应用程序名称
     */
    public static String getAppName() {
        try {
            PackageManager packageManager = CoreApplication.getApplication().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    CoreApplication.getApplication().getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return CoreApplication.getApplication().getResources().getString(labelRes);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getPackageName() {
        try {
            return CoreApplication.getApplication().getPackageName();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }


    /**
     * [获取应用程序版本名称信息]
     *
     * @param context context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getIMSI() {
        TelephonyManager mTelephonyMgr = (TelephonyManager)
                CoreApplication.getApplication().getSystemService(Context.TELEPHONY_SERVICE);
        return mTelephonyMgr.getSubscriberId() == null ? "" : mTelephonyMgr.getSubscriberId();
    }

    public static String getIMEI() {
        TelephonyManager mTelephonyMgr = (TelephonyManager)
                CoreApplication.getApplication().getSystemService(Context.TELEPHONY_SERVICE);
        return mTelephonyMgr.getDeviceId() == null ? "" : mTelephonyMgr.getDeviceId();
    }

    public static String getTelNumber() {
        TelephonyManager mTelephonyMgr = (TelephonyManager)
                CoreApplication.getApplication().getSystemService(Context.TELEPHONY_SERVICE);
        return mTelephonyMgr.getLine1Number() == null ? "" : mTelephonyMgr.getLine1Number();
    }

    public static String getSerialNumber() {
        TelephonyManager mTelephonyMgr = (TelephonyManager)
                CoreApplication.getApplication().getSystemService(Context.TELEPHONY_SERVICE);
        return mTelephonyMgr.getSimSerialNumber() == null ? "" : mTelephonyMgr.getSimSerialNumber();
    }

    public static String getUUID() {
        String androidId = Settings.Secure.getString(CoreApplication.getApplication().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        UUID deiceUUID = new UUID(androidId.hashCode(), ((long) getIMEI().hashCode() << 32) |
                getSerialNumber().hashCode());
        return "client_" + deiceUUID.toString();
    }


    /**
     * 发送短信
     *
     * @param context context
     */
    public static void sendMsg(Context context) {
        Uri smsToUri = Uri.parse("smsto:");
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        context.startActivity(intent);
    }

    public static void openBrowser(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    public static void install(Context context, String filePath) {
        chmod("777", filePath);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://"+filePath), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    private static void chmod(String permission, String path) {
        try {
            String command = "chmod " + permission + " " + path;
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
