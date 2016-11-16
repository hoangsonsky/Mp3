package com.example.mypc.mp3.Other;

import android.app.Activity;
import android.database.Cursor;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;

import com.example.mypc.mp3.Entity.EntitySong;
import com.example.mypc.mp3.R;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;


/**
 * Created by MyPC on 04/07/2016.
 */
public class InfoSong {
    Activity mActivity;

    public InfoSong(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public Cursor getCursor() {
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM
        };

        Cursor cursor = mActivity.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                null);
        cursor.moveToFirst();
        return cursor;
    }

    public String converTitle(Cursor cursor) {
        Charset charsetE = Charset.forName("iso-8859-1");
        CharsetEncoder encoder = charsetE.newEncoder();

        Charset charsetD = Charset.forName("UTF-8");
        CharsetDecoder decoder = charsetD.newDecoder();
        String result = null;
        ByteBuffer bbuf = null;
        try {
            bbuf = encoder.encode(CharBuffer.wrap(cursor.getString(2)));
            CharBuffer cbuf = decoder.decode(bbuf);
            result = cbuf.toString();
        } catch (CharacterCodingException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void dataSongFragment(ArrayList arrayList) {

        String[] arr = new String[]{"", "them vao playlist", "cai lam nhac chuong", "xoa"};
        ArrayAdapter adapter = new ArrayAdapter(mActivity, android.R.layout.simple_list_item_1, arr);
        Cursor cursor = getCursor();
        while (cursor.isAfterLast() == false) {
            EntitySong entity = new EntitySong();
            entity.setArtist(cursor.getString(1));
            entity.setId(String.valueOf(cursor.getInt(0)));
            entity.setName(cursor.getString(2));
            entity.setData(cursor.getString(3));
            entity.setIcon(R.drawable.dia);
            entity.setAdapter(adapter);
            arrayList.add(entity);
            cursor.moveToNext();
        }
    }



        public void dataPlayList(String a, ArrayList arrayList) {

        String[] arr = new String[]{"", "them vao playlist", "cai lam nhac chuong", "xoa"};
        ArrayAdapter adapter = new ArrayAdapter(mActivity, android.R.layout.simple_list_item_1, arr);

        DataPLayList pLayList = new DataPLayList(mActivity);
        pLayList.connectData();
        Cursor cursor1 = pLayList.database.query("filemp3", null, null, null, null, null, null, null);
        cursor1.moveToFirst();

        Cursor cursor = getCursor();
        cursor.moveToFirst();
        while (cursor1.isAfterLast() == false) {
            if (cursor1.getString(3).equals(a)) {

                cursor.moveToFirst();
                while (cursor.isAfterLast() == false) {
                    if (cursor1.getString(0).equals(cursor.getString(0))) {
                        EntitySong entity = new EntitySong();
                        entity.setArtist(cursor.getString(1));
                        entity.setId(String.valueOf(cursor.getInt(0)));
                        entity.setName(cursor.getString(2));
                        entity.setData(cursor.getString(3));
                        entity.setAdapter(adapter);
//                        entity.setId(Integer.parseInt(cursor.getString(0)));
                        arrayList.add(entity);
                    }
                    cursor.moveToNext();
                }

            }
            cursor1.moveToNext();
        }
    }


    public ArrayList<EntitySong> getData(int x, String artist) {// 6 name
        ArrayList<EntitySong> arrayList = new ArrayList<>();
        Cursor cursor = getCursor();
        while (cursor.isAfterLast() == false) {
            if (cursor.getString(x).equals(artist)) {
                EntitySong entity = new EntitySong();
                entity.setArtist(cursor.getString(1));
                entity.setId(String.valueOf(cursor.getInt(0)));
                entity.setName(cursor.getString(2));
                entity.setData(cursor.getString(3));
                entity.setIcon(R.drawable.dia);
                arrayList.add(entity);
            }
            cursor.moveToNext();
        }
        return arrayList;
    }

    public void dataSendSong(String album, int stt, ArrayList arrayList) {

        String[] arr = new String[]{"", "them vao playlist", "cai lam nhac chuong", "xoa"};
        ArrayAdapter adapter = new ArrayAdapter(mActivity, android.R.layout.simple_list_item_1, arr);

        Cursor cursor = getCursor();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            if (cursor.getString(stt).equals(album)) {
                EntitySong entity = new EntitySong();
                entity.setArtist(cursor.getString(1));
                entity.setName(cursor.getString(2));
                entity.setData(cursor.getString(3));
                entity.setAdapter(adapter);
                entity.setId(String.valueOf(cursor.getInt(0)));
                arrayList.add(entity);
            }
            cursor.moveToNext();
        }
    }

    public void dataAlbumFragment(ArrayList<EntitySong> arrayListAlbum) {
        String[] arr = new String[]{"", "play", "them vao playlist"};
        ArrayAdapter adapter = new ArrayAdapter(mActivity, android.R.layout.simple_list_item_1, arr);

        Cursor cursor = getCursor();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("");
        cursor.moveToFirst();
        boolean check = true;
        while (cursor.isAfterLast() == false) {
            for (int i = 0; i < arrayList.size(); i++) {
                if (cursor.getString(6).compareTo(arrayList.get(i)) != 0) {
                    check = true;
                } else {
                    check = false;
                    break;
                }
            }

            if (check == true) {
                EntitySong entity = new EntitySong();
                entity.setName(cursor.getString(6));
                entity.setAdapter(adapter);
                entity.setIcon(R.drawable.dia);
                arrayListAlbum.add(entity);
                arrayList.add(cursor.getString(6));
            }
            cursor.moveToNext();
        }
    }

//    public int getAlbum(int stt) {
//        int count = 0;
//
//        Cursor cursor = getCursor();
//        ArrayList<String> arrayList = new ArrayList<>();
//        arrayList.add("");
//        cursor.moveToFirst();
//        boolean check = true;
//        while (cursor.isAfterLast() == false) {
//            for (int i = 0; i < arrayList.size(); i++) {
//                if (cursor.getString(stt).compareTo(arrayList.get(i)) != 0) {
//                    check = true;
//                } else {
//                    check = false;
//                    break;
//                }
//            }
//
//            if (check == true) {
//                count++;
//                arrayList.add(cursor.getString(stt));
//            }
//            cursor.moveToNext();
//        }
//        return count;
//    }

    public void dataArtistFragment(ArrayList<EntitySong> arrayListArtist) {
        String[] arr = new String[]{"", "play", "them vao playlist"};
        ArrayAdapter adapter = new ArrayAdapter(mActivity, android.R.layout.simple_list_item_1, arr);

        Cursor cursor = getCursor();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("");
        cursor.moveToFirst();
        boolean check = true;
        while (cursor.isAfterLast() == false) {
            for (int i = 0; i < arrayList.size(); i++) {
                if (cursor.getString(1).compareTo(arrayList.get(i)) != 0) {
                    check = true;
                } else {
                    check = false;
                    break;
                }
            }

            if (check == true) {
                EntitySong entity = new EntitySong();
//                entity.setArtist(cursor.getString(1));
                entity.setAdapter(adapter);
                entity.setIcon(R.drawable.dia);
                entity.setName(cursor.getString(1));
                arrayListArtist.add(entity);
                arrayList.add(cursor.getString(1));
            }
            cursor.moveToNext();
        }
    }

}
