package com.ivntel.android.musicsearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ivntel.android.musicsearch.model.Lyric;
import com.ivntel.android.musicsearch.model.LyricResponse;
import com.ivntel.android.musicsearch.model.Song;
import com.ivntel.android.musicsearch.retrofit.RestClient;
import com.ivntel.android.musicsearch.retrofit.WikiaRestClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ivnte on 2017-05-13.
 */

public class LyricsActivity extends ActionBarActivity {

    public static final String ARG_SONG = "ARG_SONG";
    public static final String ARG_LYRIC = "ARG_LYRIC";
    private LyricResponse mLyricsResponse;
    private Lyric mLyric;
    public static final String TAG = LyricsActivity.class.getSimpleName();

    @Bind(R.id.detail_image)
    ImageView detailImage;
    @Bind(R.id.detail_artist)
    TextView detailArtist;
    @Bind(R.id.detail_song)
    TextView detailSong;
    @Bind(R.id.collection_name)
    TextView collectionName;
    @Bind(R.id.track_price)
    TextView trackPrice;
    @Bind(R.id.song_length)
    TextView songLength;
    @Bind(R.id.song_genre)
    TextView songGenre;
    @Bind(R.id.album_price)
    TextView albumPrice;
    @Bind(R.id.lyrics)
    TextView songLyrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyrics);
        ButterKnife.bind(this);
        getWikiaLyrics();

        Intent intent = getIntent();

        if(intent != null) {
            Song song = (Song) intent.getSerializableExtra(ARG_SONG);
            //Lyric lyric = (Lyric) intent.getSerializableExtra(ARG_LYRIC);

            if (song != null) {

                detailArtist.setText("Artist: " + song.getArtistName());
                detailSong.setText("Song name: " + song.getSongName());
                collectionName.setText("Album name: " + song.getCollectionName());
                trackPrice.setText("Song Price: " + "$" + song.getTrackPrice());
                songGenre.setText("Genre: " + song.getGenreName());
                albumPrice.setText("Album price: " + "$" + song.getAlbumPrice());
                //Convert songlength to minutes
                int songLengthSeconds = song.getSongLength() / 1000;
                int songLengthMinutes = songLengthSeconds / 60;
                songLengthSeconds = songLengthSeconds % 60;
                songLength.setText("Song length: " + songLengthMinutes + " minutes & " + songLengthSeconds + " seconds");
                Glide.with(this).load(song.getArtistUrl()).into(detailImage);
            }
        }
    }
    /*public void getWikiaLyrics() {
        String artistName = detailArtist.toString();
        String songName = detailArtist.toString();
        String artistNameAndSong = "artistname:" + artistName + "," + "trackname:" + songName + "&output=json&count=1";

        RestClient.getWikiaSearchApi().getWikiaSearchResults(artistNameAndSong, new retrofit.Callback<LyricResponse>() {

            @Override
            public void success(LyricResponse lyricResponse, Response response) {
                Log.d(TAG, response.getBody().toString());
                mLyricsResponse = lyricResponse;
                //Get the lyrics from the object brought in
                mLyricsResponse.getResponse();
                songLyrics.setText("Lyrics: " + mLyric.getLYRICS());
                //mAdapter.updateData(mSongs);
                //mProgressBar.setVisibility(View.GONE);
                //mNoResults.setVisibility(mSongs != null && mSongs.size() > 0 ? View.GONE : View.VISIBLE);
            }
            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "No lyrics results found", Toast.LENGTH_SHORT).show();
                songLyrics.setText("Lyrics: No lyrics results");
            }
        });
    }*/
}