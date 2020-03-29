package com.zhengsr.androiddemo.activity;

import android.app.NotificationManager;
import android.app.PendingIntent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.zhengsr.androiddemo.R;

public class NotificationActivity extends AppCompatActivity {
    private static final String TAG = "NotificationActivity";
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;

    private final String REPLY_KEY = "key_reply_text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        IntentFilter filter = new IntentFilter();
        filter.addAction("com.zhengsr.test");
        registerReceiver(new MyBroadcastReceiver(),filter);

        Intent intent = new Intent(this, SecondActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);









       /* mBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("紧急通知")
                .setContentText("这是一条紧急通知")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                //.setDefaults(NotificationCompat.DEFAULT_SOUND)
                //.setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
        .setFullScreenIntent(pi,true)
                .setAutoCancel(true)
               // .addAction(getRemoveAction())
               // .addAction(R.mipmap.enter,"上一曲",pi)
               // .addAction(R.mipmap.enter,"开始",pi)
                //.addAction(R.mipmap.enter,"下一曲",pi)
               // .setProgress(100,50,false)


         ;*/

       cusNotification();

    }


    /**
     * 自定义通知栏
     */
    private void cusNotification(){

        RemoteViews cusRemoveView = new RemoteViews(getPackageName(),R.layout.cus_notify_small);
        RemoteViews cusRemoveExpandView = new RemoteViews(getPackageName(),R.layout.cus_notify_large);

        mBuilder = new NotificationCompat.Builder(this)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
            //    .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(cusRemoveView)
                .setCustomBigContentView(cusRemoveExpandView);




    }

   private NotificationCompat.Action getRemoveAction(){
        //添加一个回复组件，

        RemoteInput remoteInput = new RemoteInput.Builder(REPLY_KEY)
                .build();

        //添加一个 pendingIntent 的广播
        PendingIntent replyPi = PendingIntent.getBroadcast(this,2,
                new Intent("com.zhengsr.test"),PendingIntent.FLAG_UPDATE_CURRENT);

        //构建 action
        NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.mipmap.enter,
                "回复",replyPi).addRemoteInput(remoteInput).build();

        return action;

    }

    public void notify(View view) {
        mNotificationManager.notify(1, mBuilder.build());
    }

    public void update(View view) {
        mBuilder.setContentText("我更新了内容");
       // mBuilder.setProgress(0,0,false);
        mNotificationManager.notify(1, mBuilder.build());
    }

    public void cancel(View view) {
        mNotificationManager.cancel(1);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)


    class  MyBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = RemoteInput.getResultsFromIntent(intent);
            if (bundle != null) {
                String replay = bundle.getString(REPLY_KEY);
                Log.d(TAG, "zsr onReceive: "+replay);
            }
        }
    }
}
