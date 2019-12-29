package com.example.fleetmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsListener extends BroadcastReceiver {

    private SharedPreferences preferences;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        Log.d("message received"," received1");

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
            SmsMessage[] msgs = null;
            String msg_from;
            if (bundle != null){
                //---retrieve the SMS message received---
                try{

                    Log.d("message received"," received22");

                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        String msgBody = msgs[i].getMessageBody();

                        Log.d("message body",msgBody);

                        if(msgBody.contains("<FLEET MANAGER ALERT SERVICE>"))
                        {
                            Intent dialogIntent = new Intent(context, AlertDialogActivity.class);
                            dialogIntent.putExtra("title" , "!!SOS Alert!!");
                            dialogIntent.putExtra("msg",msgBody);
                            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(dialogIntent);

                        }
                        else if(msgBody.contains("<FLEET MANAGER APP>"))
                        {
                            Intent dialogIntent = new Intent(context, AlertDialogActivity.class);
                            dialogIntent.putExtra("title" , "!!Paasword Reset Donet!!");
                            dialogIntent.putExtra("msg",msgBody);
                            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(dialogIntent);

                        }
                    }





                }catch(Exception e){
                            Log.d("Exception caught",e.getMessage());
                }
            }
        }
    }
}
