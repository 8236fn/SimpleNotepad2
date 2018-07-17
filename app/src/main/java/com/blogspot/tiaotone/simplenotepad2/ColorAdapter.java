package com.blogspot.tiaotone.simplenotepad2;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ColorAdapter extends BaseAdapter {
    ArrayList<ItemData> colorlist;
    LayoutInflater inflater;

    public ColorAdapter(@NonNull Context context, ArrayList<ItemData> colorlist) {
        this.colorlist = colorlist;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return colorlist.size();
    }

    @Override
    public Object getItem(int position) {
        return colorlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return colorlist.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemData item = (ItemData) getItem(position);
        convertView = inflater.inflate(R.layout.color_view,null);
        TextView tvColor = convertView.findViewById(R.id.tv_color);

        tvColor.setText(item.name);
        tvColor.setTextColor(Color.BLACK);
        tvColor.setBackgroundColor(Color.parseColor(item.code));
        return convertView;
    }
}
