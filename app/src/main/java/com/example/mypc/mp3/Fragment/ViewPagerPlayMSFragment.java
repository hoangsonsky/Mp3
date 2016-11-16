package com.example.mypc.mp3.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mypc.mp3.Adapter.ViewPagerPlayMSAdapter;
import com.example.mypc.mp3.R;


public class ViewPagerPlayMSFragment extends Fragment {


    ViewPager viewPager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_pager_play_m, container, false);
        viewPager = (ViewPager)view.findViewById(R.id.pagerPlayMS);
        addViewPager();
        return view;
    }

    private void addViewPager(){
        final ViewPagerPlayMSAdapter adapter = new ViewPagerPlayMSAdapter
                (getActivity().getSupportFragmentManager(), 3);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
    }

}
