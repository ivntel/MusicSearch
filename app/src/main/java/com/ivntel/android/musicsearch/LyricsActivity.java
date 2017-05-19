package com.ivntel.android.musicsearch;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ivntel.android.musicsearch.model.Song;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ivnte on 2017-05-13.
 */

public class LyricsActivity extends AppCompatActivity {

    public static final String ARG_SONG = "ARG_SONG";
    public static final String TAG = LyricsActivity.class.getSimpleName();
    public static final String apiKey = "926933d99eb64dc89d878d79e0e75bc7";
    String myJSON,songLyrics="", detailsList="", firstTrackID=null;
    JSONArray peoples=null;
    private static final String TAG_MESSAGE="message";
    private static final String TAG_BODY="body";
    private static final String TAG_LYRICS="lyrics";
    private static final String TAG_LYRICS_BODY="lyrics_body";
    private static final String TAG_TRACK="track";
    private static final String TAG_TRACK_ID = "track_id";
    private static final String TAG_TRACK_LIST="track_list";
    int count=0;

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
    TextView songLyricsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyrics);
        ButterKnife.bind(this);
        songLyricsTextView.setMovementMethod(new ScrollingMovementMethod());
        Log.d("Values: ", "calling lyric activity");

        Intent intent = getIntent();

        if(intent != null) {
            Song song = (Song) intent.getSerializableExtra(ARG_SONG);

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

                String songName = song.getSongName();
                String artistName = song.getArtistName();
                songName = songName.replaceAll(" ", "%20");
                artistName = artistName.replaceAll(" ", "%20");
                searchSong(artistName, songName);
            }
        }
    }
    private void searchSong(final String artistname, final String songname) {
        try {

            class SearchSongAsync extends AsyncTask<String, Void, String> {

                private Dialog loadingDialog;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    loadingDialog = ProgressDialog.show(LyricsActivity.this, "Please wait", "Searching for song...");
                }

                @Override
                protected String doInBackground(String... params) {

                    String result = null;
                    InputStream inputStream = null;
                    try {
                        String link = "http://api.musixmatch.com/ws/1.1/track.search?apikey=" + apiKey + "&q_artist=" + artistname + "&q_track=" + songname + "&format=json&page_size=1";
                        Log.d("Values: ", "URL: " + link);
                        URL url = new URL(link);
                        HttpClient client = new DefaultHttpClient();
                        HttpGet request = new HttpGet();
                        request.setURI(new URI(link));
                        HttpResponse response = client.execute(request);

                        HttpEntity entity = response.getEntity();

                        inputStream = entity.getContent();
                        // json is UTF-8 by default
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                        StringBuilder sb = new StringBuilder();

                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        result = sb.toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (inputStream != null) inputStream.close();
                        } catch (Exception squish) {
                        }
                    }
                    return result;
                }

                @Override
                protected void onPostExecute(String result) {
                    myJSON=result;
                    loadingDialog.dismiss();
                    checkResult();
                }
            }

            SearchSongAsync la = new SearchSongAsync();
            la.execute();
        }catch (Exception ex)
        {
            Toast.makeText(getBaseContext(),"You are not connected",Toast.LENGTH_LONG).show();
        }
    }

    protected  void checkResult(){
        peoples = null;
        detailsList = "";
        if(myJSON != null && myJSON.isEmpty() != true){
            try {
                JSONObject jsonObj = new JSONObject(myJSON);
                JSONObject messageOBJ = jsonObj.getJSONObject(TAG_MESSAGE);
                JSONObject bodyOBJ = messageOBJ.getJSONObject(TAG_BODY);
                peoples = bodyOBJ.getJSONArray(TAG_TRACK_LIST);

                for(int i=0;i<peoples.length();i++){

                    JSONObject x = peoples.getJSONObject(i);
                    JSONObject c = x.getJSONObject(TAG_TRACK);

                    if(count == 0){
                        firstTrackID = c.getString(TAG_TRACK_ID);
                        count = 1;
                    }
                    detailsList = detailsList + "\nNew Data \n\n" + "Track_ID: " + c.getString(TAG_TRACK_ID);

                }

                count = 0;

                getLyricsForTheSong(firstTrackID);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
            Toast.makeText(getBaseContext(),"No data recieved.\nVerfiy that you've entered correct data.",Toast.LENGTH_LONG).show();
        }
    }

    private void getLyricsForTheSong(final String track_id) {
        try {

            class GetLyricsAsync extends AsyncTask<String, Void, String> {

                private Dialog loadingDialog;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    loadingDialog = ProgressDialog.show(LyricsActivity.this, "Please wait", "Searching for lyrics...");
                }

                @Override
                protected String doInBackground(String... params) {

                    String result = null;
                    InputStream inputStream = null;
                    try {
                        String link = "http://api.musixmatch.com/ws/1.1/track.lyrics.get?apikey=" + apiKey + "&track_id=" +
                                track_id + "&format=json";
                        URL url = new URL(link);
                        HttpClient client = new DefaultHttpClient();
                        HttpGet request = new HttpGet();
                        request.setURI(new URI(link));
                        HttpResponse response = client.execute(request);

                        HttpEntity entity = response.getEntity();

                        inputStream = entity.getContent();
                        // json is UTF-8 by default
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                        StringBuilder sb = new StringBuilder();

                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        result = sb.toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (inputStream != null) inputStream.close();
                        } catch (Exception squish) {
                        }
                    }
                    return result;
                }

                @Override
                protected void onPostExecute(String result) {
                    myJSON=result;
                    loadingDialog.dismiss();
                    checkLyrics();
                }
            }

            GetLyricsAsync la = new GetLyricsAsync();
            la.execute();
        }catch (Exception ex)
        {
            Toast.makeText(getBaseContext(),"You are not connected",Toast.LENGTH_LONG).show();
        }
    }

    protected  void checkLyrics(){
        peoples = null;

        if(myJSON != null && myJSON.isEmpty() != true){
            try {
                JSONObject jsonObj = new JSONObject(myJSON);
                JSONObject messageOBJ = jsonObj.getJSONObject(TAG_MESSAGE);
                JSONObject bodyOBJ = messageOBJ.getJSONObject(TAG_BODY);
                JSONObject lyricsOBJ = bodyOBJ.getJSONObject(TAG_LYRICS);

                songLyrics = songLyrics + "Lyrics to song: \n\n" +
                        lyricsOBJ.getString(TAG_LYRICS_BODY);


                if(songLyrics != null && songLyrics.isEmpty() != true){
                    songLyricsTextView.setText(songLyrics);
                }else{
                    songLyrics = songLyrics + "Lyrics Not Available.";

                    songLyricsTextView.setText(songLyrics);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
            Toast.makeText(getBaseContext(),"You are not connected",Toast.LENGTH_LONG).show();
        }
    }
}