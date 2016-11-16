package com.example.mypc.mp3.Other;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mypc.mp3.Entity.EntitySong;

import java.util.ArrayList;

/**
 * Created by MyPC on 09/10/2016.
 */
public class DialogAddData {

    Activity mContext;

    public DialogAddData(Activity mContext) {
        this.mContext = mContext;
    }

    public void listName(final int positon, final EntitySong entity, final int stt) {


        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("List");

        DataPLayList pLayList = new DataPLayList(mContext);
        final ArrayList<String> stringArrayList = pLayList.getListNamefile();
        ArrayAdapter adapter = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, stringArrayList);
        ListView listView = new ListView(mContext);
        listView.setAdapter(adapter);

        builder.setView(listView);
        builder.setIcon(android.R.drawable.ic_input_add);

        builder.setNegativeButton("New", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addPlayList(stt, entity);
            }
        });

        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog dialog = builder.show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                insertData(entity, stringArrayList.get(position),stt);
                dialog.cancel();
            }
        });
        if (stringArrayList.size() == 0) {
            dialog.cancel();
            addPlayList(stt, entity);
        }
    }


    private void addPlayList(final int stt, final EntitySong entity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Add");

        final EditText input = new EditText(mContext);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        input.setLayoutParams(lp);

        builder.setView(input);
        builder.setIcon(android.R.drawable.ic_input_add);
        builder.setMessage("Tao playList moi");

        builder.setNeutralButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                DataPLayList pLayList = new DataPLayList(mContext);
                if (pLayList.checkFileName(input.getText().toString()) == false)
                    insertData(entity, input.getText().toString(),stt);
                else
                    Toast.makeText(mContext, "Tên này bị trùng.", Toast.LENGTH_SHORT).show();

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

    public void insertData(EntitySong entity, String name, int stt) {
        DataPLayList pLayList = new DataPLayList(mContext);
        InfoSong infoSong = new InfoSong(mContext);
        Cursor cursor = infoSong.getCursor();
        while (cursor.isAfterLast() == false) {
            if (entity.getName().equals(cursor.getString(stt))) {
                pLayList.insertDataMusic(String.valueOf(cursor.getInt(0)), cursor.getString(2), cursor.getString(1), name);
            }
            cursor.moveToNext();
        }
    }
}
