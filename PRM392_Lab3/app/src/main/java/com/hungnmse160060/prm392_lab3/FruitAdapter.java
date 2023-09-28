package com.hungnmse160060.prm392_lab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FruitAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Fruit> fruits;
    @Override
    public int getCount() {
        return fruits.size();
    }

    public FruitAdapter(Context context, int layout, List<Fruit> fruits) {
        this.context = context;
        this.layout = layout;
        this.fruits = fruits;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);
        TextView txtName = (TextView) view.findViewById(R.id.txtName);
        TextView txtDes = (TextView) view.findViewById(R.id.txtDescription);
        ImageView imgFruit = (ImageView) view.findViewById(R.id.imgFruit);
        Fruit fruit = fruits.get(i);
        txtName.setText(fruit.getName());
        txtDes.setText(fruit.getDescription());
        imgFruit.setImageDrawable(fruit.getImage());
        return view;
    }
}
