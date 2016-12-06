package com.shuyu.video.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

/**
 * Created by zhangleilei on 19/11/2016.
 */
public class SMSUtils {

    public static void sendSMS(Context context, String number, String text) {
        PendingIntent pi = PendingIntent.getActivity(context, 0,
                new Intent(context, context.getClass()), 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(number, null, text, pi, null);
    }

}
