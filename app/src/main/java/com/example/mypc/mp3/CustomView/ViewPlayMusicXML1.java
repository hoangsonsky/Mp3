package com.example.mypc.mp3.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.mypc.mp3.R;

/**
 * Created by MyPC on 14/07/2016.
 */
public class ViewPlayMusicXML1 extends RelativeLayout {
    public ViewPlayMusicXML1(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.play_music_layout, null);
        addView(view);
    }
}
