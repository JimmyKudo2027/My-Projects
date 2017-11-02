package com.example.jimmykudo.booklistingapp;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class BookUtilis {
    public static final String LOG_TAG = "Jimmy_2027";

    public static List<BookInfo> fetchAllBooks(String requestUrl) {

        URL url = createURL(requestUrl);

        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<BookInfo> bookInfoList = extractFeatureFromJson(jsonResponse);

        return bookInfoList;

    }

    private static List<BookInfo> extractFeatureFromJson(String jsonResponse) {

        if (jsonResponse.isEmpty()) {
            return null;
        }

        List<BookInfo> bookInfoList = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(jsonResponse);
            JSONArray items = root.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                BookInfo bookInfo = new BookInfo();
                bookInfo.setTitle(items.getJSONObject(i).getJSONObject("volumeInfo").getString("title"));

                List<String> authorsList = new ArrayList<>();
                if(items.getJSONObject(i).getJSONObject("volumeInfo").has("authors")){
                    JSONArray jsonAuthors = items.getJSONObject(i).getJSONObject("volumeInfo").getJSONArray("authors");
                    for (int j = 0; j < jsonAuthors.length(); j++) {
                        authorsList.add(jsonAuthors.getString(j));
                    }
                }else {
                    authorsList.add("There are no Authors");
                }

                bookInfo.setAuthors(authorsList);

                if(items.getJSONObject(i).getJSONObject("volumeInfo").has("description")){
                    bookInfo.setDescription(items.getJSONObject(i).getJSONObject("volumeInfo").getString("description"));
                }else{
                    bookInfo.setDescription("No Description For This Book");
                }

                bookInfo.setReadLink(items.getJSONObject(i).getJSONObject("accessInfo").getString("webReaderLink"));

                bookInfoList.add(bookInfo);
            }

        } catch (JSONException e) {
        }

        return bookInfoList;
    }

    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;

    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static URL createURL(String requestUrl) {
        URL url = null;
        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

}
