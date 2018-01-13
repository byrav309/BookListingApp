package com.exmample.android.booklistingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchResultCallBack, View.OnClickListener {

    private RecyclerView recyclerView;
    private BooksAdapter adapter;
    private List<BookDetails> bookItems;
    private EditText searchText;
    private ImageButton imageButton;
    private TextView noDataFoundTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        searchText = (EditText) findViewById(R.id.search_text);
        imageButton = (ImageButton) findViewById(R.id.search_button);
        noDataFoundTextView = (TextView) findViewById(R.id.text_no_data_found);
        imageButton.setOnClickListener(this);
        bookItems = new ArrayList<>();
        adapter = new BooksAdapter(bookItems, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        new GetBookDetailsTask("", this).execute();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.search_button) {
            new GetBookDetailsTask(searchText.getText().toString().trim(), this).execute();
        }
    }

    @Override
    public void onResult(List<BookDetails> books) {
        if (books.size() <= 0) {
            noDataFoundTextView.setVisibility(View.VISIBLE);
            return;
        }
        noDataFoundTextView.setVisibility(View.GONE);
        adapter.setItems(books);
        adapter.notifyDataSetChanged();
    }
}
