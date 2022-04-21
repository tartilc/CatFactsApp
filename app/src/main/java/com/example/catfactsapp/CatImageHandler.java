package com.example.catfactsapp;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CatImageHandler extends AsyncTask<String,Void,String> {

    private WeakReference<CatListAdapter> wrCLA;
    private String image_id;
    private int position;

    CatImageHandler(String image_id, int position, CatListAdapter catListAdapter) {
        this.image_id = image_id;
        this.position = position;
        this.wrCLA = new WeakReference<>(catListAdapter);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++){

                if(jsonArray.getJSONObject(i).has("url")){
                    String imageURL = jsonArray.getJSONObject(i).get("url").toString();
                    if(position < wrCLA.get().getmCats().size()){
                        wrCLA.get().getmCats().get(position).setImageID(imageURL);
                        wrCLA.get().getmCats().get(position).setDownloaded();
                        wrCLA.get().notifyItemChanged(position);
                    }
                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected String doInBackground(String[] params) {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://api.thecatapi.com/v1/images/search?breed_id="+image_id)
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("x-api-key", "92021ab8-d4ef-4bc2-8518-3900313df52a")
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}