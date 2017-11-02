package com.example.jimmykudo.tourgideegy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jimmy Kudo on 10/15/2017.
 */

public class PlaceAdapter extends ArrayAdapter<Place> {
    int colorRes;
    public PlaceAdapter(@NonNull Context context, @NonNull List<Place> objects, int colorRes) {
        super(context, R.layout.list_item, objects);
        this.colorRes = colorRes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        Place place = getItem(position);

        ImageView img = convertView.findViewById(R.id.place_img);
        img.setImageResource(place.getImgRes());

        TextView name = convertView.findViewById(R.id.place_name);
        name.setText(getContext().getString(place.getStrRes()));

        TextView desc = convertView.findViewById(R.id.place_desc);
        desc.setText(getContext().getString(place.getDesRes()));

        View container = convertView.findViewById(R.id.container);
        container.setBackgroundResource(colorRes);

        return convertView;
    }
}
