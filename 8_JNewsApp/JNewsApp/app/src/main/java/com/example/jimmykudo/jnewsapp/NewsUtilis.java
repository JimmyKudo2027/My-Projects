package com.example.jimmykudo.jnewsapp;

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


public final class NewsUtilis {

    public static final String LOG_TAG = "Jimmy_2027";

    public static List<NewsInfo> fetchAllNews(String requestUrl) {

        URL url = createURL(requestUrl);

        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<NewsInfo> newsInfoList = extractFeatureFromJson(jsonResponse);

        return newsInfoList;

    }

    private static List<NewsInfo> extractFeatureFromJson(String jsonResponse) {

        if (jsonResponse.isEmpty()) {
            return null;
        }

        List<NewsInfo> newsInfos = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(jsonResponse);
            JSONArray items = root.getJSONObject("response").getJSONArray("results");
            for (int i = 0; i < items.length(); i++) {
                NewsInfo info = new NewsInfo();
                info.setSectionName(items.getJSONObject(i).getString("sectionName"));
                info.setWebTitle(items.getJSONObject(i).getString("webTitle"));
                info.setType(items.getJSONObject(i).getString("type"));
                info.setWebPublicationDate(items.getJSONObject(i).getString("webPublicationDate"));
                info.setWebUrl(items.getJSONObject(i).getString("webUrl"));
                List<String> authors = new ArrayList<>();
                if(items.getJSONObject(i).has("tags")){
                    JSONArray tags = items.getJSONObject(i).getJSONArray("tags");
                    if (tags.length() == 0){
                        authors.add("No Authors Found !");
                    }
                    for (int j =0; j < tags.length(); j++) {
                        // Get a single news at position i within the list of news
                        JSONObject currentTag = tags.getJSONObject(j);
                        // In the tags the webtitle is the author of the news
                        String author = currentTag.getString("webTitle");
                        if (author != null) {
                            authors.add(author);
                        }
                    }
                }else{
                    authors.add("No Authors Found !");
                }
                info.setAuthors(authors);
                newsInfos.add(info);
            }

        } catch (JSONException e) {
            Log.i(LOG_TAG,e.getMessage());
        }

        return newsInfos;
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
