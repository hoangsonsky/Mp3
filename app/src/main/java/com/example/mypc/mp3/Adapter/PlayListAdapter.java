package com.example.mypc.mp3.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.mypc.mp3.Other.DataPLayList;
import com.example.mypc.mp3.Entity.EntitySong;
import com.example.mypc.mp3.Other.MyViewHoder;
import com.example.mypc.mp3.Other.OnClickMS;
import com.example.mypc.mp3.R;

import java.util.ArrayList;

/**
 * Created by MyPC on 31/07/2016.
 */
public class PlayListAdapter extends RecyclerView.Adapter<MyViewHoder> {
    ArrayList<EntitySong> arrayList = new ArrayList<>();
    Activity mContext;
    OnClickMS onClickMS;


    public PlayListAdapter(ArrayList<EntitySong> arrayList, Activity mContext,
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
                DataPLayList pLayList = new DataPLayList(mContext);
                switch (positionItem) {
                    case 0:
                        break;
                    case 1:
                        if (onClickMS != null) {
                            onClickMS.onClick123(false,position);
                        }
                        break;
                    case 2:
                        updateNameFile(arrayList.get(position));
                        notifyDataSetChanged();
                        break;
                    case 3:
                        Cursor cursor = DataPLayList.database.query("filemp3", null, null, null, null, null, null);
                        cursor.moveToFirst();
                        while (cursor.isAfterLast() == false) {
                            if (arrayList.get(position).getName().equals(cursor.getString(3))) {
                                pLayList.deleteDataMusic(cursor.getString(3));
                                arrayList.remove(position);
                                notifyDataSetChanged();
                                break;
                            }
                            cursor.moveToNext();
                        }
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


    private void updateNameFile(final EntitySong entity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Update Title");

        final EditText input = new EditText(mContext);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        input.setLayoutParams(lp);
        builder.setView(input);
        builder.setIcon(android.R.drawable.ic_input_add);
        builder.setMessage("New tilte");

        builder.setNeutralButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DataPLayList pLayList = new DataPLayList(mContext);
                Cursor cursor = DataPLayList.database.query("filemp3", null, null, null, null, null, null);
                cursor.moveToFirst();
                while (cursor.isAfterLast() == false) {
                    if (entity.getName().equals(cursor.getString(3))) {
                        pLayList.updateDataFile(cursor.getString(0), input.getText().toString(), cursor.getString(3));
                        notifyDataSetChanged();
                    }
                    cursor.moveToNext();
                }
            }
        });

        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }
}

