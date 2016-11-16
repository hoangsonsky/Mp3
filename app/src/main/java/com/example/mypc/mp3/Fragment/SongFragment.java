package com.example.mypc.mp3.Fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mypc.mp3.Adapter.Adapter;
import com.example.mypc.mp3.Entity.EntitySong;
import com.example.mypc.mp3.Other.InfoSong;
import com.example.mypc.mp3.Other.OnClickMS;
import com.example.mypc.mp3.Other.SongApplication;
import com.example.mypc.mp3.CustomView.ViewPlayMusic;
import com.example.mypc.mp3.CustomView.ViewPlayMusicXML;
import com.example.mypc.mp3.R;

import java.util.ArrayList;


public class SongFragment extends Fragment implements View.OnClickListener {

    public static final String POSITION = "position";
    public static final String TAG = "SongFragment";
    public static final String NAME = "name";
    public static final String PARCELABLE = "parcelable";//Parcelable
    public static final String TEXT_SEARCH = "search_song";
    public static final String CHECK = "check";
    RecyclerView mRecyclerView;
    Adapter adapter;
    Context context;
    ArrayList<EntitySong> mArrayList = new ArrayList<>();
    SongApplication songApplication;
    String str;


    OnClickMS onClickMS = new OnClickMS() {
        @Override
        public void onClick123(boolean test, int id) {
            songApplication.setCheckOnOrOff(false);
            songApplication.setPosition(id);
            songApplication.setSongs(mArrayList);

            ViewPlayMusicXML xml = (ViewPlayMusicXML) getActivity().findViewById(R.id.view1);
            xml.setVisibility(View.VISIBLE);
            String name = mArrayList.get(id).getName();
            String artist = mArrayList.get(id).getArtist();
            ViewPlayMusic playMusic = new ViewPlayMusic();
            playMusic.initCustomPlay(getActivity());
            playMusic.setTextForTV(name, artist);

            Intent intent = new Intent(getActivity(), PlayMusicActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(TAG, TAG);
            bundle.putParcelableArrayList(PARCELABLE, mArrayList);
            bundle.putInt(POSITION, id);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBundle();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_song, container, false);
        songApplication = (SongApplication) getContext().getApplicationContext();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerSong);
        Button btn = (Button) view.findViewById(R.id.btn);
        btn.setVisibility(View.GONE);
        adapter = new Adapter(mArrayList, getActivity(), onClickMS, btn);
        mRecyclerView.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        btn.setOnClickListener(this);

        return view;
    }

    public void getBundle() {
        Bundle bundle = getArguments();
        InfoSong infoSong = new InfoSong(getActivity());
        if (bundle != null) {
            String name = bundle.getString(ViewPagerFragment.TAG);
            if (name.equals(AlbumFragment.TAG)) {
                String album = bundle.getString(NAME);
                infoSong.dataSendSong(album, 6, mArrayList);
            } else if (name.equals(ArtistFragment.TAG)) {
                String artist = bundle.getString(NAME);
                infoSong.dataSendSong(artist, 1, mArrayList);
            } else if (name.equals(PlayListFragment.TAG)) {
                String data = bundle.getString(NAME);
                infoSong.dataPlayList(data, mArrayList);
            } else
                infoSong.dataSongFragment(mArrayList);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter(newText);
                str = newText;
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }
        };
        searchView.setOnQueryTextListener(textChangeListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        setHasOptionsMenu(false);
        Bundle bundle = new Bundle();
        bundle.putString(TEXT_SEARCH, str);
        bundle.putBoolean(SongFragment.CHECK, false);
        Intent intent = new Intent(getActivity(),SongActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
