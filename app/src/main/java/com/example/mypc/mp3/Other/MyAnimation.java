package com.example.mypc.mp3.Other;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.widget.ImageView;

/**
 * Created by MyPC on 04/07/2016.
 */
public class MyAnimation {//extends AlphaAnimation

    private ObjectAnimator mObjectAnimator;
    private Context mContext;

    public MyAnimation(ObjectAnimator mObjectAnimator, Context mContext) {
        this.mObjectAnimator = mObjectAnimator;
        this.mContext = mContext;
    }

    public void playAnimation(ImageView imgClockwise) {
        mObjectAnimator = ObjectAnimator.ofFloat(imgClockwise, "rotation", 0f, 360f);
        mObjectAnimator.setDuration(30000);
        mObjectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mObjectAnimator.start();
    }


    public void pauseAnimation() {
        if (mObjectAnimator != null) {
            mObjectAnimator.pause();
        }
    }


    public void resumeAnimation() {
        if (mObjectAnimator != null) {
            mObjectAnimator.resume();
        }
    }
}
