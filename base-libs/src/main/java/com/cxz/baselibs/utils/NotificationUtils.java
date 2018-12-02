package com.cxz.baselibs.utils;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.cxz.baselibs.base.BaseActivity;

import static android.app.Notification.VISIBILITY_SECRET;
import static android.support.v4.app.NotificationCompat.PRIORITY_DEFAULT;

/**
 * NotificationUtils
 */
public class NotificationUtils extends ContextWrapper {
    public String CHANNEL_ID = "default";
    private String CHANNEL_NAME = "Default Channel";
    private static final String CHANNEL_DESCRIPTION = "this is default channel!";
    private NotificationManager mManager;

    public NotificationUtils(Context base, String CHANNEL_ID, String CHANNEL_NAME) {
        super(base);
        this.CHANNEL_ID = CHANNEL_ID;
        this.CHANNEL_NAME = CHANNEL_NAME;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        channel.canBypassDnd();//是否绕过请勿打扰模式
        channel.enableLights(true);//闪光灯
        channel.setLockscreenVisibility(VISIBILITY_SECRET);//锁屏显示通知
        channel.setLightColor(Color.RED);//闪关灯的灯光颜色
        channel.canShowBadge();//桌面launcher的消息角标
        channel.enableVibration(true);//是否允许震动
        channel.getAudioAttributes();//获取系统通知响铃声音的配置
        channel.getGroup();//获取通知取到组
        channel.setBypassDnd(true);//设置可绕过  请勿打扰模式
        channel.setVibrationPattern(new long[]{100, 100, 200});//设置震动模式
        channel.shouldShowLights();//是否会有灯光
        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public void sendNotification(String title, String content) {
        NotificationCompat.Builder builder = getNotification(title, content);
        getManager().notify(1, builder.build());
    }

    public NotificationCompat.Builder getNotification(String title, String content) {
        Intent mainIntent = new Intent(this, BaseActivity.class);
        mainIntent.putExtra("title", title);
        PendingIntent mainPendingIntent = PendingIntent.getActivity(this, (int) (Math.random() * 100), mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        } else {
            builder = new NotificationCompat.Builder(getApplicationContext());
            builder.setPriority(PRIORITY_DEFAULT);
        }
        builder.setContentTitle(title);
        builder.setContentIntent(mainPendingIntent);
        builder.setContentText(content);
        builder.setColor(Color.parseColor("#052de1"));
        builder.setSmallIcon(android.R.mipmap.sym_def_app_icon);
        //点击自动删除通知
        builder.setAutoCancel(true);
        return builder;
    }

    public void sendNotification(int notifyId, String title, String content) {
        NotificationCompat.Builder builder = getNotification(title, content);
        getManager().notify(notifyId, builder.build());
    }
}
