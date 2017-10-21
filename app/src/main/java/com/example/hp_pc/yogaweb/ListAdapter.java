package com.example.hp_pc.yogaweb;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by HP-PC on 31-07-2017.
 */

public class ListAdapter extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<ListPojo> al;
    LayoutInflater layoutInflater;
    public ListAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<ListPojo> objects) {
        super(context, resource, objects);
        layoutInflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.context=context;
        this.resource=resource;
        this.al=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v=layoutInflater.inflate(R.layout.listiing_item,null);
        ImageView imageView1=v.findViewById(R.id.imageView1);
        TextView textView=v.findViewById(R.id.txtView);
        ListPojo pojo=al.get(position);
        textView.setText(pojo.getCategory());
        Glide.with(context).load(pojo.getImg()).error(R.drawable.run).into(imageView1);
        return v;
    }
}
