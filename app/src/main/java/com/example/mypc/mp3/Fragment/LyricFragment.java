package com.example.mypc.mp3.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mypc.mp3.Other.MyService;
import com.example.mypc.mp3.Other.SongApplication;
import com.example.mypc.mp3.Other.StringUtils;
import com.example.mypc.mp3.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class LyricFragment extends Fragment {


    TextView tv;
    SongApplication songApplication;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            tv.setText("");
            if (songApplication.isCheckOnOrOff()){
                int i = songApplication.getPosition();
                new PareseURL().execute(songApplication.getSongs().get(i).getData());
            }
//            else getLyrics();
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lyric, container, false);
        tv = (TextView) view.findViewById(R.id.tvLyrics);
        songApplication = (SongApplication) getActivity().getApplicationContext();
        tv.setMovementMethod(new ScrollingMovementMethod());
//        if (songApplication.isCheckOnOrOff()){
//            int i = songApplication.getPosition();
//            new PareseURL().execute(songApplication.getSongs().get(i).getData());
//        }
//        else getLyrics();
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (mReceiver!=null) {
                    IntentFilter filter = new IntentFilter(MyService.TAG);
                    getActivity().registerReceiver(mReceiver, filter);
                }
            }
        };
        handler.postDelayed(runnable, 100);
        return view;
    }

    private void getLyrics() {
        int i = songApplication.getPosition();
        String name = songApplication.getSongs().get(i).getName();
        name = StringUtils.removeAccent(name);
        name = name.replaceAll(" ", "%20");// thay khoang trang bang dau +

        String LINK = "http://j.ginggong.com/jOut.ashx?k=";//"http://searchsong.azurewebsites.net/api/song/";
        String KEY = "&h=mp3.zing.vn&code=[54db3d4b-518f-4f50-aa34-393147a8aa18]";//"&key=hoangson1";
        String data = LINK + name + KEY;
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jor1 = new JsonArrayRequest(Request.Method.GET, data, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject object = response.getJSONObject(0);
                    new PareseURL().execute(object.getString("UrlSource"));
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

    private class PareseURL extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {
            String link = "";
            try {
                Document doc = Jsoup.connect(strings[0]).get();
                Element element = doc.select("div.fn-container").first();//div.lyric(nhac cua tui)
                Element p = element.select("p").first();
                link = p.text();
                link = link.replace(".", ".\n");
                link = link.replace("[Đk]","\n[Đk]");
                Log.e("aaaaa",link);
            } catch (Throwable t) {
                t.printStackTrace();
            }

            return link;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tv.setText(s);
        }


    }


}
