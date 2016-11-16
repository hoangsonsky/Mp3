package com.example.mypc.mp3.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.mypc.mp3.R;

/**
 * Created by MyPC on 5/10/2016.
 */
public class ViewPlayMusicXML extends LinearLayout {
    public ViewPlayMusicXML(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_no_seekbar, null);
        addView(view);
    }
}
