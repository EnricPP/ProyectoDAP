package com.example.proyectodap;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.proyectodap.notification.NotificationHandler;
import com.example.proyectodap.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity implements ListAdapter.ListItemClickListener {

    private ArrayList<Champion> championList;

    private ListAdapter mAdapter;
    private RecyclerView mChampionList;

    private ProgressBar mLoadingIndicator;
    private TextView mErrorMessageDisplay;

    private SwipeRefreshLayout swipeRefreshLayout;

    private String SearchResult;

    private NotificationHandler handler;


    final static String IMAGE_PATH =
            "http://ddragon.leagueoflegends.com/cdn/12.23.1/img/champion/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mChampionList = (RecyclerView) findViewById(R.id.rv_list);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        championList = new ArrayList<Champion>();
        handler = new NotificationHandler(this);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkChampionsUpdate();
                swipeRefreshLayout.setRefreshing(false);

            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mChampionList.setLayoutManager(layoutManager);
        mChampionList.setHasFixedSize(true);
        mAdapter = new ListAdapter(championList, this);

        ChampionTask champTask = (ChampionTask) new  ChampionTask();
        champTask.execute(NetworkUtils.buildUrl());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();

        if (itemThatWasClickedId == R.id.action_profile) {
            startProfileActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void checkChampionsUpdate() {

        String result = SearchResult;

        Thread t = new Thread(() -> {
            try {
                String new_result = URLtoString(NetworkUtils.buildUrl());

                if (result.equals(new_result)){
                    Notification.Builder nBuilder = handler.createNotification("No new updates", "No changes in the characters", true);
                    handler.getManager().notify(1,nBuilder.build());
                }
                else{
                    Notification.Builder nBuilder = handler.createNotification("New updates", "There are changes in the characters", true);
                    handler.getManager().notify(1,nBuilder.build());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void showJsonDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mChampionList.setVisibility(View.VISIBLE);
    }


    private void showErrorMessage() {
        mChampionList.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    private String URLtoString (URL... params){

        URL searchUrl = params[0];
        String championSearchResults = null;
        try {
            championSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchResult = championSearchResults;
        return championSearchResults;
    }


    //tarea en segundo plano (Obtener campeones)
    public class ChampionTask extends AsyncTask<URL, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            return URLtoString(params);
        }

        @Override
        protected void onPostExecute(String githubSearchResults) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (githubSearchResults != null && !githubSearchResults.equals("")) {
                showJsonDataView();

                try {
                    String id = null;

                    JSONObject jsonbody = new JSONObject(githubSearchResults);
                    JSONObject data = jsonbody.getJSONObject("data");

                    Iterator<?> keys = data.keys();

                    //Iteramos por todos los campeones
                    while(keys.hasNext() ) {
                        ArrayList<String> stats = new ArrayList<String>();
                        String key = (String)keys.next();
                        if ( data.get(key) instanceof JSONObject ) {
                            JSONObject champ = new JSONObject(data.get(key).toString());

                            id = champ.getString("id");


                            JSONArray statschamp = champ.getJSONArray("tags");

                            for (int i = 0; i<statschamp.length(); i++)
                               stats.add(statschamp.getString(i));

                            JSONObject infochamp = champ.getJSONObject("stats");
                            double hp =  infochamp.getDouble("hp");
                            double armor =  infochamp.getDouble("armor");
                            double spellblock =  infochamp.getDouble("spellblock");
                            double attackdamage =  infochamp.getDouble("attackdamage");
                            double attackspeed =  infochamp.getDouble("attackspeed");
                            double attackrange =  infochamp.getDouble("attackrange");

                            Champion curr_champ = new Champion(stats,id, champ.getString("name"),champ.getString("title"), champ.getString("blurb") ,
                                    hp, armor,spellblock,attackdamage, attackspeed, attackrange, IMAGE_PATH + id + ".png");

                            Thread t = new Thread(() -> {
                                try {
                                    Bitmap mBitmap = Picasso.get().load(curr_champ.getImagepath()).get();
                                    curr_champ.setImageBitmap(mBitmap);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });

                            t.start();
                            try {
                                t.join();
                            } catch (InterruptedException e) {
                              e.printStackTrace();
                            }
                            championList.add(curr_champ);
                        }
                    }
                    mChampionList.setAdapter(mAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.i("ERROR", "ERROR_MENSAJE");
                showErrorMessage();
            }
        }
    }

    @Override
    public void onListItemClick(Champion clickedChamp) {
        Intent intent = new Intent(getApplicationContext(), ItemActivity.class);
        intent.putExtra("champion", clickedChamp).putExtra("championImg", clickedChamp.getImageBitmap());
        startActivity(intent);
    }

    private void startProfileActivity() {
        Intent startProfileActivity = new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(startProfileActivity);
    }

}