package com.example.proyectodap.utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class NetworkUtils {

    final static String BASE_URL =
            "http://ddragon.leagueoflegends.com/cdn/12.23.1/data/en_US/champion.json";


    public static URL buildUrl (){

        URL searchURL = null;

        try {
            searchURL = new URL(BASE_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return searchURL;
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static Bitmap getURLImage(String url) throws Exception {
        URL website = new URL(url);
        URLConnection connection = website.openConnection();

        Bitmap res = BitmapFactory.decodeStream(connection.getInputStream());
        return res;
    }
}