package com.example.mypc.mp3.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mypc.mp3.Adapter.PlayListAdapter;
import com.example.mypc.mp3.Entity.EntitySong;
import com.example.mypc.mp3.Other.DataPLayList;
import com.example.mypc.mp3.Other.OnClickMS;
import com.example.mypc.mp3.Other.SongApplication;
import com.example.mypc.mp3.CustomView.ViewPlayMusic;
import com.example.mypc.mp3.CustomView.ViewPlayMusicXML;
import com.example.mypc.mp3.R;

import java.util.ArrayList;


public class PlayListFragment extends Fragment {

    public static final String TAG = "PlayListFragment";
    RecyclerView mRecyclerView;
    PlayListAdapter adapter;
    ArrayList<EntitySong> mArrayList = new ArrayList<>();
    SongApplication songApplication;
    OnClickMS onClickMS = new OnClickMS() {
        @Override
        public void onClick123(boolean test, int position) {
            songApplication.setCheckOnOrOff(false);
            String name = mArrayList.get(position).getName();
            if (test){
                Bundle bundle = new Bundle();
                bundle.putString(SongFragment.NAME, name);
                bundle.putString(ViewPagerFragment.TAG, TAG);
                bundle.putBoolean(SongFragment.CHECK, true);
                Intent intent = new Intent(getActivity(),SongActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }else {
                DataPLayList dataPLayList = new DataPLayList(getContext());
                songApplication.setPosition(0);
//                dataPlayList(bundle.getString(FragmentPlayList.PLAYLIST_SONG));
                ArrayList<EntitySong> arrayList =dataPLayList.data(name);
                songApplication.setSongs(arrayList);

                ViewPlayMusicXML xml = (ViewPlayMusicXML) getActivity().findViewById(R.id.view1);
                xml.setVisibility(View.VISIBLE);
                String name1 = arrayList.get(0).getName();
                String artist = arrayList.get(0).getArtist();
                ViewPlayMusic playMusic = new ViewPlayMusic();
                playMusic.initCustomPlay(getActivity());
                playMusic.setTextForTV(name1, artist);

                Intent intent = new Intent(getActivity(),PlayMusicActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(SongFragment.TAG,SongFragment.TAG);
                bundle.putParcelableArrayList(SongFragment.PARCELABLE,arrayList);
                bundle.putInt(SongFragment.POSITION,0);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataPLayList pLayList = new DataPLayList(getContext());
        pLayList.dataPlayList(mArrayList);
        songApplication = (SongApplication) getContext().getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_play_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerPlayList);
        adapter = new PlayListAdapter(mArrayList, getActivity(),onClickMS);
        mRecyclerView.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        return view;
    }


}
