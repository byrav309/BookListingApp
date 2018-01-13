package com.exmample.android.booklistingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.RecyclerViewHolder> {

    private List<BookDetails> items;
    private Context context;

    public BooksAdapter(List<BookDetails> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public void setItems(List<BookDetails> items){
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        BookDetails bookDetails = items.get(position);
        holder.author.setText(bookDetails.getAuthor());
        holder.title.setText(bookDetails.getTitle());
        Picasso.with(context).load(bookDetails.getBookThumbNail()).placeholder(R.mipmap.ic_launcher).into(holder.thumbnail);
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView title, author;
        ImageView thumbnail;

        public RecyclerViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.book_title);
            author = view.findViewById(R.id.book_author);
            thumbnail = view.findViewById(R.id.book_thumbnail);
        }
    }
}
