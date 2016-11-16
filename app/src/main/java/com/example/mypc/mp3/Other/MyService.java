package com.example.mypc.mp3.Other;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.mypc.mp3.Entity.EntitySong;
import com.example.mypc.mp3.Fragment.MainActivity;
import com.example.mypc.mp3.Fragment.SongFragment;
import com.example.mypc.mp3.CustomView.ViewPlayMusic;
import com.example.mypc.mp3.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MyService extends Service {

    Notification status;
    RemoteViews views,bigViews;
    public static final String LOG_TAG = "MyService";
    public static final String PROGRESS_SB = "progress";
    public static final String DATA = "data";//DURATION_SONG
    public static final String DURATION_SONG = "duration";
    public static final String CURRENT_TIME = "current_time";
    public static final String FINAL_TIME = "final_time";
    public static final String POSITION = "position";
    public static final String TAG = "tag";
    public static final String STATUS_MEDIA = "status";
    public static final String CHECK_LOOP = "loop";
    private MediaPlayer mediaPlayer = new MediaPlayer();
    Handler handler = new Handler();
    SongApplication songApplication;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        songApplication = (SongApplication) getApplicationContext();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        try {
            if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
                showNotification();
            } else if (intent.getAction().equals(Constants.ACTION.PREV_ACTION)) {
                String id = prev();
                createMS(id);
            } else if (intent.getAction().equals(Constants.ACTION.PAUSE)) {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                } else
                    mediaPlayer.pause();

            } else if (intent.getAction().equals(Constants.ACTION.NEXT_ACTION)) {
                String id = nextMS();
                createMS(id);
            } else if (intent.getAction().equals(Constants.ACTION.STOPFOREGROUND_ACTION)) {
                System.exit(0);
                stopForeground(true);
                stopSelf();
//                System.exit(0);
//                Intent myIntent = new Intent(g, this);
//                this.stopService(myIntent);

            } else if (intent.getAction().equals(Constants.ACTION.PLAY_ACTION)) {
                Bundle bundle = intent.getExtras();
                String id = bundle.getString(SongFragment.POSITION);
                createMS(id);
            } else if (intent.getAction().equals(Constants.ACTION.UPDATE_SEEKBAR)) {
                Bundle bundle = intent.getExtras();
                int id = bundle.getInt(ViewPlayMusic.NEXT_SONG);
                fastForward(id);
            } else if (intent.getAction().equals(Constants.ACTION.LOOP)) {
                loopMusic();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return START_STICKY;
    }

    private String nextMS() {
        String id = "";
        if (0 <= songApplication.getPosition() &&
                songApplication.getPosition() < songApplication.getSongs().size() - 1) {
            songApplication.setPosition(songApplication.getPosition() + 1);
            id = songApplication.getSongs().
                    get(songApplication.getPosition()).getId() + "";

        }
        return id;
    }

    private String prev() {
        String id = "";
        if (0 < songApplication.getPosition() &&
                songApplication.getPosition() < songApplication.getSongs().size()) {
            songApplication.setPosition(songApplication.getPosition() - 1);

            id = songApplication.getSongs().
                    get(songApplication.getPosition()).getId() + "";

        }
        return id;
    }

    private void showNotification() {
         views = new RemoteViews(getPackageName(),
                R.layout.status_bar);
         bigViews = new RemoteViews(getPackageName(),
                R.layout.status_bar_expanded);

        views.setViewVisibility(R.id.status_bar_icon, View.VISIBLE);
        views.setViewVisibility(R.id.status_bar_album_art, View.GONE);
        bigViews.setImageViewBitmap(R.id.status_bar_album_art,
                Constants.getDefaultAlbumArt(this));

        Intent notificationIntent = new Intent(this, MainActivity.class);//
        notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        Intent previousIntent = new Intent(this, MyService.class);
        previousIntent.setAction(Constants.ACTION.PREV_ACTION);
        PendingIntent ppreviousIntent = PendingIntent.getService(this, 0,
                previousIntent, 0);

        Intent playIntent = new Intent(this, MyService.class);
        playIntent.setAction(Constants.ACTION.PAUSE);
        PendingIntent pplayIntent = PendingIntent.getService(this, 0,
                playIntent, 0);

        Intent nextIntent = new Intent(this, MyService.class);
        nextIntent.setAction(Constants.ACTION.NEXT_ACTION);
        PendingIntent pnextIntent = PendingIntent.getService(this, 0,
                nextIntent, 0);

        Intent closeIntent = new Intent(this, MyService.class);
        closeIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
        PendingIntent pcloseIntent = PendingIntent.getService(this, 0,
                closeIntent, 0);

        views.setOnClickPendingIntent(R.id.status_bar_play, pplayIntent);
        bigViews.setOnClickPendingIntent(R.id.status_bar_play, pplayIntent);

        views.setOnClickPendingIntent(R.id.status_bar_next, pnextIntent);
        bigViews.setOnClickPendingIntent(R.id.status_bar_next, pnextIntent);

        views.setOnClickPendingIntent(R.id.status_bar_prev, ppreviousIntent);
        bigViews.setOnClickPendingIntent(R.id.status_bar_prev, ppreviousIntent);

        views.setOnClickPendingIntent(R.id.status_bar_collapse, pcloseIntent);
        bigViews.setOnClickPendingIntent(R.id.status_bar_collapse, pcloseIntent);

        views.setImageViewResource(R.id.status_bar_play,
                R.drawable.apollo_holo_dark_pause);
        bigViews.setImageViewResource(R.id.status_bar_play,
                R.drawable.apollo_holo_dark_pause);

        int i = songApplication.getPosition();
        String name = songApplication.getSongs().get(i).getName();
        String artist = songApplication.getSongs().get(i).getArtist();
        views.setTextViewText(R.id.status_bar_track_name, name);
        bigViews.setTextViewText(R.id.status_bar_track_name, name);

        views.setTextViewText(R.id.status_bar_artist_name, artist);
        bigViews.setTextViewText(R.id.status_bar_artist_name, artist);

        bigViews.setTextViewText(R.id.status_bar_album_name, "");

        status = new Notification.Builder(this).build();
        status.contentView = views;
        status.bigContentView = bigViews;
        status.flags = Notification.FLAG_ONGOING_EVENT;
        status.icon = R.drawable.ic_launcher;
        status.contentIntent = pendingIntent;
        startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, status);
    }

    public void createMS(String id) {
        songApplication.setCheckNull(true);
        Uri uri;
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.reset();
        if (songApplication.isCheckOnOrOff()) {
            uri = Uri.parse(id);
        } else {
            uri = Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
        }
        try {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(MyService.this, uri);
            mediaPlayer.prepare();
            sendPosition();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                return true;
            }
        });

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onPrepared(MediaPlayer mp) {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            sendProgress();
                            handler.postDelayed(this, 1);
                        }
                    };
                    handler.postDelayed(runnable, 1);
                }
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                int position = songApplication.getPosition();
                ArrayList<EntitySong> arrayList = songApplication.getSongs();
                if (position < arrayList.size() - 1) {
                    songApplication.setPosition(position + 1);
                    createMS(arrayList.get(position + 1).getId());
                } else {
                    songApplication.setPosition(0);
                    createMS(arrayList.get(0).getId());
                }
            }
        });
        showNotification();
    }

    private void sendProgress() {
        Intent intent = new Intent(LOG_TAG);
        Bundle bundle = new Bundle();
        int progress = getProgress();
        bundle.putInt(PROGRESS_SB, progress);
        bundle.putString(CURRENT_TIME, currentTime());
        bundle.putString(FINAL_TIME, findnalTime());
        bundle.putBoolean(STATUS_MEDIA, statusMedia());
        bundle.putBoolean(CHECK_LOOP, checkLoop());
        bundle.putLong(DURATION_SONG, mediaPlayer.getDuration());
        intent.putExtra(DATA, bundle);
        sendBroadcast(intent);
    }

    private void sendPosition() {
        Intent intent = new Intent(TAG);
        Bundle bundle = new Bundle();
        bundle.putString(TAG, TAG);
        intent.putExtras(bundle);
        sendBroadcast(intent);
    }

    public int getProgress() {
        long current = mediaPlayer.getCurrentPosition();
        long duration = mediaPlayer.getDuration();
        int progress = getProgressPercentag(current, duration);
        return progress;

    }

    public int getProgressPercentag(long current, long total) {
        Double percentage = (double) 0;
        long curentSeconds = (int) (current / 1000);
        long totalseconds = (int) (total / 1000);
        percentage = (((double) curentSeconds) / totalseconds) * 100;
        if ((total - current) < 60) {
            return 100;
        }
        return percentage.intValue();
    }

    public void fastForward(int pos) {
        mediaPlayer.seekTo(pos);
    }

    public String currentTime() {
        String current = "";
        int currentTime = mediaPlayer.getCurrentPosition();
        long s = TimeUnit.MILLISECONDS.toSeconds((long) currentTime) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) currentTime));
        long m = TimeUnit.MILLISECONDS.toMinutes((long) currentTime);
        current = m + ":" + s;
        return current;
    }

    public String findnalTime() {
        String time = "";
        int findnalTime = mediaPlayer.getDuration();
        time = TimeUnit.MILLISECONDS.toMinutes((long) findnalTime) + ":" +
                (TimeUnit.MILLISECONDS.toSeconds((long) findnalTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) findnalTime)));
        return time;
    }

    public boolean statusMedia() {
        boolean b = false;
        if (mediaPlayer.isPlaying())
            b = true;
        return b;
    }

    public void loopMusic() {
        if (mediaPlayer.isLooping()) {
            mediaPlayer.setLooping(false);
        } else
            mediaPlayer.setLooping(true);
        Toast.makeText(MyService.this, mediaPlayer.isLooping() + "", Toast.LENGTH_SHORT).show();
    }

    public boolean checkLoop() {
        boolean b = false;
        if (mediaPlayer.isLooping())
            b = true;
        return b;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }
}
