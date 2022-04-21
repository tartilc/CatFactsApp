package com.example.catfactsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    CatListAdapter catListAdapter;
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    static List<String> breeds;

    List<Cat> totalCats;
    String filter;
    static String[] catBreeds = { "Filter by breed"};

    static ArrayAdapter<String> adapter;
    Spinner spinner;
    Button buttonClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startUI();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction1 = manager.beginTransaction();
        FragmentTransaction transaction2 = manager.beginTransaction();
        transaction1.replace(R.id.main_fragment, new MainFragment()).commit();
        transaction2.replace(R.id.bottom_fragment, new bottomFragment()).commit();

        OkHttpHandler okHttpHandler= new OkHttpHandler(catListAdapter,this);
        okHttpHandler.execute();

    }

    public void startUI(){

        floatingActionButton = findViewById(R.id.fab);
        buttonClear = findViewById(R.id.clearButton);

        filter = getResources().getString(R.string.filter);
        totalCats = new ArrayList<>();

        breeds = new ArrayList<>();
        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, catBreeds);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        recyclerView = findViewById(R.id.recyclerView);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        catListAdapter = new CatListAdapter(this, this);
        recyclerView.setAdapter(catListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    public void goTop(View view) {
        recyclerView.smoothScrollToPosition(0);
    }

    public class OkHttpHandler extends AsyncTask<String,Void,String> {

        private WeakReference<CatListAdapter> wrCLA;
        private  Context context;

        OkHttpHandler(CatListAdapter catListAdapter, Context context) {
            this.wrCLA = new WeakReference<>(catListAdapter);
            this.context = context;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Cat catTemp = new Cat(jsonObject);

                    String breed="";
                    if(jsonObject.has("name")){
                        breed = jsonObject.get("name").toString();
                    }
                    if(!breeds.contains(breed) && !breed.isEmpty()) breeds.add(breed);

                    totalCats.add(catTemp);
                    wrCLA.get().addCat(catTemp);
                    wrCLA.get().notifyDataSetChanged();
                }

                breeds.add(0,getResources().getString(R.string.filter));
                catBreeds = breeds.toArray(new String[0]);
                adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, catBreeds);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(String[] params) {

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/breeds")
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
}