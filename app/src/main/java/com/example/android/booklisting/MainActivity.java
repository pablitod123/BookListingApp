package com.example.android.booklisting;

import android.app.LoaderManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ListMenuItemView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class MainActivity extends AppCompatActivity {

    /** Tag for the log messages */
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    // URL for the books api
    private static final String GOOGLE_BOOKS_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes";

    private TextView mEmptyNetworkTextView;

    private TextView mEmptyDataTextView;

    private BookAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find the ListView in the layout
        ListView bookListView = (ListView) findViewById(R.id.book_list);

        mAdapter = new BookAdapter(this, new ArrayList<Book>());

        bookListView.setAdapter(mAdapter);

        //TextView for error message when there is no internet connection
        mEmptyNetworkTextView = (TextView) findViewById(R.id.empty_view_network);
        bookListView.setEmptyView(mEmptyNetworkTextView);

        //TextView for error message when no books are returned
        mEmptyDataTextView = (TextView) findViewById(R.id.empty_view_data);
        bookListView.setEmptyView(mEmptyDataTextView);

        final TextView editView = (TextView) findViewById(R.id.topic_entry);

        final Button submitButton = (Button) findViewById(R.id.submit_button);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        final NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        //Set Click Listener on Button

        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // If there is a network connection, fetch data
                if (networkInfo != null && networkInfo.isConnected()) {

                    String newTopic = editView.getText().toString().trim();

                    if (editView.getText().length() == 0) {
                        mEmptyDataTextView.setText(R.string.no_result);
                    }

                    Uri baseUri = Uri.parse(GOOGLE_BOOKS_REQUEST_URL);
                    final Uri.Builder uriBuilder = baseUri.buildUpon();
                    uriBuilder.appendQueryParameter("q", newTopic);
                    uriBuilder.appendQueryParameter("maxResults", "20");

                    BookAsyncTask task = new BookAsyncTask();
                    task.execute(uriBuilder.toString());

                } else {
                    // If no network connectivity, display error
                    mEmptyNetworkTextView.setText(R.string.no_internet_connection);
                    submitButton.setVisibility(View.GONE);
                    editView.setVisibility(View.GONE);
                }
            }
        });
    }

    private class BookAsyncTask extends AsyncTask<String, Void, List<Book>> {

        @Override
        protected List<Book> doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<Book> result = QueryUtils.extractBooks(urls[0]);

            return result;
        }

        @Override
        protected void onPostExecute(List<Book> data) {
            // Clear the adapter of previous Book data
            mAdapter.clear();

            // If there is a valid list of {@link Book}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }
        }
    }
}
