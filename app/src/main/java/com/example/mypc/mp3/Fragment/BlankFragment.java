package com.example.mypc.mp3.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mypc.mp3.Adapter.AdapterBlank;
import com.example.mypc.mp3.Entity.EntityBlank;
import com.example.mypc.mp3.Other.Constants;
import com.example.mypc.mp3.Other.MyAnimation;
import com.example.mypc.mp3.Other.MyService;
import com.example.mypc.mp3.Other.SongApplication;
import com.example.mypc.mp3.R;

import java.util.ArrayList;


public class BlankFragment extends Fragment implements AdapterView.OnItemClickListener {

    ListView listView;
    AdapterBlank mAdapter;
    ArrayList<EntityBlank> arrayList =new ArrayList();
    MyAnimation myAnimation;
    SongApplication songApplication;
    private Handler mHandler = new Handler();
    private Runnable mRunnable;

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getBundleExtra(MyService.DATA);
            int i = songApplication.getPosition();
            mAdapter = new AdapterBlank(arrayList, i,
                    myAnimation, getContext());
            listView.setAdapter(mAdapter);
//            mAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        songApplication = (SongApplication) getActivity().getApplicationContext();
        data();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        songApplication = (SongApplication) getActivity().getApplicationContext();
        View view =  inflater.inflate(R.layout.fragment_blank, container, false);
        listView = (ListView)view.findViewById(R.id.lvPlay);
        mAdapter = new AdapterBlank(arrayList, songApplication.getPosition(), myAnimation, getContext());
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);

        mRunnable = new Runnable() {
            @Override
            public void run() {
                IntentFilter filter = new IntentFilter(MyService.TAG);
                getActivity().registerReceiver(mReceiver, filter);
            }
        };
        mHandler.postDelayed(mRunnable, 100);
        return view;
    }

    private void data() {
        for (int i = 0; i < songApplication.getSongs().size(); i++) {
            EntityBlank entity = new EntityBlank();
            entity.setID(songApplication.getSongs().get(i).getId()+"");
            entity.setName(songApplication.getSongs().get(i).getName());
            entity.setSinger(songApplication.getSongs().get(i).getArtist());
            entity.setIcon(R.drawable.dia);
            arrayList.add(entity);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        songApplication.setPosition(position);
        Intent startIntent = new Intent(getActivity(),
                MyService.class);
        startIntent.setAction(Constants.ACTION.PLAY_ACTION);
        startIntent.putExtra(SongFragment.POSITION,
                songApplication.getSongs().get(songApplication.getPosition()).getId());
        getActivity().startService(startIntent);
    }
}
