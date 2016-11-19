package com.shuyu.video.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangleilei on 19/11/2016.
 */

public class SMSUtils {

    public static String PhoneNumber = "";

    public void SendSMS(Context context, String number, String text) {
        PendingIntent pi = PendingIntent.getActivity(context, 0,
                new Intent(context, context.getClass()), 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(number, null, text, pi, null);

    }

    public static String GetPhoneNumberFromSMSText(String sms) {

        List<String> list = GetNumberInString(sms);
        for (String str : list) {
            if (str.length() == 11)
                return str;
        }
        return "";
    }

    public static List<String> GetNumberInString(String str) {
        List<String> list = new ArrayList<>();
        String regex = "\\d*";
        Pattern p = Pattern.compile(regex);

        Matcher m = p.matcher(str);

        while (m.find()) {
            if (!"".equals(m.group()))
                list.add(m.group());
        }
        return list;
    }


}
