package com.example.mypc.mp3.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mypc.mp3.Adapter.AdapterListView;
import com.example.mypc.mp3.Entity.EntityListView;
import com.example.mypc.mp3.Other.MyEnum;
import com.example.mypc.mp3.R;

import java.util.ArrayList;


public class ListFragment extends Fragment {

    public static final String TITLE = "title";
    ListView listView;
    MyEnum page;
    ArrayList<EntityListView> mArrayList = new ArrayList<>();
    private Context context;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addItem(page);
    }
    ViewPagerFragment fragmentMain;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        listView = (ListView) view.findViewById(R.id.lv);
        AdapterListView adapterListView = new AdapterListView(mArrayList);
        listView.setAdapter(adapterListView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView textView = (TextView) view.findViewById(R.id.tvNameItem);
                String name = textView.getText().toString();

                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                if (fragmentMain==null){
                    fragmentMain = new ViewPagerFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(TITLE, name);
                    fragmentMain.setArguments(bundle);
                }
                transaction.replace(R.id.rlt, fragmentMain);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }

    private void addItem(MyEnum page) {
        EntityListView music = new EntityListView();
        music.setIconItem(R.drawable.music);
        music.setNameItem(page.SONG.toString());
//        music.setNumberItem(10);
        mArrayList.add(music);

        EntityListView album = new EntityListView();
        album.setIconItem(R.drawable.album);
        album.setNameItem(page.ALBUM.toString());
//        album.setNumberItem(1);
        mArrayList.add(album);

        EntityListView ns = new EntityListView();
        ns.setIconItem(R.drawable.users);
        ns.setNameItem(page.ARTIST.toString());
//        ns.setNumberItem(5);
        mArrayList.add(ns);

//        DataPLayList pLayList1 = new DataPLayList(getContext());
        EntityListView playList = new EntityListView();
        playList.setIconItem(R.drawable.list);
        playList.setNameItem(page.PLAYLIST.toString());
//        playList.setNumberItem(0);
        mArrayList.add(playList);
    }

}
