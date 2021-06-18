package solversteam.aveway.utiltes;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

import solversteam.aveway.Activities.SplashActivity;
import solverteam.aveway.R;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FCM Service";
    private String title, image;
    private PendingIntent pendingIntent;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());


        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            try {
                JSONObject jsonObject = new JSONObject(remoteMessage.getData());
                Iterator<String> iter = jsonObject.keys();

                while (iter.hasNext()) {

                    String key = iter.next();
                    if (key.equals("title")) {
                        title = jsonObject.getString("title");
                    }
//                    else if(key.equals("image"))
//                    {
//                        image=jsonObject.getString("image")
//                    }
                    Log.d("chechiterjson", key);
                }

                try {
                    Log.d("checkjsonfcm", jsonObject.getString("language") + "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody().toString(), title);
        }


    }

    private void sendNotification(String messageBody, String title) {
        final int notifId = 1337;
//

        Intent intent = new Intent(this, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_SINGLE_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK);

        if (isAppRunning(this, "solversteam.aveway")) {
            // App is running
            Log.d("checkrunning","Yes");
             intent = new Intent();
            pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);

        } else {
            // App is not running
             pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);
            Log.d("checkrunning","No");

        }

//             pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                    0);
//        PackageManager pm = getPackageManager();
//        Intent launchIntent = pm.getLaunchIntentForPackage("solversteam.aveway");
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, launchIntent, 0);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        final Notification notification = new NotificationCompat.Builder(this)
                .setContentText(messageBody)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.appicon)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .build();
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notification);


    }

//    public static class NotificationActionService extends IntentService {
//        public NotificationActionService() {
//            super(NotificationActionService.class.getSimpleName());
//        }
//
//        @Override
//        protected void onHandleIntent(Intent intent) {
//            Log.d("hereaa","Yes");
//
//            String action = intent.getAction();
//            Log.d("Received notification action: ", action.toString());
//            if (ACTION_1.equals(action)) {
//                Log.d("here","Yes");
//
//                // TODO: handle action 1.
//                if (isAppRunning(this, "solversteam.aveway")) {
//                    // App is running
//                    Log.d("checkrunning","Yes");
//                } else {
//                    // App is not running
//                    Log.d("checkrunning","No");
//
//                }
//
//                // If you want to cancel the notification: NotificationManagerCompat.from(this).cancel(NOTIFICATION_ID);
//            }
//        }
//
//
//    }

        public static boolean isAppRunning(final Context context, final String packageName) {
            final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            final List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
            if (procInfos != null)
            {
                for (final ActivityManager.RunningAppProcessInfo processInfo : procInfos) {
                    if (processInfo.processName.equals(packageName)) {
                        return true;
                    }
                }
            }
            return false;
        }

}
