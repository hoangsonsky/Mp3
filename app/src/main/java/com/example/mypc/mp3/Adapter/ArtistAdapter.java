package com.example.mypc.mp3.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.mypc.mp3.Other.DialogAddData;
import com.example.mypc.mp3.Entity.EntitySong;
import com.example.mypc.mp3.Other.MyViewHoder;
import com.example.mypc.mp3.Other.OnClickMS;
import com.example.mypc.mp3.R;

import java.util.ArrayList;

/**
 * Created by MyPC on 31/07/2016.
 */
public class ArtistAdapter extends RecyclerView.Adapter<MyViewHoder> {
    ArrayList<EntitySong> arrayList = new ArrayList<>();
    Activity mContext;
    OnClickMS onClickMS;

    public ArtistAdapter(ArrayList<EntitySong> arrayList, Activity mContext, OnClickMS onClickMS) {
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
                        if (onClickMS != null) {
                            onClickMS.onClick123(false,position);
                        }
                        break;
                    case 2:
                        DialogAddData addData = new DialogAddData(mContext);
                        addData.listName(position, arrayList.get(position),1);;
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

}

