package com.example.mypc.mp3.Other;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.mypc.mp3.R;

/**
 * Created by MyPC on 01/10/2016.
 */
public class Constants {
    public interface ACTION {
        public static String PLAY_ACTION = "com.truiton.foregroundservice.action.main";
        public static String PREV_ACTION = "com.truiton.foregroundservice.action.prev";
        public static String PAUSE = "com.truiton.foregroundservice.action.play";
        public static String NEXT_ACTION = "com.truiton.foregroundservice.action.next";
        public static String STARTFOREGROUND_ACTION = "com.truiton.foregroundservice.action.startforeground";
        public static String STOPFOREGROUND_ACTION = "com.truiton.foregroundservice.action.stopforeground";
        public static String UPDATE_SEEKBAR = "com.truiton.foregroundservice.action.update";
        public static String LOOP = "com.truiton.foregroundservice.action.loop";
        public static String RANDOM = "com.truiton.foregroundservice.action.random";
        public static String MAIN_ACTION = "com.truiton.foregroundservice.action.main1";
//        String MAIN_ACTION = ;
    }

    public interface NOTIFICATION_ID {
        public static int FOREGROUND_SERVICE = 101;
    }

    public static Bitmap getDefaultAlbumArt(Context context) {
        Bitmap bm = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        try {
            bm = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.default_album_art, options);
        } catch (Error ee) {
        } catch (Exception e) {
        }
        return bm;
    }
}
