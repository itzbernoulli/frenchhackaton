package com.oyinloyeayodeji.www.frenchhackaton.Services;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.oyinloyeayodeji.www.frenchhackaton.ChatRoomActivity;
import com.oyinloyeayodeji.www.frenchhackaton.Managers.MyNotificationManager;


import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Data: " + remoteMessage.getData()); //TODO: get transaction id from map
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

        //Get values from the notification part
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();

        //Get values from the data part.
        Map<String, String> data = remoteMessage.getData();
        String transactionId = data.get("transactionId");
        String status = data.get("status");
        String requestToken = data.get("requestToken");
        String requesterId = data.get("requesterId");
        String requestAmount = data.get("requestAmount");
        //TODO: You can get the data from the map and run a switch to determine which notifyUser method to call.
        //TODO: All notify user methods can call different activities based on the switch scenario
        switch (status){
            case "requesting":
//                notifyUserRequest(title,body,transactionId,requestToken,requesterId,requestAmount);
                break;
            case "paired":
                notifyUserPairing(title,body,transactionId);
                break;
        }
    }

//    public void notifyUserRequest(String from, String notification, String transactionId, String requestToken,String requesterId,String requestAmount){
//        MyNotificationManager myNotificationManager = new MyNotificationManager(getApplicationContext());
//        Intent i = new Intent(getApplicationContext(),TestActivity.class);
//        //TODO: add transaction id extra
//        i.putExtra("transId",transactionId);
//        i.putExtra("requestToken",requestToken);
//        i.putExtra("requesterId",requesterId);
//        i.putExtra("requestAmount",requestAmount);
//        myNotificationManager.showNotification(from, notification, i);
//    }

    public void notifyUserPairing(String from, String notification, String transactionId){
        MyNotificationManager myNotificationManager = new MyNotificationManager(getApplicationContext());
        Intent i = new Intent(getApplicationContext(),ChatRoomActivity.class);
        //TODO: add transaction id extra
        i.putExtra("transactionId",transactionId);
        i.putExtra("paired",true);
        i.putExtra("debitor",true);
        i.putExtra("creditor",false);
        myNotificationManager.showNotification(from, notification, i);
    }
}