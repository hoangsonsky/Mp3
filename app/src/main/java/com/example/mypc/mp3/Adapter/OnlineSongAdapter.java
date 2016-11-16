package com.example.mypc.mp3.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.mypc.mp3.Other.DownloadMusic;
import com.example.mypc.mp3.Entity.EntitySong;
import com.example.mypc.mp3.Other.MyViewHoder;
import com.example.mypc.mp3.Other.OnClickMS;
import com.example.mypc.mp3.R;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.util.ArrayList;

/**
 * Created by MyPC on 31/07/2016.
 */
public class OnlineSongAdapter extends RecyclerView.Adapter<MyViewHoder> {
    ArrayList<EntitySong> arrayList = new ArrayList<>();
    Activity mContext;
    OnClickMS onClickMS;


    public OnlineSongAdapter(ArrayList<EntitySong> arrayList, Activity mContext,
                             OnClickMS onClickMS) {
        this.arrayList = arrayList;
        this.mContext = mContext;
        this.onClickMS = onClickMS;

    }

    @Override
    public MyViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_view_layout,null);
        return new MyViewHoder(view);
    }


    @Override
    public void onBindViewHolder(MyViewHoder holder, final int position) {
        holder.tv_name.setText(arrayList.get(position).getName());
        holder.tv_artist.setText(arrayList.get(position).getArtist());
        holder.spinner.setAdapter(arrayList.get(position).getAdapter());
        holder.icon.setImageResource(arrayList.get(position).getIcon());

        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int positionItem, long id) {

                switch (positionItem) {
                    case 0:
                        break;
                    case 1:
                        String link = arrayList.get(position).getId();
                        Log.e("sssssssssssss",link);
                        String name = arrayList.get(position).getName() + ".mp3";
                        name = name.replace(" ","");
                        DownloadMusic downloadMusic = new DownloadMusic(name, link, mContext);
                        break;
                    case 2:
                        FacebookSdk.sdkInitialize(mContext);
                        AppEventsLogger.activateApp(mContext);
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_TEXT,
                                arrayList.get(position).getData().toString());
                        mContext.startActivity(Intent.createChooser(intent, "Share with"));
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickMS != null) {
                    onClickMS.onClick123(true,position);
                }

            }
        });
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

//    public void getFilter(String charText) {
//        charText = charText.toLowerCase(Locale.getDefault());
//
//        arrayList.clear();
//            for (Entity entity : arrayList) {
//                if (charText.length() != 0 && entity.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
//                    arrayList.add(entity);
//                }
//            }
//        notifyDataSetChanged();
//    }

//    private void setAnimation(View viewToAnimate, int position)
//    {
//        // If the bound view wasn't previously displayed on screen, it's animated
//        if (position > lastPosition)
//        {
//            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
//            viewToAnimate.startAnimation(animation);
//            lastPosition = position;
//        }
//    }
}

