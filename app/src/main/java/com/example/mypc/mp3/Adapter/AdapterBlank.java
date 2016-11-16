package com.example.mypc.mp3.Adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mypc.mp3.Entity.EntityBlank;
import com.example.mypc.mp3.Other.MyAnimation;
import com.example.mypc.mp3.R;

import java.util.ArrayList;


/**
 * Created by MyPC on 15/07/2016.
 */
public class AdapterBlank extends BaseAdapter {
    ArrayList<EntityBlank> arrayList;
    int mPosition;
    MyAnimation myAnimation;
    ObjectAnimator imageViewObjectAnimator;
    Context mContext;

    public AdapterBlank(ArrayList<EntityBlank> arrayList, int position, MyAnimation myAnimation, Context context) {
        this.arrayList = arrayList;
        this.mPosition = position;
        this.myAnimation = myAnimation;
        this.mContext = context;
    }

    private class MyViewHolder {
        ImageView img1, img2, img3;
        TextView tvName;
        TextView tvSinger;
        LinearLayout layout;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder viewHolder = null;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.item_music_layout, null);

            viewHolder = new MyViewHolder();
            viewHolder.img1 = (ImageView) convertView.findViewById(R.id.imgAnimator1);
            viewHolder.img2 = (ImageView) convertView.findViewById(R.id.imgAnimator2);
            viewHolder.img3 = (ImageView) convertView.findViewById(R.id.imgAnimator3);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            viewHolder.tvSinger = (TextView) convertView.findViewById(R.id.tvSinger);
            viewHolder.layout = (LinearLayout) convertView.findViewById(R.id.linear);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (MyViewHolder) convertView.getTag();

        EntityBlank item = (EntityBlank) getItem(position);
        viewHolder.layout.setVisibility(View.INVISIBLE);
        viewHolder.tvName.setText(item.getName());
        viewHolder.tvSinger.setText(item.getSinger());

        viewHolder.img1.setVisibility(View.INVISIBLE);
        viewHolder.img2.setVisibility(View.INVISIBLE);
        viewHolder.img3.setVisibility(View.INVISIBLE);

        if (mPosition == position) {
            viewHolder.layout.setVisibility(View.VISIBLE);
            Animation slideUp = AnimationUtils.loadAnimation(mContext, R.anim.main1);
            viewHolder.img1.startAnimation(slideUp);
            viewHolder.img1.setVisibility(View.VISIBLE);
            final MyViewHolder finalViewHolder = viewHolder;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Animation slideUp3 = AnimationUtils.loadAnimation(mContext, R.anim.main1);
                    finalViewHolder.img3.startAnimation(slideUp3);
                    finalViewHolder.img3.setVisibility(View.VISIBLE);
                }
            }, 1000);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Animation slideUp2 = AnimationUtils.loadAnimation(mContext, R.anim.main1);
                    finalViewHolder.img2.startAnimation(slideUp2);
                    finalViewHolder.img2.setVisibility(View.VISIBLE);
                }
            }, 500);
        }

        return convertView;
    }

}
