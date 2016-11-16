package com.example.mypc.mp3.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mypc.mp3.Entity.EntityListView;
import com.example.mypc.mp3.R;

import java.util.ArrayList;

/**
 * Created by MyPC on 05/10/2016.
 */
public class AdapterListView extends BaseAdapter {

    ArrayList<EntityListView> arrayList = new ArrayList<>();

    public AdapterListView(ArrayList<EntityListView> arrayList) {
        this.arrayList = arrayList;
    }

    private class MyView{
        ImageView icon;
        TextView tvName;
//        TextView tvNumber;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyView viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.view_item, null);

            viewHolder = new MyView();
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.iconItem);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvNameItem);
//            viewHolder.tvNumber = (TextView) convertView.findViewById(R.id.tvNumberItem);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (MyView) convertView.getTag();

        EntityListView item = (EntityListView) getItem(position);
        viewHolder.icon.setImageResource(item.getIconItem());
        viewHolder.tvName.setText(item.getNameItem() + "");
//        viewHolder.tvNumber.setText(item.getNumberItem()+"");
        return convertView;
    }
}
