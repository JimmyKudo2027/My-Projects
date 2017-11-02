package com.example.jimmykudo.jnewsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import java.util.List;

public class NewsLoader extends AsyncTaskLoader{

        private String requestURL;

        public NewsLoader(Context context, String requestURL) {
        super(context);
            this.requestURL =requestURL;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<NewsInfo> loadInBackground() {
        if (requestURL==null){
            return null;
        }
        return NewsUtilis.fetchAllNews(requestURL);    }
}
