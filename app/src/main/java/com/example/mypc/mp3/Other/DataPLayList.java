package com.example.mypc.mp3.Other;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.mypc.mp3.Entity.EntitySong;
import com.example.mypc.mp3.R;

import java.util.ArrayList;
/**
 * Created by MyPC on 19/05/2016.
 */
public class DataPLayList {
    private Context mContext;
    public static SQLiteDatabase database;

    public DataPLayList(Context context) {
        this.mContext = context;
        connectData();
    }

    public void connectData() {
        initDataBase();
        createTable();
    }

    public void initDataBase() {
        String data_name = "mp3.db";
        database = mContext.openOrCreateDatabase(data_name, Context.MODE_APPEND, null);
    }

    public void createTable() {
        String init = "CREATE TABLE IF NOT EXISTS `filemp3` (\n" +
                "\t`idsong`\tTEXT NOT NULL,\n" +
                "\t`NameSong`\tTEXT NOT NULL,\n" +
                "\t`Artist`\tTEXT NOT NULL,\n" +
                "\t`NameFile`\tTEXT NOT NULL,\n" +
                "\tPRIMARY KEY(idsong,NameFile)\n" +
                ");";
        database.execSQL(init);
    }

    public void insertDataMusic(String idsong, String name, String artist, String nameFile) {
        ContentValues values = new ContentValues();
        values.put("idsong", idsong);
        values.put("NameSong", name);
        values.put("Artist", artist);
        values.put("NameFile", nameFile);
        int status = (int) database.insert("filemp3", null, values);
        if (status == -1) {
            Toast.makeText(mContext, "insert fail", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(mContext, "insert success", Toast.LENGTH_SHORT).show();

    }

    public void deleteDataMusic(String name) {
        int status = (int) database.delete("filemp3", "NameFile=?", new String[]{name});
        if (status == -1) {
            Toast.makeText(mContext, "delete fail", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(mContext, "delete success", Toast.LENGTH_SHORT).show();
    }

    public void updateDataFile(String idsong, String nameFile, String name) {
        ContentValues values = new ContentValues();
        values.put("NameFile", nameFile);
        database.update("filemp3", values, "idsong =? and NameFile=?", new String[]{idsong, name});
    }

    public ArrayList<String> getListNamefile() {
        ArrayList<String> arrName = new ArrayList<>();
        arrName.add("");
        ArrayList<String> stringArrayList = new ArrayList<>();
        Cursor cursor = database.query("filemp3", null, null, null, null, null, null);
        cursor.moveToFirst();
        boolean check = true;
        while (cursor.isAfterLast() == false) {
            for (int i = 0; i < arrName.size(); i++) {
                if (cursor.getString(3).equals(arrName.get(i) + "")) {
                    check = false;
                    break;
                } else check = true;
            }
            if (check == true) {
                stringArrayList.add(cursor.getString(3));
                arrName.add(cursor.getString(3));
            }
            cursor.moveToNext();
        }
        return stringArrayList;
    }

    public void dataPlayList(ArrayList<EntitySong> arrayList) {
        String[] arr = new String[]{"", "play", "doi ten", "xoa"};
        ArrayAdapter adapter = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, arr);

        ArrayList<String> arrName = new ArrayList<>();
        arrName.add("");

        DataPLayList pLayList = new DataPLayList(mContext);
        Cursor cursor = pLayList.database.query("filemp3", null, null, null, null, null, null);
        cursor.moveToFirst();
        boolean check = true;
        while (cursor.isAfterLast() == false) {
            for (int i = 0; i < arrName.size(); i++) {
                if (cursor.getString(3).equals(arrName.get(i))) {
                    check = false;
                    break;
                } else check = true;
            }
            if (check == true) {
                EntitySong entity = new EntitySong();
                entity.setName(cursor.getString(3));
                entity.setAdapter(adapter);
                arrayList.add(entity);
                arrName.add(cursor.getString(3));
            }
            cursor.moveToNext();
        }

    }

    public int getCount() {
        int count = 0;
        ArrayList<String> arrName = new ArrayList<>();
        arrName.add("");

        DataPLayList pLayList = new DataPLayList(mContext);
        Cursor cursor = pLayList.database.query("filemp3", null, null, null, null, null, null);
        cursor.moveToFirst();
        boolean check = true;
        while (cursor.isAfterLast() == false) {
            for (int i = 0; i < arrName.size(); i++) {
                if (cursor.getString(3).equals(arrName.get(i))) {
                    check = false;
                    break;
                } else check = true;
            }
            if (check == true) {
                count++;
                arrName.add(cursor.getString(3));
            }
            cursor.moveToNext();
        }
        return count;
    }

    public boolean checkFileName(String name) {
        boolean check = false;
        DataPLayList pLayList = new DataPLayList(mContext);
        Cursor cursor = pLayList.database.query("filemp3", null, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            if (cursor.getString(3).equals(name)) {
                check = true;
                break;
            }
            cursor.moveToNext();
        }
        return check;
    }

    public ArrayList<EntitySong> data(String name) {
        ArrayList<EntitySong> arrayList = new ArrayList<>();
        Cursor cursor = database.query("filemp3", null, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            if (cursor.getString(3).equals(name)) {
                EntitySong entity = new EntitySong();
                entity.setArtist(cursor.getString(2));
                entity.setId(String.valueOf(cursor.getInt(0)));
                entity.setName(cursor.getString(1));
                entity.setIcon(R.drawable.dia);
                arrayList.add(entity);
            }
            cursor.moveToNext();
        }
        return  arrayList;
    }


}
