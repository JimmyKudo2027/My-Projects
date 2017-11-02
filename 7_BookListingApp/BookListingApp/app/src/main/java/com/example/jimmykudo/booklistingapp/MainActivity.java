package com.example.jimmykudo.booklistingapp;

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

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<BookInfo>>{

    private static final int BOOK_LOADER_ID = 1;
    private static final String BOOKS_REQUEST_URL =
            "https://www.googleapis.com/books/v1/volumes?q=";
    ListView bookListView ;
    TextView somethingGoesWrong;
    Button searchButton;
    EditText searchEditText;
    BookTaskLoader bookTaskLoader;
    BookAdapter bookAdapter;
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
        bookListView = (ListView) findViewById(R.id.bookListView);
        somethingGoesWrong = (TextView) findViewById(R.id.noBooksFound);
        searchButton = (Button) findViewById(R.id.bookSearchButton);
        searchEditText =(EditText) findViewById(R.id.bookSearchBar);
        bookAdapter = new BookAdapter(getApplicationContext(),R.layout.book_custom_layout);
        bookListView.setAdapter(bookAdapter);
        bookListView.setEmptyView(somethingGoesWrong);
        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BookInfo book = (BookInfo) parent.getItemAtPosition(position);
                String readerLink =book.getReadLink();
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
                    bookTaskLoader = new BookTaskLoader(getApplicationContext(),BOOKS_REQUEST_URL+query);

                    networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {

                        loaderManager = getLoaderManager();
                        loaderManager.destroyLoader(BOOK_LOADER_ID);
                        loaderManager.initLoader(BOOK_LOADER_ID, null, MainActivity.this);
                    } else {
                        bookAdapter.clear();
                        progressDialog.hide();
                        somethingGoesWrong.setText(getString(R.string.check_connection));
                    }
                }
            }
        });

        networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            loaderManager = getLoaderManager();
            loaderManager.initLoader(BOOK_LOADER_ID, null, MainActivity.this);
        } else {
            progressDialog.hide();
            somethingGoesWrong.setText(getString(R.string.check_connection));
        }

    }

    @Override
    public Loader<List<BookInfo>> onCreateLoader(int id, Bundle args) {
        return bookTaskLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<BookInfo>> loader, List<BookInfo> data) {

        progressDialog.hide();
        bookAdapter.clear();
        if (data != null && !data.isEmpty()) {
            bookAdapter.addAll(data);
        }else{
            somethingGoesWrong.setText(getString(R.string.no_books));
        }
    }

    @Override
    public void onLoaderReset(Loader<List<BookInfo>> loader) {
        bookAdapter.clear();
    }
}
