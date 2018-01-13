package com.exmample.android.booklistingapp;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;


public class GetBookDetailsTask extends AsyncTask<Void, Void, List<BookDetails>> {

    private String searchText;
    private final int READ_TIMEOUT_TIME = 5000;
    private final int CONNECTION_TIMEOUT_TIME = 5000;
    private final String BOOK_BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=search+";
    private SearchResultCallBack resultCallBack;
    private final String TAG = getClass().getName();


    public GetBookDetailsTask(String searchText, SearchResultCallBack resultCallBack) {
        this.searchText = searchText;
        this.resultCallBack = resultCallBack;
    }

    @Override
    protected List<BookDetails> doInBackground(Void... params) {
        URL url = createURL(getUrlForHttpRequest());
        String jsonResponse = "";

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<BookDetails> books = parseJson(jsonResponse);
        return books;
    }

    private List<BookDetails> parseJson(String json) {

        if (json == null) {
            return null;
        }
        List<BookDetails> books = Utility.extractBooks(json);
        return books;
    }

    private String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(READ_TIMEOUT_TIME);
            urlConnection.setConnectTimeout(CONNECTION_TIMEOUT_TIME);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(TAG, "Error response code: " + urlConnection.getResponseCode());
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

    private String readFromStream(InputStream inputStream) throws IOException {
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


    private URL createURL(String stringUrl) {
        try {
            return new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getUrlForHttpRequest() {
        String url = BOOK_BASE_URL;
        String formatUserInput = searchText.trim().replaceAll("\\s+", "+");
        if (!TextUtils.isEmpty(formatUserInput))
            url = BOOK_BASE_URL + formatUserInput;
        return url;
    }

    @Override
    protected void onPostExecute(List<BookDetails> bookList) {
        if(resultCallBack != null){
            resultCallBack.onResult(bookList);
        }
    }
}
