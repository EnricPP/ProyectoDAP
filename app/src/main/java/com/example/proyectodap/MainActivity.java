package com.example.proyectodap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectodap.utilities.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mChampionList = (RecyclerView) findViewById(R.id.rv_list);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        championList = new ArrayList<Champion>();

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
            //Log.i("RESULTADO", githubSearchResults);
            if (githubSearchResults != null && !githubSearchResults.equals("")) {
                showJsonDataView();

                try {
                    JSONObject jsonbody = new JSONObject(githubSearchResults);
                    JSONObject data = jsonbody.getJSONObject("data");

                    Iterator<?> keys = data.keys();

                    //Iteramos por todos los campeones
                    while(keys.hasNext() ) {
                        String key = (String)keys.next();
                        if ( data.get(key) instanceof JSONObject ) {
                            JSONObject champ = new JSONObject(data.get(key).toString());
                            Champion curr_champ = new Champion(champ.getString("name"), champ.getString("title"));
                            Log.i("Campeon", champ.getString("name"));
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
        intent.putExtra("champion", clickedChamp);
        startActivity(intent);
    }
}