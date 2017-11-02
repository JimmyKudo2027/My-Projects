package com.example.jimmykudo.jnewsapp;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsInfo>> {

    private static final int NEWS_LOADER_ID = 1;
    private static final String NEWS_REQUEST_URL =
            "http://content.guardianapis.com/search";
    ListView newsListView ;
    TextView emptyView;
    Button searchButton;
    EditText searchEditText;
    NewsLoader newsLoader;
    NewsAdapter newsAdapter;
    LoaderManager loaderManager;
    ProgressDialog progressDialog;
    ConnectivityManager connMgr;
    NetworkInfo networkInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.wait));
        newsListView = (ListView)findViewById(R.id.newsListView);
        emptyView = (TextView)findViewById(R.id.emptyView);
        searchButton = (Button) findViewById(R.id.searchButton);
        searchEditText =(EditText) findViewById(R.id.newsQuery);
        newsAdapter = new NewsAdapter(this,R.layout.news_layout);
        newsListView.setEmptyView(emptyView);
        newsListView.setAdapter(newsAdapter);
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsInfo news = (NewsInfo) parent.getItemAtPosition(position);
                String readerLink =news.getWebUrl();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(readerLink));
                startActivity(intent);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchEditText.getText().toString();
                if (query.isEmpty()){
                    Toast.makeText(getApplicationContext(),getString(R.string.search_hint),Toast.LENGTH_LONG).show();
                }else {
                    progressDialog.show();
                    Uri baseUri = Uri.parse(NEWS_REQUEST_URL);
                    Uri.Builder uriBuilder = baseUri.buildUpon();
                    uriBuilder.appendQueryParameter("api-key",getString(R.string.API_KEY));
                    uriBuilder.appendQueryParameter("q",query);
                    uriBuilder.appendQueryParameter("show-tags",getString(R.string.tag));
                    newsLoader = new NewsLoader(getApplicationContext(),uriBuilder.toString());

                    networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {

                        loaderManager = getLoaderManager();
                        loaderManager.destroyLoader(NEWS_LOADER_ID);
                        loaderManager.initLoader(NEWS_LOADER_ID, null, MainActivity.this);
                    } else {
                        newsAdapter.clear();
                        progressDialog.hide();
                        emptyView.setText(getString(R.string.check_connection));
                    }
                }
            }
        });
        networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, MainActivity.this);
        } else {
            progressDialog.hide();
            emptyView.setText(getString(R.string.check_connection));
        }

    }

    @Override
    public Loader<List<NewsInfo>> onCreateLoader(int id, Bundle args) {
        return newsLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<NewsInfo>> loader, List<NewsInfo> data) {
        progressDialog.hide();
        newsAdapter.clear();
        if (data != null && !data.isEmpty()) {
            newsAdapter.addAll(data);
        }else{
            emptyView.setText(getString(R.string.no_news));
        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsInfo>> loader) {
        newsAdapter.clear();
    }
}
