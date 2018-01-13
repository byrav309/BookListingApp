package com.exmample.android.booklistingapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Utility {
    final private static String TAG = Utility.class.getName();

    public static List<BookDetails> extractBooks(String json) {

        List<BookDetails> books = new ArrayList<>();

        try {
            JSONObject jsonResponse = new JSONObject(json);

            if (jsonResponse.getInt("totalItems") == 0) {
                return books;
            }
            JSONArray jsonArray = jsonResponse.getJSONArray("items");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject bookObject = jsonArray.getJSONObject(i);

                JSONObject bookInfo = bookObject.getJSONObject("volumeInfo");

                String title = bookInfo.getString("title");
                JSONArray authorsArray = bookInfo.getJSONArray("authors");
                String authors = formatListOfAuthors(authorsArray);
                JSONObject thumbNail = bookInfo.getJSONObject("imageLinks");
                String thumbnail = formatThumbnail(thumbNail);

                BookDetails book = new BookDetails(authors, title, thumbnail);
                books.add(book);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return books;
    }

    public static String formatThumbnail(JSONObject thumbnailObject) {
        String thumbnail = "";
        try {
            thumbnail = thumbnailObject.getString("thumbnail");
        } catch (JSONException e) {
            Log.e(TAG, "Exception while parsing thumbnail");
        }
        return thumbnail;
    }

    public static String formatListOfAuthors(JSONArray authorsList) throws JSONException {

        String authorsListInString = null;

        if (authorsList.length() == 0) {
            return null;
        }
        for (int i = 0; i < authorsList.length(); i++) {
            if (i == 0) {
                authorsListInString = authorsList.getString(0);
            } else {
                authorsListInString += ", " + authorsList.getString(i);
            }
        }
        return authorsListInString;
    }
}
