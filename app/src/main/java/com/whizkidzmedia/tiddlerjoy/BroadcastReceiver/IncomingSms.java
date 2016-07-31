package com.whizkidzmedia.tiddlerjoy.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.whizkidzmedia.tiddlerjoy.ParentInterface.ParentSignupActivity;
import com.whizkidzmedia.tiddlerjoy.Utilities.DialogBox;

/**
 * Created by Sourabh Dixit on 07-07-2016.
 */
public class IncomingSms extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        final Bundle bundle = intent.getExtras();
        try{
            if(bundle!=null)
            {
                final Object[] pdusObj = (Object[])bundle.get("pdus");
                for(int i=0;i<pdusObj.length;i++)
                {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[])pdusObj[i]);
                    String phoneNumber = smsMessage.getDisplayOriginatingAddress();
                    String senderNumber = phoneNumber.toString();
                    String message = smsMessage.getDisplayMessageBody().toString();
                    try{
                        if(senderNumber.contains("YOUHUU"))
                        {
                            String[] newMessage = message.split(" ");
                            ParentSignupActivity obj = new ParentSignupActivity();
                            obj.receivedSms(newMessage[newMessage.length-1]);
//                            new DialogBox(context,"Number Verified otp : "+ newMessage[newMessage.length-1]);
                        }
                        else
                        {
                            int length = phoneNumber.length();
                            Toast.makeText(context,"",Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch(Exception ex){
                        Toast.makeText(context,"exception"+ex,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        catch (Exception ex){}
    }
}
