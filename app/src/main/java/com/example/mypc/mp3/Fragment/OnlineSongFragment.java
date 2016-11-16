package com.example.mypc.mp3.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mypc.mp3.Adapter.OnlineSongAdapter;
import com.example.mypc.mp3.Entity.EntitySong;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.example.mypc.mp3.Other.OnClickMS;
import com.example.mypc.mp3.Other.SongApplication;
import com.example.mypc.mp3.Other.StringUtils;
import com.example.mypc.mp3.CustomView.ViewPlayMusic;
import com.example.mypc.mp3.CustomView.ViewPlayMusicXML;
import com.example.mypc.mp3.R;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


public class OnlineSongFragment extends Fragment {

    RecyclerView mRecyclerView;
    OnlineSongAdapter adapter;
    ArrayList<EntitySong> mArrayList = new ArrayList<>();
    SongApplication songApplication;
    ProgressBar mProgressBar;
    OnClickMS onClickMS = new OnClickMS() {
        @Override
        public void onClick123(boolean test, int position) {
            songApplication.setCheckOnOrOff(true);
            songApplication.setPosition(position);
            songApplication.setSongs(mArrayList);

            ViewPlayMusicXML xml = (ViewPlayMusicXML) getActivity().findViewById(R.id.viewSong);
            xml.setVisibility(View.VISIBLE);
            String name = mArrayList.get(position).getName();
            String artist = mArrayList.get(position).getArtist();
            ViewPlayMusic playMusic = new ViewPlayMusic();
            playMusic.initCustomPlay(getActivity());
            playMusic.setTextForTV(name, artist);

            Intent intent = new Intent(getActivity(), PlayMusicActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(SongFragment.TAG, SongFragment.TAG);
            bundle.putParcelableArrayList(SongFragment.PARCELABLE, mArrayList);
            bundle.putInt(SongFragment.POSITION, position);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_online_song, container, false);
        mArrayList.clear();
        songApplication = (SongApplication) getContext().getApplicationContext();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerOnline);
        mProgressBar = (ProgressBar)view.findViewById(R.id.prg) ;
        Bundle bundle = getArguments();
        String keySearch = bundle.getString(SongFragment.TEXT_SEARCH);
        dataOnline(keySearch);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        AppEventsLogger.activateApp(getActivity());
        return view;
    }

    private void dataOnline(String tktk) {

        String[] arr = new String[]{"", "tải về", "chia sẻ"};
        final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, arr);
        tktk = StringUtils.removeAccent(tktk);
        tktk = tktk.replaceAll(" ", "+");// thay khoang trang bang dau +
        String LINK = "http://j.ginggong.com/jOut.ashx?k=";//"http://searchsong.azurewebsites.net/api/song/";
        String KEY = "&h=mp3.zing.vn&code=[54db3d4b-518f-4f50-aa34-393147a8aa18]";//"&key=hoangson1";
        String data = LINK + tktk + KEY;
        mProgressBar.setVisibility(View.VISIBLE);
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jor1 = new JsonArrayRequest(Request.Method.GET, data, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        EntitySong entity = new EntitySong();
                        String link = object.getString("UrlJunDownload");//link
                        entity.setId(link);
                        entity.setIcon(R.drawable.dia);
                        entity.setData(object.getString("UrlSource"));
                        entity.setArtist(object.getString("Artist"));
                        entity.setName(object.getString("Title"));
                        entity.setAdapter(adapter);
                        mArrayList.add(entity);
                    }
                    OnlineSongAdapter adapter = new OnlineSongAdapter(mArrayList, getActivity(), onClickMS);
                    mRecyclerView.setAdapter(adapter);
                    LinearLayoutManager llm = new LinearLayoutManager(getContext());
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    mRecyclerView.setLayoutManager(llm);
                    mProgressBar.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"Internet connection errors",Toast.LENGTH_SHORT).show();
                Log.e("Volley", "Error");
            }
        });
        requestQueue.add(jor1);
    }

}
