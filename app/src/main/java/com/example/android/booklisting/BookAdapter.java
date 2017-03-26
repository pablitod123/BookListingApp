package com.example.android.booklisting;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by paulstyslinger on 3/21/17.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    //Contructor of the BookAdapter
    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        //Get the current book position in the list of books
        Book currentBook = getItem(position);

        TextView titleView = (TextView) listItemView.findViewById(R.id.title);
        titleView.setText(currentBook.getTitle());

        TextView descriptionView = (TextView) listItemView.findViewById(R.id.author);
        descriptionView.setText(currentBook.getAuthor());

        TextView pagesView = (TextView) listItemView.findViewById(R.id.pages);
        pagesView.setText(String.valueOf(currentBook.getPages()));

        return listItemView;
    }
}
