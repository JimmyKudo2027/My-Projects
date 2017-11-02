package com.example.jimmykudo.jnewsapp;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter {
    LayoutInflater inflater;

    public NewsAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView = inflater.inflate(R.layout.news_layout,null);
        }
        NewsInfo info = (NewsInfo)getItem(position);
        TextView sectionName = (TextView) convertView.findViewById(R.id.sectionName);
        sectionName.setText(info.getSectionName());
        TextView webTitle  = (TextView) convertView.findViewById(R.id.webTitle);
        webTitle.setText(info.getWebTitle());
        TextView type = (TextView) convertView.findViewById(R.id.type);
        type.setText(info.getType());
        TextView webPublicationDate = (TextView) convertView.findViewById(R.id.webPublicationDate);
        webPublicationDate.setText(info.getWebPublicationDate());
        TextView authors = (TextView) convertView.findViewById(R.id.getAuthors);
        authors.setText(getAllAuthors((ArrayList<String>) info.getAuthors()));

        return convertView;
    }

    String getAllAuthors (ArrayList<String> authors){
        String allAuthors = "";
        for (int i = 0;i<authors.size();i++){
            if (i==0){
                allAuthors += authors.get(i);
            }else{
                allAuthors += (" & "+authors.get(i));
            }
        }
        return allAuthors;
    }
}
