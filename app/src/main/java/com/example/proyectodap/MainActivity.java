package com.example.proyectodap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectodap.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

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

        getSupportActionBar().hide();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mChampionList.setLayoutManager(layoutManager);
        mChampionList.setHasFixedSize(true);
        mAdapter = new ListAdapter(championList, this);

        ChampionTask champTask = (ChampionTask) new  ChampionTask();
        champTask.execute(NetworkUtils.buildUrl());
    }

    private void showJsonDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mChampionList.setVisibility(View.VISIBLE);
    }


    private void showErrorMessage() {
        mChampionList.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
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
            URL searchUrl = params[0];
            String githubSearchResults = null;
            try {
                githubSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return githubSearchResults;
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
                        String key = (String)keys.next();
                        if ( data.get(key) instanceof JSONObject ) {
                            JSONObject champ = new JSONObject(data.get(key).toString());

                            id = champ.getString("id");

                            JSONObject infochamp = champ.getJSONObject("stats");
                            int hp =  infochamp.getInt("hp");
                            int hpperlevel =  infochamp.getInt("hpperlevel");
                            int mp =  infochamp.getInt("mp");
                            int mpperlevel =  infochamp.getInt("mpperlevel");
                            int movespeed =  infochamp.getInt("hp");
                            int armor =  infochamp.getInt("hp");
                            int armorperlevel =  infochamp.getInt("hp");
                            int spellblock =  infochamp.getInt("hp");
                            int spellblockperlevel =  infochamp.getInt("hp");
                            int attackrange =  infochamp.getInt("hp");
                            int hpregen =  infochamp.getInt("hp");
                            int hpregenperlevel =  infochamp.getInt("hp");
                            int mpregen =  infochamp.getInt("hp");
                            int mpregenperlevel =  infochamp.getInt("hp");
                            int crit =  infochamp.getInt("hp");
                            int critperlevel =  infochamp.getInt("hp");
                            int attackdamage =  infochamp.getInt("hp");
                            int attakdamageperlevel =  infochamp.getInt("hp");
                            int attackspeedperlevel =  infochamp.getInt("hp");
                            int attackspeed =  infochamp.getInt("hp");

                            Champion curr_champ = new Champion(id, champ.getString("title"),hp,hpperlevel,mp,mpperlevel,movespeed,armor,armorperlevel,spellblock,spellblockperlevel,attackrange,
                                    hpregen,hpregenperlevel,mpregen,mpregenperlevel,crit,critperlevel,attackdamage,attakdamageperlevel,attackspeedperlevel,attackspeed,IMAGE_PATH + id + ".png");

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
}