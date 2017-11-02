package com.example.jimmykudo.booklistingapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class BookTaskLoader extends AsyncTaskLoader {
    private String requestURL;

    public BookTaskLoader(Context context,String mURL) {
        super(context);
        requestURL = mURL;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<BookInfo> loadInBackground() {
        if (requestURL==null){
            return null;
        }
        return BookUtilis.fetchAllBooks(requestURL);
    }
}