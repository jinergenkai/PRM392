package com.hungnmse160060.prm392_lab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FootballAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Football> footballs;

    public FootballAdapter(Context context, int layout, List<Football> footballs) {
        this.context = context;
        this.layout = layout;
        this.footballs = footballs;
    }

    @Override
    public int getCount() {
        return footballs.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);
        TextView txtName = (TextView) view.findViewById(R.id.txtName);
        TextView txtDes = (TextView) view.findViewById(R.id.txtDescription);
        ImageView imgFruit = (ImageView) view.findViewById(R.id.imgFruit);
        ImageView imgFlag = (ImageView) view.findViewById(R.id.imgFruit);
        Football fb = footballs.get(i);
        txtName.setText(fb.getName());
        txtDes.setText(fb.getDescription());
        imgFruit.setImageResource(fb.getImage());
        imgFlag.setImageResource(fb.getFlag());
        return view;
    }
}
