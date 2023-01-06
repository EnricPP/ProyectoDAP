package com.example.proyectodap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectodap.utilities.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements ListAdapter.ListItemClickListener {

    private ArrayList<Champion> championList;

    private ListAdapter mAdapter;
    private RecyclerView mChampionList;

    //private ImageView iv;
    private ProgressBar mLoadingIndicator;
    private TextView mErrorMessageDisplay;

    private Bitmap defaultbitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //iv = findViewById(R.id.img_personaje);
        mChampionList = (RecyclerView) findViewById(R.id.rv_list);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        championList = new ArrayList<Champion>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mChampionList.setLayoutManager(layoutManager);
        mChampionList.setHasFixedSize(true);
        mAdapter = new ListAdapter(championList, this);

        ChampionTask champTask = (ChampionTask) new ChampionTask();
        champTask.execute(NetworkUtils.buildUrl());

        (new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    defaultbitmap = NetworkUtils.getURLImage("https://yt3.googleusercontent.com/iTUxDc9x5KWl4vg4z2av5OOp8PNKk4BmDy_wrFqMk9mlWJptozB2R6hqY90Ob2U925bqMlNHDg=s900-c-k-c0x00ffffff-no-rj");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        })).start();

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
    public class ChampionTask extends AsyncTask<URL, Void, String> {

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
                    String path = null;
                    String nuevoNombre = null;
                    Iterator<?> keys = data.keys();
                    //Iteramos por todos los campeones
                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        if (data.get(key) instanceof JSONObject) {
                            JSONObject champ = new JSONObject(data.get(key).toString());

                            nuevoNombre = champ.getString("name").trim();
                            nuevoNombre = nuevoNombre.replaceAll("\\.","");
                            nuevoNombre = nuevoNombre.replaceAll("\\'","");
                            nuevoNombre = nuevoNombre.replaceAll(" ","");
                            //nuevoNombre = nuevoNombre.substring(0, 1) + nuevoNombre.substring(1).toLowerCase(Locale.ROOT);
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
                            Log.i("champion",""+hp);
                            path = "http://ddragon.leagueoflegends.com/cdn/11.8.1/img/champion/"+nuevoNombre+".png";
                            Bitmap bitmap = null;
                            Champion curr_champ = new Champion(nuevoNombre, champ.getString("title"),hp,hpperlevel,mp,mpperlevel,movespeed,armor,armorperlevel,spellblock,spellblockperlevel,attackrange,hpregen,hpregenperlevel,mpregen,mpregenperlevel,crit,critperlevel,attackdamage,attakdamageperlevel,attackspeedperlevel,attackspeed,path);
                            Log.i("Campeon", nuevoNombre);
                            Log.i("URL", path);

                            String finalNuevoNombre = nuevoNombre;
                            (new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Bitmap bitmap = NetworkUtils.getURLImage(curr_champ.getImagepath());
                                        curr_champ.setImageBitmap(bitmap);
                                    } catch (FileNotFoundException e){
                                        Log.i("ERROR", "Campeon"+ finalNuevoNombre);
                                        try {
                                            curr_champ.setImageBitmap(defaultbitmap);
                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                        }
                                        e.printStackTrace();
                                    }catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            })).start();
                            championList.add(curr_champ);
                        }
                    }
                    mChampionList.setAdapter(mAdapter);
                    //iv.setImageBitmap(championList.get(1).getImageBitmap());
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
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

        Log.i("MAIN3", "CAMPEON"+clickedChamp.getName());
        startActivity(intent);
    }

}