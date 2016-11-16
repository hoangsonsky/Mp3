package com.example.mypc.mp3.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mypc.mp3.Adapter.ViewPagerAdapter;
import com.example.mypc.mp3.Other.MyEnum;
import com.example.mypc.mp3.R;


public class ViewPagerFragment extends Fragment {


    public static final String TAG = "ViewPagerFragment";
    TabLayout tabLayout;
    ViewPager viewPager;
    MyEnum page;
    private Context context;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);
        tabLayout = (TabLayout)view.findViewById(R.id.tab_layout);
        viewPager = (ViewPager)view.findViewById(R.id.pager);
        addViewPager();
        showViewPager(page);
        return view;
    }

    private void showViewPager(MyEnum page) {
        Bundle bundle = getArguments();
        String title = bundle.getString(ListFragment.TITLE);

        if (title.equals(page.PLAYLIST.toString())) {
            viewPager.setCurrentItem(0);
        } else if (title.equals(page.SONG.toString())) {
            viewPager.setCurrentItem(1);
        } else if (title.equals(page.ALBUM.toString())) {
            viewPager.setCurrentItem(2);
        } else if (title.equals(page.ARTIST.toString())) {
            viewPager.setCurrentItem(3);
        }

    }

    private void addViewPager() {

        Bundle bundle = new Bundle();
        bundle.putString(TAG, TAG);

        tabLayout.addTab(tabLayout.newTab().setText("PlayList"));
        tabLayout.addTab(tabLayout.newTab().setText("Bài Hát"));
        tabLayout.addTab(tabLayout.newTab().setText("Album"));
        tabLayout.addTab(tabLayout.newTab().setText("Nghệ sĩ"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPagerAdapter adapter = new ViewPagerAdapter(
                getActivity().getSupportFragmentManager(),
                tabLayout.getTabCount(),
                bundle);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


}
