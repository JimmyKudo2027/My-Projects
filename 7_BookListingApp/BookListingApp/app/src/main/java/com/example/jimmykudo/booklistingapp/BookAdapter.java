package com.example.jimmykudo.booklistingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter {
    LayoutInflater inflater;
    public BookAdapter(Context context, int resource) {
        super(context, resource);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView==null){
            convertView = inflater.inflate(R.layout.book_custom_layout,null);
        }

        BookInfo bookInfo = (BookInfo) getItem(position);

        TextView title =(TextView) convertView.findViewById(R.id.bookTitle);
        title.setText(bookInfo.getTitle());

        TextView author =(TextView) convertView.findViewById(R.id.bookAuthors);
        author.setText(getAllAuthors((ArrayList<String>) bookInfo.getAuthors()));

        TextView description =(TextView) convertView.findViewById(R.id.bookDescription);
        description.setText(bookInfo.getDescription());

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
