package com.ivntel.android.musicsearch;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.ivntel.android.musicsearch.model.Song;
import com.ivntel.android.musicsearch.model.SongsResponse;
import com.ivntel.android.musicsearch.retrofit.RestClient;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String DEFAULT_SEARCH_TERM = "rock";
    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String KEY_SONGS_RESPONSE = "KEY_SONGS_RESPONSE";
    //butterknife - associating xml id's to their java counterparts
    @Bind(R.id.search_text)
    EditText mSearchText;
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @Bind(R.id.no_results)
    TextView mNoResults;
    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;

    private LinearLayoutManager mLayoutManager;
    //the adapter that works with the array
    private SongsAdapter mAdapter;
    //giving the array of songs its variable name
    private List<Song> mSongs = new ArrayList<Song>();
    //brings the SongsResponse class in which handles all of the url data and puts it into an array and what not.
    private SongsResponse mSongsResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //sets the text in the searchtext view in the xml to this default
        mSearchText.setText(DEFAULT_SEARCH_TERM);
        //changes what is in the searchtext view to whatever the user types in
        mSearchText.setSelection(mSearchText.getText().length());

        // Check if we have data to display (after rotation)
        if (savedInstanceState != null) {
            mSongsResponse = (SongsResponse) savedInstanceState.getSerializable(KEY_SONGS_RESPONSE);
            mSongs = mSongsResponse.getSongs();
        } else {
            handleSearch();
        }

        mRecyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new SongsAdapter(this, mSongs);
        mRecyclerView.setAdapter(mAdapter);

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    handleSearch();
                    return true;
                }
                return false;
            }
        });
    }

    //saves all content after screen rotation
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_SONGS_RESPONSE, mSongsResponse);
    }

    @OnClick(R.id.search_button)
    public void handleSearch() {
        mProgressBar.setVisibility(View.VISIBLE);
        mNoResults.setVisibility(View.GONE);

        // Hide keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mSearchText.getWindowToken(), 0);

        String searchTerm = mSearchText.getText().toString();
        RestClient.getSearchApi().getItunesSearchResults(searchTerm, new Callback<SongsResponse>() {

            //method that is called when the url has succesfuly brought back data to be stored
            @Override
            public void success(SongsResponse songsResponse, Response response) {
                Log.d(TAG, response.getBody().toString());
                mSongsResponse = songsResponse;
                mSongs = mSongsResponse.getSongs();
                mAdapter.updateData(mSongs);
                mProgressBar.setVisibility(View.GONE);
                mNoResults.setVisibility(mSongs != null && mSongs.size() > 0 ? View.GONE : View.VISIBLE);
            }

            //the method that is called when no results are able to be pulled or displayed from the url for whatever reason
            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(MainActivity.this, "No results found", Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.GONE);
                mNoResults.setVisibility(View.VISIBLE);
            }
        });
    }
}