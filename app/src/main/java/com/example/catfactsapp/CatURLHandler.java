package com.example.catfactsapp;

import android.content.Context;
import android.os.AsyncTask;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CatURLHandler extends AsyncTask<String,Void,String> {

    private String image_id;
    Context context;

    @Override
    protected String doInBackground(String... strings) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://api.thecatapi.com/v1/images/search?breed_id="+image_id)
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("x-api-key", "d416e23e-5a60-4981-b384-00b1ba730db3")
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++){
                if(jsonArray.getJSONObject(i).has("url")){
                    String imageURL = jsonArray.getJSONObject(i).get("url").toString();
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
