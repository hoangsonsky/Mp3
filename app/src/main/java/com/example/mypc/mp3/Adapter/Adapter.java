package com.example.mypc.mp3.Adapter;

import android.app.Activity;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.example.mypc.mp3.Other.DialogAddData;
import com.example.mypc.mp3.Entity.EntitySong;
import com.example.mypc.mp3.Other.MyViewHoder;
import com.example.mypc.mp3.Other.OnClickMS;
import com.example.mypc.mp3.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by MyPC on 31/07/2016.
 */
public class Adapter extends RecyclerView.Adapter<MyViewHoder> {
    ArrayList<EntitySong> arrayList = new ArrayList<>();
    ArrayList<EntitySong> search;
    Activity mContext;
    OnClickMS onClickMS;
    Button btnSearch;

    public Adapter(ArrayList<EntitySong> arrayList, Activity mContext,
                   OnClickMS onClickMS, Button btn) {
        this.arrayList = arrayList;
        this.mContext = mContext;
        this.onClickMS = onClickMS;
        search = new ArrayList<EntitySong>();
        search.addAll(arrayList);
        this.btnSearch = btn;
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
                        DialogAddData addData = new DialogAddData(mContext);
                        addData.listName(position, arrayList.get(position),2);;
//                        listName(position, item);
                        break;
                    case 2:
                        int idSong = Integer.parseInt(arrayList.get(position).getId());
                        RingtoneManager manager = new RingtoneManager(mContext);
                        Uri playableUri = Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                                String.valueOf(idSong));
                        manager.setActualDefaultRingtoneUri(mContext, RingtoneManager.TYPE_RINGTONE, playableUri);
                        break;
                    case 3:
                        File file = new File(arrayList.get(position).getData());
                        file.delete();
                        arrayList.remove(position);
                        notifyDataSetChanged();
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

    public void getFilter(String charText) {

        charText = charText.toLowerCase(Locale.getDefault());

        arrayList.clear();
        if (charText.length() == 0) {
            arrayList.addAll(search);

        } else {
            for (EntitySong entity : search) {
                if (charText.length() != 0 && entity.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    arrayList.add(entity);
                    btnSearch.setVisibility(View.GONE);
                } else if (charText.length() != 0 && entity.getArtist().toLowerCase(Locale.getDefault()).contains(charText)) {
                    arrayList.add(entity);
                    btnSearch.setVisibility(View.GONE);
                }
                if (arrayList.size() == 0) {
                    btnSearch.setVisibility(View.VISIBLE);
                }
            }
        }
        notifyDataSetChanged();
    }

}

